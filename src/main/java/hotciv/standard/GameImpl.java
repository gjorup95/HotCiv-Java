package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.lang.Math;

/**
 * Skeleton implementation of HotCiv.
 * <p>
 * This source code is from the book
 * "Flexible, Reliable Software:
 * Using Patterns and Agile Development"
 * published 2010 by CRC Press.
 * Author:
 * Henrik B Christensen
 * Department of Computer Science
 * Aarhus University
 * <p>
 * Please visit http://www.baerbak.com/ for further information.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class GameImpl implements Game {

    /**
     * Fields
     */
    private Player playerInTurn;
    private int age;
    private WinningCondition winningCondition;
    private Ageing ageing;
    private WorldCreator worldCreator;

    /**
     * HashMaps that together make up the World in the Game.
     */
    private Map<Position, TileImpl> worldMap = new HashMap<>();
    private Map<Position, CityImpl> cityMap = new HashMap<>();
    private Map<Position, UnitImpl> unitMap = new HashMap<>();

    /**
     * Constructor
     */
    public GameImpl(GameType version) {
        if(version == GameType.ALPHA){
         winningCondition = new WinningConditionAlphaCiv(this);
         ageing = new AgeingAlphaCiv();
         worldCreator = new WorldCreatorAlphaCiv(this);
        }
        if (version == GameType.BETA){
            winningCondition = new WinningConditionBetaCiv(this);
            ageing = new AgeingBetaCiv(this);
        }
        playerInTurn = Player.RED;
        age = GameConstants.STARTING_AGE;
        // Creates an 16x16 worldMap of plains through a nested loop.
        for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
            for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
                worldMap.put(new Position(i, j), new TileImpl(GameConstants.PLAINS));
            }
        }

        // Places the cities in the worldMap
        cityMap.put(GameConstants.RED_CITY_POSITION, new CityImpl(Player.RED));
        cityMap.put(GameConstants.BLUE_CITY_POSITION, new CityImpl(Player.BLUE));
        // Places the unique spots for oceans, hills and mountains and overrides the positions in the worldMap.
        worldMap.put(GameConstants.OCEAN_POSITION, new TileImpl(GameConstants.OCEANS));
        worldMap.put(GameConstants.HILLS_POSITION, new TileImpl(GameConstants.HILLS));
        worldMap.put(GameConstants.MOUNTAINS_POSITION, new TileImpl(GameConstants.MOUNTAINS));
        // places the initial units on the unitMap
        unitMap.put(GameConstants.ARCHER_POSITION_RED, new UnitImpl(GameConstants.ARCHER, Player.RED));
        unitMap.put(GameConstants.LEGION_POSITION_BLUE, new UnitImpl(GameConstants.LEGION, Player.BLUE));
        unitMap.put(GameConstants.SETTLER_POSITION_RED, new UnitImpl(GameConstants.SETTLER, Player.RED));
    }

    /**
     * ====== ACCESOR METHODS ===========================================
     */

    public TileImpl getTileAt(Position p) {
        return worldMap.get(p);
    }

    public UnitImpl getUnitAt(Position p) {
        return unitMap.get(p);
    }

    public CityImpl getCityAt(Position p) {
        return cityMap.get(p);
    }

    public Player getPlayerInTurn() {
        return playerInTurn;
    }

    public Player getWinner() {
        return winningCondition.getWinner();
    }

    public int getAge() {
        return age;
    }

    /**
     * ====== MUTATOR METHODS ===========================================
     */

    public boolean moveUnit(Position from, Position to) {
        UnitImpl temporaryUnitHolder = new UnitImpl(getUnitAt(from).getTypeString(), getUnitAt(from).getOwner());
        if (getTileAt(to).equals(getTileAt(GameConstants.MOUNTAINS_POSITION)) || getTileAt(to).equals(getTileAt(GameConstants.OCEAN_POSITION))) {
            return false;
        }
        if (!getPlayerInTurn().equals(temporaryUnitHolder.getOwner())) {
            return false;
        }

        if (legalMove(from, to)) {
            conquerCity(to);
            unitMap.remove(from);
            unitMap.put(to, temporaryUnitHolder);
            unitMap.get(to).setMoveCount(0);
        }
        return true;
    }

    private void conquerCity(Position toConquer) {
        if (getCityAt(toConquer) != null && getCityAt(toConquer).getOwner() != playerInTurn) {
            getCityAt(toConquer).setOwner(playerInTurn);
        }

    }

    public boolean legalMove(Position from, Position to) {
        if (Math.abs(from.getColumn() - to.getColumn()) <= getUnitAt(from).getMoveCount() && Math.abs(from.getRow() - to.getRow()) <= getUnitAt(from).getMoveCount()) {
            return true;
        } else {
            return false;
        }
    }

    public void endOfTurn() {
        if (getPlayerInTurn() == Player.RED) {
            playerInTurn = Player.BLUE;
        } else {
            playerInTurn = Player.RED;
            endOfRound();
            age += ageing.calculateAge();
        }
    }

    private void endOfRound() {

        getCityAt(GameConstants.RED_CITY_POSITION).addTreasury(GameConstants.PRODUCTION_FIXED6);
        getCityAt(GameConstants.BLUE_CITY_POSITION).addTreasury(GameConstants.PRODUCTION_FIXED6);
        if (getCityAt(GameConstants.RED_CITY_POSITION).getTreasury() >= GameConstants.UNIT_COST) {
            legalProduction(new UnitImpl(getCityAt(GameConstants.RED_CITY_POSITION).getProduction(), Player.RED), GameConstants.RED_CITY_POSITION);
            getCityAt(GameConstants.RED_CITY_POSITION).addTreasury(-GameConstants.UNIT_COST);
        }

        if (getCityAt(GameConstants.BLUE_CITY_POSITION).getTreasury() >= GameConstants.UNIT_COST) {
            legalProduction(new UnitImpl(getCityAt(GameConstants.BLUE_CITY_POSITION).getProduction(), Player.BLUE), GameConstants.BLUE_CITY_POSITION);
            getCityAt(GameConstants.BLUE_CITY_POSITION).addTreasury(-GameConstants.UNIT_COST);
        }
        resetMoveCount();
    }

    private void legalProduction(UnitImpl chosenUnit, Position inCity) {
        if (getUnitAt(inCity) == null) {
            unitMap.put(inCity, chosenUnit);
        } else {
            for (Position p : Utility.get8neighborhoodOf(inCity)) {
                if (getUnitAt(p) == null && getTileAt(p).getTypeString() != GameConstants.MOUNTAINS && getTileAt(p).getTypeString() != GameConstants.OCEANS) {
                    unitMap.put(p, chosenUnit);
                    break;
                }
            }
        }
    }

    private void resetMoveCount() {
        // Itterares the entire unitMap and checks whether or not there is a unit.
        // If there is a unit on the position, it's moveCount is reset to 1.
        for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
            for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
                if (getUnitAt(new Position(i, j)) != null) {
                    unitMap.get(new Position(i, j)).setMoveCount(1);
                }
            }
        }
    }


    public void changeWorkForceFocusInCityAt(Position p, String balance) {
    }

    public void changeProductionInCityAt(Position p, String unitType) {
        getCityAt(p).setUnitProductionFocus(unitType);
    }

    public void performUnitActionAt(Position p) {
    }

}
