package hotciv.standard;

import hotciv.framework.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
    private UnitActions unitActions;

    /**
     * HashMaps that together make up the World in the Game.
     */
    Map<Position, TileImpl> worldMap = new HashMap<>();
    Map<Position, CityImpl> cityMap = new HashMap<>();
    Map<Position, UnitImpl> unitMap = new HashMap<>();

    /**
     * Constructor
     */
    public GameImpl(GameType version) {
        winningCondition = new WinningConditionAlphaCiv(this);
        ageing = new AgeingAlphaCiv();
        worldCreator = new WorldCreatorAlphaCiv();
        worldMap.putAll(worldCreator.getWorldMap());
        cityMap.putAll(worldCreator.getCityMap());
        unitMap.putAll(worldCreator.getUnitMap());
        unitActions = new UnitActionsAlphaCiv();
        playerInTurn = Player.RED;
        age = GameConstants.STARTING_AGE;
        setUpGame(version);


    }

    private void setUpGame(GameType version) {
        switch (version) {
            case BETA:
                winningCondition = new WinningConditionBetaCiv(this);
                ageing = new AgeingBetaCiv();
                break;
            case GAMMA:
                unitActions = new UnitActionsGammaCiv(this);
                break;
            case DELTA:
                worldCreator = new WorldCreatorDeltaCiv();
                worldMap.putAll(worldCreator.getWorldMap());
                cityMap.putAll(worldCreator.getCityMap());
                unitMap.putAll(worldCreator.getUnitMap());
                break;
        }

    }

    /**
     * ====== ACCESOR METHODS ===========================================
     */

    public Collection<CityImpl> getCityMapValues() {
        return cityMap.values();
    }

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

    public void addUnit(Position placeUnitAt, String unitType, Player owner) {
        unitMap.put(placeUnitAt, new UnitImpl(unitType, owner));
    }

    public void removeUnit(Position p) {
        unitMap.remove(p);
    }

    public void addCity(Position p, Player owner) {
        cityMap.put(p, new CityImpl(owner));
    }

    public boolean moveUnit(Position from, Position to) {
        // Checks if the moveTo tile is a illegal tile type.
        if (tileIsNotLegal(to)) {
            return false;
        }
        if (playerInTurnIsNotOwnerOfUnit(from)) {
            return false;
        }

        // Checks that the unit has sufficient MoveCount to move to the destinated position.
        if (isLegalMove(from, to)) {
            conquerCity(to);
            addUnit(to, getUnitAt(from).getTypeString(), getUnitAt(from).getOwner());
            getUnitAt(to).setMoveCount(getUnitAt(from).getMoveCount() - 1);
            removeUnit(from);

        }
        return true;
    }

    private void conquerCity(Position toConquer) {
        winningCondition.conquerCity(toConquer);
    }

    // TODO ASK IF IT IS OK OR IT SHOULD BE REFACTORED.
    public boolean isLegalMove(Position from, Position to) {
        if (calculateLegalMove(from, to) && getUnitAt(from).getMoveCount() >= 1) {
            return true;
        }
        return false;
    }

    public boolean calculateLegalMove(Position from, Position to) {
        int columnDifference = Math.abs(from.getColumn() - to.getColumn());
        int rowDifference = Math.abs(from.getRow() - to.getRow());
        if (columnDifference < 2 && rowDifference < 2 && !from.equals(to)) {
            return true;
        } else
            return false;
    }

    public void endOfTurn() {
        if (getPlayerInTurn() == Player.RED) {
            playerInTurn = Player.BLUE;
        } else {
            playerInTurn = Player.RED;
            endOfRound();
            age += ageing.calculateAge(getAge());
        }
    }

    private void endOfRound() {

        addTreasuryInAllCities();
        buyUnitsInAllCitiesForAllPlayers();
        resetMoveCount();
    }

    private void addTreasuryInAllCities() {
        ArrayList<CityImpl> tempCityValueList = new ArrayList<>(getCityMapValues());
        for (CityImpl c : tempCityValueList) {
            c.addTreasury(GameConstants.PRODUCTION_FIXED6);
        }
    }

    // TODO SHOULD WE INCLUDE LOCAL BOOLEANS EVEN THOUGH THEY INCLUDE EXTRA CODE
    private void buyUnitsInAllCitiesForAllPlayers() {
        for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
            for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
                boolean cityPositionIsNotNull = getCityAt(new Position(i, j)) != null;
                if (cityPositionIsNotNull) {
                    //if (getCityAt(new Position(i, j)) != null) {
                    if (getCityAt(new Position(i, j)).getTreasury() >= GameConstants.UNIT_COST) {
                        placeUnitsForProduction(new UnitImpl(getCityAt(new Position(i, j)).getProduction(), (getCityAt(new Position(i, j)).getOwner())), new Position(i, j));
                        getCityAt(new Position(i, j)).addTreasury(-GameConstants.UNIT_COST);
                    }
                }
            }
        }

    }

    private void placeUnitsForProduction(UnitImpl chosenUnit, Position inCity) {
        if (getUnitAt(inCity) == null) {
            addUnit(inCity, chosenUnit.getTypeString(), chosenUnit.getOwner());
        } else {
            for (Position p : Utility.get8neighborhoodOf(inCity)) {
                if (getUnitAt(p) == null && !tileIsNotLegal(p)) {
                    addUnit(p, chosenUnit.getTypeString(), chosenUnit.getOwner());
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
                    getUnitAt(new Position(i, j)).setMoveCount(1);
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
        unitActions.performAction(p);
    }

    public boolean tileIsNotLegal(Position p) {
        if (getTileAt(p).getTypeString().equals(GameConstants.MOUNTAINS) || getTileAt(p).getTypeString().equals(GameConstants.OCEANS)) {
            return true;
        }
        return false;
    }

    public boolean playerInTurnIsNotOwnerOfUnit(Position unitPosition) {
        if (!getPlayerInTurn().equals(getUnitAt(unitPosition).getOwner())) {
            return true;
        }
        return false;
    }
}


