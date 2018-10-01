package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;

public class TestZetaCiv {
    private GameImpl game;
    private GameConstants gameConstants;

    @Before // Before is run before every @Test
    public void setUp() {
        game = new GameImpl(new ZetaCivFactory());
    }

    @Test
    public void isBefore20RoundsShouldReturnTrueWhenTheGameStarts() {
        assertThat(game.isBefore20Rounds(), is(true));
    }

    @Test
    public void isBefore20RoundsShouldReturnTrueOnThe20Round() {
        for (int i = 0; i < 40; i++) {
            game.endOfTurn();
        }
        assertThat(game.isBefore20Rounds(), is(true));
    }

    @Test
    public void isBefore20RoundsShouldReturnFalseAfterThe20Round() {
        for (int i = 0; i < 42; i++) {
            game.endOfTurn();
        }
        assertThat(game.isBefore20Rounds(), is(false));
    }

    @Test
    public void shouldBeBetaCivWinningConditionIfBefore20Round() {
        // Moves the redArcher to attack the blueCity and by that winning the game.
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 0));
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(new Position(3, 0), GameConstants.BLUE_CITY_POSITION);
        assertThat(game.getUnitAt(GameConstants.BLUE_CITY_POSITION).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION).getOwner(), is(game.getPlayer(GameConstants.RED)));
        assertThat(game.getWinner(), is(game.getPlayer(GameConstants.RED)));
    }

    @Test
    public void shouldNotBeBetaCivWinningConditionIfAfter20Round() {
        for (int i = 0; i < 42; i++) {
            game.endOfTurn();
        }
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 0));
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(new Position(3, 0), GameConstants.BLUE_CITY_POSITION);
        assertThat(game.getUnitAt(GameConstants.BLUE_CITY_POSITION).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION).getOwner(), is(game.getPlayer(GameConstants.RED)));
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldBeEpsilonCivWinningConditionAfter20Round() {
        for (int i = 0; i < 42; i++) {
            game.endOfTurn();
        }
        game.incrementCurrentPlayersAttackBattlesWon(3);
        assertThat(game.getWinner(), is(game.getPlayer(GameConstants.RED)));
    }

    @Test
    public void shouldBeEpsilonCivWinningConditionAfter20RoundsButOnlyIfTheRequirementsAreMet() {
        for (int i = 0; i < 42; i++) {
            game.endOfTurn();
        }
        game.incrementCurrentPlayersAttackBattlesWon(2);
        assertThat(game.getWinner(), is(nullValue()));
    }
}

