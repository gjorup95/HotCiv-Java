package hotciv.standard;

import hotciv.framework.*;

import java.util.*;
import java.lang.Math;

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
    private AttackingStrat attackingStrat;
    private int noOfRounds;
    private Factory factory;


    /**
     * HashMaps that together make up the World in the Game.
     */
    Map<Position, TileImpl> worldMap = new HashMap<>();
    Map<Position, CityImpl> cityMap = new HashMap<>();
    Map<Position, UnitImpl> unitMap = new HashMap<>();
    // Map for Players
    Map<Integer, Player> playerMap = new TreeMap<>();

    /**
     * Constructor
     */

    public GameImpl(Factory factory) {
        this.factory = factory;
        this.winningCondition = factory.createWinningCondition(this);
        this.ageing = factory.createAgeingStrategy();
        this.worldCreator = factory.createWorldCreator(this);
        this.unitActions = factory.createUnitActionsStrategy(this);
        this.attackingStrat = factory.createAttackingStrat(this);
        playerInTurn = playerMap.get(GameConstants.RED);
        age = GameConstants.STARTING_AGE;
        noOfRounds = 0;
    }

    /**
     * ====== ACCESOR METHODS ===========================================
     */

    public int getAge() {
        return age;
    }

    public int getCurrentPlayersAttackingBattlesWon() {
        return playerInTurn.getAttackingBattlesWon();
    }

    public Player getPlayer(Integer player) {
        return playerMap.get(player);
    }

    public Player getPlayerInTurn() {
        return playerInTurn;
    }

    public Player getWinner() {
        return winningCondition.getWinner();
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

    public Map<Integer, Player> getPlayerMap() {
        return playerMap;
    }

    public Collection<CityImpl> getCityMapValues() {
        return cityMap.values();
    }


    /**
     * ====== MUTATOR METHODS ===========================================
     */

    public void incrementCurrentPlayersAttackBattlesWon(int battlesWon) {
        playerInTurn.setAttackingBattlesWon(playerInTurn.getAttackingBattlesWon() + battlesWon);
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
    // TODO HOW to implement the adding and removing of units related to attacking? ask TA.

    /**
     * Creates a new unit on the destination and removes the old unit. The new unit's moveCount is reduced by a static 1, since only ONE tile can
     * be moved at a time.
     */
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

    /**
     * First checks whether the unit has sufficient moveCount to move to the destination. Returns false if that's the case.
     */
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
        if (getPlayerInTurn().equals(getPlayer(GameConstants.RED))) {
            playerInTurn = getPlayer(GameConstants.BLUE);
        } else {
            playerInTurn = getPlayer(GameConstants.RED);
            endOfRound();
            age += ageing.calculateAge(getAge());
        }
    }

    private void endOfRound() {
        addTreasuryInAllCities();
        buyUnitsInAllCitiesForAllPlayers();
        resetMoveCount();
        noOfRounds++;
    }

    private void addTreasuryInAllCities() {
        ArrayList<CityImpl> tempCityValueList = new ArrayList<>(getCityMapValues());
        for (CityImpl c : tempCityValueList) {
            c.addTreasury(GameConstants.PRODUCTION_FIXED6);
        }
    }

    /**
     * Uses a double for-loop to itterate the cityMap and buy units in that city, if the city have accumulated enough production.
     */
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

    /**
     * Determines where to place the unit that was purchased by the city.
     */
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


    /**
     * Method used in the WinningConditionAlternating subclass
     */
    public boolean isBefore20Rounds() {
        if (noOfRounds <= 20) {
            return true;
        }
        return false;
    }

    /**
     * Methods from the  AttackingStrat Interface
     */
    public int calculateAttackerStr(Position from) {
        return attackingStrat.calculateAttackerStr(from);
    }

    public int calculateDefensiveStr(Position to) {
        return attackingStrat.calculateDefensiveStr(to);
    }

    public int attackResult(Position from, Position to) {
        return attackingStrat.attackResult(from, to);
    }

    public boolean attack(Position from, Position to) {
        return attackingStrat.attack(from, to);
    }
}


