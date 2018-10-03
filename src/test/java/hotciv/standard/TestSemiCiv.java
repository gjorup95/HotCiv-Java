package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestSemiCiv {

    private GameImpl game;

    @Before
    public void setUp() {
        this.game = new GameImpl(new SemiCivFactory());
    }

    @Test
    public void shouldIntegrateBetaCivWorldAgeingCorrectly() {
        assertThat(game.getAge(), is(GameConstants.STARTING_AGE));
        for (int i = 0; i < 78; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-100));
    }

    @Test
    public void


}
