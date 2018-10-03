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
        game = new GameImpl(new EtaCivFactory());
    }

    @Test
    public void isBefore20RoundsShouldReturnTrueWhenTheGameStarts() {
        assertThat(game.isBefore20Rounds(), is(true));
    }
}
