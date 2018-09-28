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
    private int noOfRounds;

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
        worldCreator = new WorldCreatorAlphaCiv(this);
        unitActions = new UnitActionsAlphaCiv();
        setUpGame(version);
        playerInTurn = Player.RED;
        age = GameConstants.STARTING_AGE;
        noOfRounds = 0;
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
                worldCreator = new WorldCreatorDeltaCiv(this);
                break;
            case EPSILON:
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

    public int getAttackingBattlesWon() {
        return playerInTurn.getAttackingBattlesWon();
    }

    /**
     * ====== MUTATOR METHODS ===========================================
     */

    public void incrementAttackBattlesWon(int battlesWon) {
        playerInTurn.setAttackingBattlesWon(battlesWon);
    }

    public void addUnit(Position placeUnitAt, String unitType, Player owner) {
        unitMap.put(placeUnitAt, new UnitImpl(unitType, owner));
    }

    public void removeUnit(Position p) {
        unitMap.remove(p);
    }

    public void addCity(Position p, Player owner) {
        cityMap.put(p, new CityImpl(owner));
    }

    public void addTile(Position p, String tileType) {
        worldMap.put(p, new TileImpl(tileType));
    }

    /** Creates a new unit on the destination and removes the old unit. The new unit's moveCount is reduced by a static 1, since only ONE tile can
     *  be moved at a time. */
    public boolean moveUnit(Position from, Position to) {
        if (isLegalMove(from, to)) {
            conquerCity(to);
            addUnit(to, getUnitAt(from).getTypeString(), getUnitAt(from).getOwner());
            /** Making sure that the moveCount is decremented by 1 */
            decrementMoveCount(from, to);
            removeUnit(from);
            return true;

        }
        return false;
    }

   /** First checks whether the unit has sufficient moveCount to move to the destination. Returns false if that's the case. */
    public boolean isLegalMove(Position from, Position to) {
        if (tileIsNotLegal(to)) {
            return false;
        }
        if (playerInTurnIsNotOwnerOfUnit(from)) {
            return false;
        }
        if (unitIsNotNull(to) && !playerInTurnIsNotOwnerOfUnit(to)) {
            return false;
        }
        if (!calculateLegalMove(from, to)) {
            return false;
        }
        boolean unitHasSufficientMoveCount = getUnitAt(from).getMoveCount() >= 1;
        if (unitHasSufficientMoveCount) return true;
        return false;
    }

    /**
     * Checks if the move is legal. The column and row difference must be less than 2, since units can only move one tile at a time. Also
     * the from position cannot be equal to the to position, since its illegal to move on the same tile as the original position.
     */
    public boolean calculateLegalMove(Position from, Position to) {
        int columnDifference = Math.abs(from.getColumn() - to.getColumn());
        int rowDifference = Math.abs(from.getRow() - to.getRow());
        if (columnDifference < 2 && rowDifference < 2 && !from.equals(to)) {
            return true;
        } else
            return false;
    }

    private void conquerCity(Position toConquer) {
        winningCondition.conquerCity(toConquer);
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
        noOfRounds ++;
    }

    private void addTreasuryInAllCities() {
        ArrayList<CityImpl> tempCityValueList = new ArrayList<>(getCityMapValues());
        for (CityImpl c : tempCityValueList) {
            c.addTreasury(GameConstants.PRODUCTION_FIXED6);
        }
    }

    /** Uses a double for-loop to itterate the cityMap and buy units in that city, if the city have accumulated enough production.*/
    private void buyUnitsInAllCitiesForAllPlayers() {
        for (int i = 0; i < GameConstants.WORLDSIZE; i++)
            for (int j = 0; j < GameConstants.WORLDSIZE; j++)
                if (cityIsNotNull(new Position(i, j))) {
                    if (getCityAt(new Position(i, j)).getTreasury() >= GameConstants.UNIT_COST) {
                        placeUnitsForProduction(new UnitImpl(getCityAt(new Position(i, j)).getProduction(), (getCityAt(new Position(i, j)).getOwner())), new Position(i, j));
                        addTreasury(new Position(i, j));
                    }
                }
    }

    /** Determines where to place the unit that was purchased by the city. */
    private void placeUnitsForProduction(UnitImpl chosenUnit, Position insideCity) {
        if (!unitIsNotNull(insideCity)) {
            addUnit(insideCity, chosenUnit.getTypeString(), chosenUnit.getOwner());
        } else {
            for (Position p : Utility.get8neighborhoodOf(insideCity)) {
                if (!unitIsNotNull(p) && !tileIsNotLegal(p)) {
                    addUnit(p, chosenUnit.getTypeString(), chosenUnit.getOwner());
                    break;
                }
            }
        }
    }

    private void resetMoveCount() {
        for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
            for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
                if (unitIsNotNull(new Position(i, j))) {
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

    public boolean unitIsNotNull(Position p) {
        if (getUnitAt(p) != null) {
            return true;
        }
        return false;
    }

    public boolean cityIsNotNull(Position p) {
        if (getCityAt(p) != null) {
            return true;
        }
        return false;
    }

    private void decrementMoveCount(Position from, Position to) {
        getUnitAt(to).setMoveCount(getUnitAt(from).getMoveCount() - 1);
    }

    private void addTreasury(Position p) {
        getCityAt(p).addTreasury(-GameConstants.UNIT_COST);
    }

    public boolean isBefore20Rounds() {
        if (noOfRounds <= 20) {
            return true;
        }
        return false;
    }
}


