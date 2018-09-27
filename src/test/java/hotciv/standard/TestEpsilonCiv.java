package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

public class TestEpsilonCiv {

        private GameImpl game;


        /**
         * Fixture for alphaciv testing.
         */

        @Before // Before is run before every @Test
        public void setUp() {
            game = new GameImpl(GameType.EPSILON);
        }

        @Test
        public void shouldBeAbleToIncrementAttackingBattleWon() {
            assertThat(game.getPlayerInTurn(),is(Player.RED));
            assertThat(game.getAttackingBattlesWon(), is(0));
            game.incrementAttackBattlesWon(1);
            assertThat(game.getAttackingBattlesWon(), is(1));
        }
    }



