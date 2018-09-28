package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.GameType;
import org.junit.Before;

public class TestZetaCiv {
    private GameImpl game;
    private GameConstants gameConstants;

    @Before // Before is run before every @Test
    public void setUp() {
        game = new GameImpl(GameType.ZETA);
    }


}
