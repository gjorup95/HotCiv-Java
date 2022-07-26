package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestEpsilonCiv {

    private GameImpl game;

    /**
     * Fixture for alphaciv testing.
     */

    @Before // Before is run before every @Test
    public void setUp() {
        game = new GameImpl(new EpsilonCivGameFactory());
    }


    @Test
    public void shouldBeAbleToIncrementAttackingBattleWon() {
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        assertThat(game.getCurrentPlayersAttackingBattlesWon(), is(0));
        game.incrementCurrentPlayersAttackBattlesWon(1);
        assertThat(game.getCurrentPlayersAttackingBattlesWon(), is(1));
        game.incrementCurrentPlayersAttackBattlesWon(-1);
        assertThat(game.getCurrentPlayersAttackingBattlesWon(), is(0));
    }

    @Test
    public void shouldIterateEnumClassProperlyAndReturnRedWinner() {
        game.incrementCurrentPlayersAttackBattlesWon(3);
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void shouldNotReturnAWinnerIfThereIsNone() {
        game.incrementCurrentPlayersAttackBattlesWon(2);
        assertThat(game.getRedBattlesWon(), is(2));
        game.endOfTurn();
        game.incrementCurrentPlayersAttackBattlesWon(2);
        assertThat(game.getBlueBattlesWon(), is(2));
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldProperlyPlusAttStrAttackingForUnitFromFriendlyUnits() {
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 1));
        assertThat(game.getUnitAt(new Position(3, 1)), is(notNullValue()));
        // adding an unit to extend att by 1
        game.addUnit(new Position(3, 0), GameConstants.ARCHER, Player.RED);
        assertThat(game.calculateAttackerStr(new Position(3, 1)), is(2));
    }

    @Test
    public void shouldNotAddAttStrForUnfriendlyUnitsToAttackingUnit() {
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 1));
        assertThat(game.getUnitAt(new Position(3, 1)), is(notNullValue()));
        // adding an unit to extend att by 1
        game.addUnit(new Position(3, 0), GameConstants.ARCHER, Player.BLUE);
        assertThat(game.calculateAttackerStr(new Position(3, 1)), is(1));
    }

    @Test
    public void shouldAccountForHillsAttMultiplier() {
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 1));
        assertThat(game.getUnitAt(new Position(3, 1)), is(notNullValue()));
        game.addTile(new Position(3, 1), GameConstants.HILLS);
        assertThat(game.getTileAt(new Position(3, 1)).getTypeString(), is(GameConstants.HILLS));
        assertThat(game.calculateAttackerStr(new Position(3, 1)), is(2));
    }

    @Test
    public void shouldAccountForForestAttMultiplier() {
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 1));
        assertThat(game.getUnitAt(new Position(3, 1)), is(notNullValue()));
        game.addTile(new Position(3, 1), GameConstants.FOREST);
        assertThat(game.getTileAt(new Position(3, 1)).getTypeString(), is(GameConstants.FOREST));
        assertThat(game.calculateAttackerStr(new Position(3, 1)), is(2));
    }

    @Test
    public void shouldAccountForCityAttMultiplier() {
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 1));
        assertThat(game.getUnitAt(new Position(3, 1)), is(notNullValue()));
        game.addCity(new Position(3, 1), Player.RED);
        assertThat(game.getCityAt(new Position(3, 1)), is(notNullValue()));
        assertThat(game.calculateAttackerStr(new Position(3, 1)), is(3));
    }

    // Seeing that the calculation of Defensive and Attacking Str is 100% similar only few test will be produced for the calculation.
    @Test
    public void shouldAccountForFriendlyDefendUnits() {
        game.addUnit(new Position(3, 3), GameConstants.LEGION, Player.BLUE);
        assertThat(game.calculateDefensiveStr(GameConstants.LEGION_POSITION_BLUE), is(2));
    }

    @Test
    public void shouldAccountForHillsOnDefensivePosition() {
        game.addTile(GameConstants.LEGION_POSITION_BLUE, GameConstants.HILLS);
        assertThat(game.getTileAt(GameConstants.LEGION_POSITION_BLUE).getTypeString(), is(GameConstants.HILLS));
        assertThat(game.calculateDefensiveStr(GameConstants.LEGION_POSITION_BLUE), is(2));
    }

    @Test
    public void shouldAccountForDefensiveCityMultiply() {
        game.addCity(GameConstants.LEGION_POSITION_BLUE, Player.BLUE);
        assertThat(game.calculateDefensiveStr(GameConstants.LEGION_POSITION_BLUE), is(3));
    }

    @Test
    public void shouldAccountForMultipleDefensiveUnitsAndCityMultiplier() {
        game.addCity(GameConstants.LEGION_POSITION_BLUE, Player.BLUE);
        game.addUnit(new Position(3, 3), GameConstants.LEGION, Player.BLUE);
        game.addUnit(new Position(2, 3), GameConstants.LEGION, Player.BLUE);
        assertThat(game.calculateDefensiveStr(GameConstants.LEGION_POSITION_BLUE), is(9));
    }

    @Test
    public void redShouldWinMostOfTheTimeWhenHavingSuperiorAttackToDefenseRatio() {
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 1));
        assertThat(game.getUnitAt(new Position(3, 1)).getTypeString(), is(GameConstants.ARCHER));
        game.addUnit(new Position(3, 0), GameConstants.ARCHER, Player.RED);
        assertThat(game.calculateDefensiveStr(GameConstants.LEGION_POSITION_BLUE), is(1));
        assertThat(game.calculateAttackerStr(new Position(3, 1)), is(2));
        int redWins = 0;
        int blueWins = 0;
        for (int i = 0; i < 100; i++) {
            if (game.attackResult(new Position(3, 1), GameConstants.LEGION_POSITION_BLUE) > 0) {
                redWins += 1;
            } else {
                blueWins += 1;
            }
        }
        assertThat(redWins > blueWins, is(true));
        assertThat(redWins > 60, is(true));
    }

    /**
     * Testing with Fixed Test stub
     */
    @Test
    public void testingWithTheTestStubOneUnit() {
        game = new GameImpl(new TestGameFactoryEpsilon());
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 1));
        assertThat(game.getUnitAt(new Position(3, 1)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.attackResult(new Position(3, 1), GameConstants.LEGION_POSITION_BLUE), is(0));
    }

    @Test
    public void ShouldReturn2When2AlliedUnits() {
        game = new GameImpl(new TestGameFactoryEpsilon());
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 1));
        assertThat(game.getUnitAt(new Position(3, 1)).getTypeString(), is(GameConstants.ARCHER));
        game.addUnit(new Position(3, 0), GameConstants.ARCHER, Player.RED);
        game.addUnit(new Position(4, 2), GameConstants.ARCHER, Player.RED);
        assertThat(game.attackResult(new Position(3, 1), GameConstants.LEGION_POSITION_BLUE), is(2));
    }

    @Test
    public void shouldRemoveAttackerIfAttackIsUnsuccesful() {
        game = new GameImpl(new TestGameFactoryEpsilon());
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 1));
        assertThat(game.getUnitAt(new Position(3, 1)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(3, 1)).getAttackingStrength(), is(1));
        assertThat(game.getUnitAt(new Position(3, 1)).getMoveCount(), is(0));
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        // making it so that the attacking unit is mathematically not able to win
        game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).setDefensiveStrength(2);
        game.moveUnit(new Position(3, 1), GameConstants.LEGION_POSITION_BLUE);
        assertThat(game.getUnitAt(new Position(3, 1)), is(nullValue()));
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).getOwner(), is(Player.BLUE));

    }

    @Test
    public void shouldOverwriteDefenderAndMoveAttackerWhenAttackIsSuccesful() {
        game = new GameImpl(new TestGameFactoryEpsilon());
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 1));
        assertThat(game.getUnitAt(new Position(3, 1)).getTypeString(), is(GameConstants.ARCHER));
        game.getUnitAt(new Position(3, 1)).setAttackingStrength(2);

        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);

        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).getDefensiveStrength(), is(1));
        game.moveUnit(new Position(3, 1), GameConstants.LEGION_POSITION_BLUE);
        assertThat(game.getUnitAt(new Position(3, 1)), is(nullValue()));
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldIncrementAPlayersBattlesWonIfHeWinsAnAttack() {
        game = new GameImpl(new TestGameFactoryEpsilon());
        assertThat(game.getRedBattlesWon(), is(0));
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 1));
        assertThat(game.getUnitAt(new Position(3, 1)).getTypeString(), is(GameConstants.ARCHER));
        game.getUnitAt(new Position(3, 1)).setAttackingStrength(2);
        assertThat(game.getPlayerInTurn(), is(Player.RED));

        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);

        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(new Position(3, 1), GameConstants.LEGION_POSITION_BLUE);
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).getOwner(), is(Player.RED));
        assertThat(game.getRedBattlesWon(), is(1));

    }

    @Test
    public void shouldNotIncrementBattlesWonWhenMovingUnitToEmptyTile() {
        game = new GameImpl(new TestGameFactoryEpsilon());
        assertThat(game.getRedBattlesWon(), is(0));
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 1));
        assertThat(game.getRedBattlesWon(), is(0));


    }

    @Test
    public void shouldBeAbleToAttackEmptyTiles() {
        game = new GameImpl(new TestGameFactoryEpsilon());
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 0));
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(3, 0)), is(notNullValue()));
        game.moveUnit((new Position(3, 0)), GameConstants.BLUE_CITY_POSITION);
        assertThat(game.getUnitAt(GameConstants.BLUE_CITY_POSITION), is(notNullValue()));
        assertThat(game.getUnitAt(GameConstants.BLUE_CITY_POSITION).getTypeString(), is(GameConstants.ARCHER));
    }
}




