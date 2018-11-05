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
    private GameFactory gameFactory;
    private CityHandlingStrategy cityHandlingStrategy;
    private int redBattlesWon = 0;
    private int blueBattlesWon = 0;
    ArrayList<GameObserver> observers = new ArrayList<>();

    /**
     * HashMaps that together make up the World in the Game.
     */
    Map<Position, TileImpl> worldMap = new HashMap<>();
    Map<Position, CityImpl> cityMap = new HashMap<>();
    Map<Position, UnitImpl> unitMap = new HashMap<>();
    // Map for Players
    public final Map<String, Integer> unitPriceList = new HashMap<>();


    /**
     * Constructor
     */

    public GameImpl(GameFactory gameFactory) {
        //TODO This should be refactored with a strategy patteren for the variablity of x number of players.
        unitPriceList.put(GameConstants.ARCHER, GameConstants.ARCHER_COST);
        unitPriceList.put(GameConstants.LEGION, GameConstants.ARCHER_COST);
        unitPriceList.put(GameConstants.SETTLER, GameConstants.ARCHER_COST);
        unitPriceList.put(GameConstants.BOMB, GameConstants.BOMB_COST);
        this.gameFactory = gameFactory;
        this.winningCondition = gameFactory.createWinningCondition(this);
        this.ageing = gameFactory.createAgeingStrategy();
        this.worldCreator = gameFactory.createWorldCreator(this);
        this.unitActions = gameFactory.createUnitActionsStrategy(this);
        this.attackingStrat = gameFactory.createAttackingStrat(this);
        this.cityHandlingStrategy = gameFactory.createCityHandlingStrategy(this);
        playerInTurn = Player.RED;
        age = GameConstants.STARTING_AGE;
        noOfRounds = 0;
    }

    /**
     * ====== ACCESOR METHODS ===========================================
     */
    public int getUnitCost(String unitType) {
        return unitPriceList.get(unitType);
    }

    public int getNoOfRounds() {
        return noOfRounds;
    }

    public int getAge() {
        return age;
    }

    public int getCurrentPlayersAttackingBattlesWon() {
        if (playerInTurn == Player.RED){
            return getRedBattlesWon();
        }
        if (playerInTurn == Player.BLUE){
            return getBlueBattlesWon();
        }
        return -1;
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


    public Collection<CityImpl> getCityMapValues() {
        return cityMap.values();
    }



    /**
     * ====== MUTATOR METHODS ===========================================
     */

    public void resetAttackingBattlesForAllPlayers() {
        setRedBattlesWon(0);
        setBlueBattlesWon(0);

    }

    public void incrementCurrentPlayersAttackBattlesWon(int battlesWon) {
        if (playerInTurn == Player.RED){
            setRedBattlesWon(redBattlesWon + battlesWon);
        }
        if (playerInTurn == Player.BLUE){
            setBlueBattlesWon(blueBattlesWon + battlesWon);
        }
    }

    public void addUnit(Position placeUnitAt, String unitType, Player owner) {
        unitMap.put(placeUnitAt, new UnitImpl(unitType, owner));
    }

    public void removeCity(Position p) {
        cityMap.remove(p);
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

    /**
     * Creates a new unit on the destination and removes the old unit. The new unit's moveCount is reduced by a static 1, since only ONE tile can
     * be moved at a time.
     */
    public boolean moveUnit(Position from, Position to) {
        if (isLegalMove(from, to)) {
            if (attack(from, to)) {
                conquerCity(to);
                addUnit(to, getUnitAt(from).getTypeString(), getUnitAt(from).getOwner());
                /** Making sure that the moveCount is decremented by 1 */
                decrementMoveCount(from, to);
                removeUnit(from);
                getObservers().forEach(gameObserver -> gameObserver.worldChangedAt(from));
                getObservers().forEach(gameObserver -> gameObserver.worldChangedAt(to));
                return true;
            }
        }
        return false;
    }

    /**
     * First checks whether the unit has sufficient moveCount to move to the destination. Returns false if that's the case.
     */
    public boolean isLegalMove(Position from, Position to) {
        if (getUnitAt(from).getTypeString() != GameConstants.BOMB) {
            if (tileIsNotLegal(to)) {
                return false;
            }
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
        if (unitHasSufficientMoveCount) {

            return true;
        }
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
        if (getPlayerInTurn().equals(Player.RED)) {
            playerInTurn = Player.BLUE;
        }
        else {
            playerInTurn = Player.RED;
            endOfRound();
            age += ageing.calculateAge(getAge());
        }
        getObservers().forEach(gameObserver -> gameObserver.turnEnds(playerInTurn,age));
    }

    private void endOfRound() {
        addTreasuryInAllCities();
        buyUnitsInAllCitiesForAllPlayers();
        resetMoveCount();
        noOfRounds++;
        getObservers().forEach(gameObserver -> gameObserver.worldChangedAt(new Position(0,0)));
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
                    if (getCityAt(new Position(i, j)).getTreasury() >= getUnitCost(getCityAt(new Position(i, j)).getProduction())) {
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
                    //TODO This should be refactored so that we are able to retrieve the initial movecount for all units somewhere and update units value to that
                    if (getUnitAt(new Position(i, j)).getTypeString() == GameConstants.BOMB) {
                        getUnitAt(new Position(i, j)).setMoveCount(2);
                    }
                }
            }
        }
    }

    public void changeWorkForceFocusInCityAt(Position p, String balance) {
    }

    public int calculateWorkforceProduction(Position city) {
        return cityHandlingStrategy.calculateWorkforceProduction(city);
    }

    public void changeProductionInCityAt(Position p, String unitType) {
        getCityAt(p).setUnitProductionFocus(unitType);
    }

    public void performUnitActionAt(Position p) {
        unitActions.performAction(p);
    }

    public ArrayList<GameObserver> getObservers() {
        return observers;
    }

    @Override
    public void addObserver(GameObserver observer) {
        observers.add(observer);

    }

    @Override
    public void setTileFocus(Position position) {
        getObservers().forEach(gameObserver -> gameObserver.tileFocusChangedAt(position));

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
        getCityAt(p).addTreasury(-getUnitCost(getCityAt(p).getProduction()));
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

    public int getBlueBattlesWon() {
        return blueBattlesWon;
    }

    public void setBlueBattlesWon(int blueBattlesWon) {
        this.blueBattlesWon = blueBattlesWon;
    }

    public int getRedBattlesWon() {
        return redBattlesWon;
    }

    public void setRedBattlesWon(int redBattlesWon) {
        this.redBattlesWon = redBattlesWon;
    }
}