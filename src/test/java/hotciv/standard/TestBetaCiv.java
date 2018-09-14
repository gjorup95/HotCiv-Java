package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

/**
 * @ author Troels Gjørup
 * 14-09-2018
 */
public class TestBetaCiv {
    private Game game;
    private CityImpl redCity;
    private GameConstants gameConstants;
    private WinningCondition winningConditionBeta;

    @Before // Before is run before every @Test
    public void setUp() {
        game = new GameImpl();
        redCity = new CityImpl(Player.RED);
        winningConditionBeta = new WinningConditionBetaCiv(game);
    }

    @Test
    public void gameShouldProperlyTestBetaImplementation() {
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION).getOwner(), is(Player.BLUE));
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getOwner(), is(Player.RED));
        for (int i = 0; i < 10; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-3000));
        assertThat(winningConditionBeta.getWinner(), is(nullValue()));
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
        assertThat(winningConditionBeta.getWinner(), is(Player.RED));
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
        assertThat(winningConditionBeta.getWinner(), is(Player.BLUE));
    }
}
