package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

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

    // Fields
    private Player playerInTurn;
    private int age;
    Map<Position, TileImpl> worldMap = new HashMap<>();
    Map<Position, CityImpl> cityMap = new HashMap<>();
    Map<Position, UnitImpl> unitMap = new HashMap<>();

    // Constructor
    public GameImpl() {
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
        if (getAge() == -3000) {
            return Player.RED;
        }
        return null;
    }

    public int getAge() {
        return age;
    }

    /**
     * ====== MUTATOR METHODS ===========================================
     */

    public boolean moveUnit(Position from, Position to) {
        UnitImpl unitHolder = new UnitImpl(getUnitAt(from).getTypeString(), getUnitAt(from).getOwner());
        unitMap.put(to, unitHolder);
        unitMap.remove(from);
        return false;
    }

    public void endOfTurn() {
        if (getPlayerInTurn() == Player.RED) {
            playerInTurn = Player.BLUE;
        } else {
            playerInTurn = Player.RED;
            endOfRound();
        }
        age += 100;
    }

    private void endOfRound() {
        getCityAt(GameConstants.RED_CITY_POSITION).addTreasury(GameConstants.PRODUCTION_FIXED6);
        getCityAt(GameConstants.BLUE_CITY_POSITION).addTreasury(GameConstants.PRODUCTION_FIXED6);
        unitProduction();

    }

    public void unitProduction() {
        CityImpl tempRedCity = getCityAt(GameConstants.RED_CITY_POSITION);
        CityImpl tempBlueCity = getCityAt(GameConstants.BLUE_CITY_POSITION);
        UnitImpl chosenRedUnit = new UnitImpl(getCityAt(GameConstants.RED_CITY_POSITION).getProduction(), tempRedCity.getOwner());
        UnitImpl chosenBlueUnit = new UnitImpl(getCityAt(GameConstants.BLUE_CITY_POSITION).getProduction(), tempBlueCity.getOwner());
        tempRedCity.addTreasury(-GameConstants.UNIT_COST);
        if (getUnitAt(GameConstants.RED_CITY_POSITION) == null) {
            unitMap.put(GameConstants.RED_CITY_POSITION, chosenRedUnit);

        } else {
            for (Position p : Utility.get8neighborhoodOf(GameConstants.RED_CITY_POSITION)) {
                if (getUnitAt(p) == null && getTileAt(p).getTypeString() != GameConstants.MOUNTAINS) {
                    unitMap.put(p, chosenRedUnit);
                    break;
                }

            }
        }
        if (getUnitAt(GameConstants.BLUE_CITY_POSITION) == null) {
            unitMap.put(GameConstants.BLUE_CITY_POSITION, chosenBlueUnit);

        } else {
            for (Position p : Utility.get8neighborhoodOf(GameConstants.BLUE_CITY_POSITION)) {
                if (getUnitAt(p) == null && getTileAt(p).getTypeString() != GameConstants.MOUNTAINS) {
                    unitMap.put(p, chosenBlueUnit);
                    break;
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
