package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * @ author Troels Gjørup
 * 14-09-2018
 */
public class TestBetaCiv {
    private Game game;
    private GameConstants gameConstants;

    @Before // Before is run before every @Test
    public void setUp() {
        game = new GameImpl(Unit.GameType.BETA);

    }

    @Test
    public void gameShouldProperlyTestBetaImplementation() {
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION).getOwner(), is(Player.BLUE));
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getOwner(), is(Player.RED));
        for (int i = 0; i < 20; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-3000));
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void redShouldBeAbleToConquerBlueCity() {
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 1));
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(new Position(3, 1), GameConstants.BLUE_CITY_POSITION);
        assertThat(game.getUnitAt(gameConstants.BLUE_CITY_POSITION), is(notNullValue()));
        assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION).getOwner(), is(Player.RED));

    }

    @Test
    public void blueShouldBeAbleToConquerRedCity() {
        game.endOfTurn();
        game.moveUnit(GameConstants.LEGION_POSITION_BLUE, new Position(2, 1));
        assertThat(game.getUnitAt(new Position(2, 1)).getTypeString(), is(GameConstants.LEGION));
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(new Position(2, 1), GameConstants.RED_CITY_POSITION);
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getOwner(), is(Player.BLUE));

    }

    @Test
    public void redShouldWinAfterConqueringAllCities() {
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 1));
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(new Position(3, 1), GameConstants.BLUE_CITY_POSITION);
        assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getOwner(), is(Player.RED));
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void blueShouldWinAfterConqueringAllCities() {
        game.endOfTurn();
        game.moveUnit(GameConstants.LEGION_POSITION_BLUE, new Position(2, 1));
        assertThat(game.getUnitAt(new Position(2, 1)).getTypeString(), is(GameConstants.LEGION));
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(new Position(2, 1), GameConstants.RED_CITY_POSITION);
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getOwner(), is(Player.BLUE));
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION).getOwner(), is(Player.BLUE));
        assertThat(game.getWinner(), is(Player.BLUE));
    }

    @Test
    public void betweenYear4000And100BCAgeShouldIncrementWith100() {
        assertThat(game.getAge(), is(gameConstants.STARTING_AGE));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getAge(), is(-3900));
    }

    @Test
    public void after78EndOfTurnsShouldBe100BC() {
        assertThat(game.getAge(), is(GameConstants.STARTING_AGE));
        for (int i = 0; i < 78; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-100));
    }

    @Test
    public void untilAge100BCAgeShouldIncrementBy100Years() {
        assertThat(game.getAge(), is(GameConstants.STARTING_AGE));
        for (int i = 0; i < 78; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-100));

    }

    @Test
    public void atAge100BCShouldincrementYearwith99() {
        for (int i = 0; i < 80; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-1));
    }

    @Test
    public void atAge1BCShouldIncrementWith2Years() {
        for (int i = 0; i < 82; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(1));
    }

    @Test
    public void atAge1ShouldIncrementWith49() {
        for (int i = 0; i < 84; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(50));
    }

    @Test
    public void atAge50ShouldIncrementYearWith50() {
        for (int i = 0; i < 86; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(100));
    }

    @Test
    public void atAge1750ShouldIncrementWith25Years() {
        for (int i = 0; i < 154; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(1775));
    }

    @Test
    public void atAge1900ShouldIncrementWith5Years() {
        for (int i = 0; i < 166; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(1905));
    }

    @Test
    public void after1970ShouldIncrementWith1Year() {
        for (int i = 0; i < 194; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(1971));
    }
}
