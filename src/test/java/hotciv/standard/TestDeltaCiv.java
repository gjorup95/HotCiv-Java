package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * @ author Troels Gjørup, Christoffer Ashorn
 * 14-09-2018
 */
public class TestDeltaCiv {
    private GameImpl game;
    private GameConstants gameConstants;

    @Before // Before is run before every @Test
    public void setUp() {
        game = new GameImpl(GameType.DELTA);

    }


    @Test
    public void shouldProperlyImplementTheWorldMapHorizontally() {
        // Testing Horizontally
        // i will only test for a few of the first tiles as the introduced world implemented in delta
        // is generated by code handed by the course professor.
        assertThat(game.getTileAt(new Position(0, 0)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position(0, 1)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position(0, 2)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position(0, 3)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(game.getTileAt(new Position(0, 4)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(game.getTileAt(new Position(0, 5)).getTypeString(), is(GameConstants.MOUNTAINS));
        assertThat(game.getTileAt(new Position(0, 6)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(game.getTileAt(new Position(0, 7)).getTypeString(), is(GameConstants.PLAINS));

    }

    @Test
    public void shouldProperlyImplementTheWorldMapVertically() {
        // Testing Vertically
        assertThat(game.getTileAt(new Position(0, 0)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position(1, 0)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position(2, 0)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position(3, 0)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position(4, 0)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position(5, 0)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position(6, 0)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position(7, 0)).getTypeString(), is(GameConstants.OCEANS));
    }

    @Test
    public void shouldProperlyImplementTheWorldMapDiagonally() {
        assertThat(game.getTileAt(new Position(0, 0)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position(1, 1)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position(2, 2)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(game.getTileAt(new Position(3, 3)).getTypeString(), is(GameConstants.MOUNTAINS));
        assertThat(game.getTileAt(new Position(4, 4)).getTypeString(), is(GameConstants.FOREST));
        assertThat(game.getTileAt(new Position(5, 5)).getTypeString(), is(GameConstants.FOREST));
        assertThat(game.getTileAt(new Position(6, 6)).getTypeString(), is(GameConstants.OCEANS));
        assertThat(game.getTileAt(new Position(7, 7)).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void shouldProperlyImplementTheUnitMap() {
        assertThat(game.getUnitAt(new Position(5, 5)), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(5, 5)).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(new Position(5, 5)).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(new Position(4, 4)), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(4, 4)).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(new Position(4, 4)).getOwner(), is(Player.BLUE));
        assertThat(game.getUnitAt(new Position(4, 8)), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(4, 8)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(4, 8)).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldProperlyImplementTheCityMap() {
        assertThat(game.getCityAt(new Position(8, 12)), is(notNullValue()));
        assertThat(game.getCityAt(new Position(8, 12)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(4, 5)), is(notNullValue()));
        assertThat(game.getCityAt(new Position(4, 5)).getOwner(), is(Player.BLUE));
    }
}