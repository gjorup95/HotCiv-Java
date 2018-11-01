package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * @ author Troels Gjørup, Christoffer Ashorn
 * 14-09-2018
 */
public class TestThirdPartyAdapter {
    private GameImpl game;

    @Before // Before is run before every @Test
    public void setUp() {
        game = new GameImpl(new ThirdPartyAdapterGameFactory());
    }

    @Test
    public void shouldHaveDifferentWorldMapsWhenComparingTwoGamesUsingAdapterFactory() {
        HashMap<Position, String> firstWorldMap = new HashMap<>();
        HashMap<Position, String> secondWorldMap = new HashMap<>();
        for (int r = 0; r < 16; r++) {
            for (int c = 0; c < 16; c++) {
                firstWorldMap.put(new Position(r,c), game.getTileAt(new Position(r,c)).getTypeString());
            }
        }
        game = new GameImpl(new ThirdPartyAdapterGameFactory());
        for (int r = 0; r < 16; r++) {
            for (int c = 0; c < 16; c++) {
                secondWorldMap.put(new Position(r,c), game.getTileAt(new Position(r,c)).getTypeString());
            }
        }
        assertThat(firstWorldMap.values(), is(not(secondWorldMap.values())));
    }

    @Test
    public void shouldGenerateTheWorldMapProperlyWhenUsingTheThirdPartyGenerator() {
        assertThat(game.getTileAt(new Position(0,0)),is(notNullValue()));
        assertThat(game.getTileAt(new Position(16,16)), is(nullValue()));
    }
}
