package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;

public class TestZetaCiv {
    private GameImpl game;

    @Before // Before is run before every @Test
    public void setUp() {
        game = new GameImpl(new ZetaCivGameFactory());
    }

    @Test
    public void isBefore20RoundsShouldReturnTrueWhenTheGameStarts() {
        assertThat(game.isBefore20Rounds(), is(true));
    }

    @Test
    public void isBefore20RoundsShouldReturnTrueOnThe20Round() {
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        for (int i = 0; i < 40; i++) {
            game.endOfTurn();
        }
        assertThat(game.isBefore20Rounds(), is(true));
    }

    @Test
    public void isBefore20RoundsShouldReturnFalseAfterThe20Round() {
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        for (int i = 0; i < 42; i++) {
            game.endOfTurn();
        }
        assertThat(game.isBefore20Rounds(), is(false));
    }

    @Test
    public void shouldBeBetaCivWinningConditionIfBefore20Round() {
        // Moves the redArcher to attack the blueCity and by that winning the game.
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 0));
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.moveUnit(new Position(3, 0), GameConstants.BLUE_CITY_POSITION);
        assertThat(game.getUnitAt(GameConstants.BLUE_CITY_POSITION).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION).getOwner(), is(Player.RED));
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void shouldNotBeBetaCivWinningConditionIfAfter20Round() {
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.LEGION);
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.LEGION);
        for (int i = 0; i < 41; i++) {
            game.endOfTurn();
        }
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 0));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(GameConstants.BLUE_CITY_POSITION).getTypeString(), is(GameConstants.LEGION));
        game.moveUnit(new Position(3, 0), GameConstants.BLUE_CITY_POSITION);
        assertThat(game.getUnitAt(new Position(4,1)), is(notNullValue()));
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION).getOwner(), is(Player.BLUE));
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldBeEpsilonCivWinningConditionAfter20Round() {
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.incrementCurrentPlayersAttackBattlesWon(3);
        for (int i = 0; i < 40; i++) {
            game.endOfTurn();
        }
        game.endOfTurn();
        game.endOfTurn();
        game.getWinner();
        game.incrementCurrentPlayersAttackBattlesWon(3);
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void shouldBeEpsilonCivWinningConditionAfter20RoundsButOnlyIfTheRequirementsAreMet() {
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        for (int i = 0; i < 42; i++) {
            game.endOfTurn();
        }
        game.incrementCurrentPlayersAttackBattlesWon(2);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldBeEpsilonCivAttackingStratAfter20Rounds() {
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.incrementCurrentPlayersAttackBattlesWon(3);
        for (int i = 0; i < 50; i++) {
            game.endOfTurn();
        }
        // red units
        game.addUnit(new Position(5, 1), GameConstants.ARCHER, Player.RED);
        game.addUnit(new Position(6, 1), GameConstants.ARCHER, Player.RED);
        game.addUnit(new Position(5, 3), GameConstants.ARCHER, Player.RED);
        game.addUnit(new Position(6, 3), GameConstants.ARCHER, Player.RED);
        game.addUnit(new Position(6, 2), GameConstants.ARCHER, Player.RED);
        // blue units
        game.addUnit(new Position(5, 2), GameConstants.LEGION, Player.BLUE);
        int redWins = 0;
        int blueWins = 0;
        for (int i = 0; i < 10000; i++) {
            if (game.attackResult(new Position(6, 2), new Position(5, 2)) > 0) {
                redWins += 1;
            } else {
                blueWins += 1;
            }
        }
        assertThat(redWins > blueWins, is(true));
        assertThat(redWins > 75, is(true));
    }
}


