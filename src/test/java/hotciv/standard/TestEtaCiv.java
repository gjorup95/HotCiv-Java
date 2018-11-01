package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


/**
 * @ author Troels Gjørup
 * 03-10-2018
 */
public class TestEtaCiv {
    private GameImpl game;

    @Before // Before is run before every @Test
    public void setUp() {
        game = new GameImpl(new EtaCivGameFactory());
        // Setting up a test fixture map for testing
        // Red city at 1,4
        game.addCity(new Position(1,4), Player.RED);
        // Tiles
        game.addTile(new Position(0,4), GameConstants.PLAINS);
        game.addTile(new Position(0,5), GameConstants.MOUNTAINS);
        game.addTile(new Position(1,5), GameConstants.FOREST);
        game.addTile(new Position(2,5),GameConstants.HILLS);
        game.addTile(new Position(2,4), GameConstants.PLAINS);
        game.addTile(new Position(2,3), GameConstants.MOUNTAINS);
        game.addTile(new Position(1,3), GameConstants.FOREST);
        game.addTile(new Position(0,3), GameConstants.OCEANS);

    }

    @Test
    public void shouldGet6ProductionwhenCityPopIs3() {
        /** TODO IMPLEMENT ETA CIV PROPERLY */
        assertThat(game.getCityAt(new Position(1,4)).getSize(), is(1));
        game.getCityAt(new Position(1,4)).setPopulation(3);
        assertThat(game.getCityAt(new Position(1,4)).getSize(), is(3));
        assertThat(game.calculateWorkforceProduction(new Position(1,4)), is(0));

    }
}
