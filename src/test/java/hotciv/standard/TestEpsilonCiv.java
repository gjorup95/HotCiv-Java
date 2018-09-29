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
            game = new GameImpl(new EpsilonCivFactory());
        }

        @After
        public void CleanUp(){
        }
        @Test
        public void shouldBeAbleToIncrementAttackingBattleWon() {
            assertThat(game.getPlayerInTurn(),is(game.getPlayer(GameConstants.RED)));
            assertThat(game.getCurrentPlayersAttackingBattlesWon(), is(0));
            game.incrementCurrentPlayersAttackBattlesWon(1);
            assertThat(game.getCurrentPlayersAttackingBattlesWon(), is(1));
            game.incrementCurrentPlayersAttackBattlesWon(-1);
            assertThat(game.getCurrentPlayersAttackingBattlesWon(), is(0));
        }
        @Test
        public void shouldIterateEnumClassProperlyAndReturnRedWinner(){
            game.incrementCurrentPlayersAttackBattlesWon(3);
            assertThat(game.getWinner(), is(game.getPlayer(GameConstants.RED)));
        }
        @Test
        public void shouldNotReturnAWinnerIfThereIsNone(){
            game.incrementCurrentPlayersAttackBattlesWon(2);
            assertThat(game.getPlayer(GameConstants.RED).getAttackingBattlesWon(), is(2));
            game.endOfTurn();
            game.incrementCurrentPlayersAttackBattlesWon(2);
            assertThat(game.getPlayer(GameConstants.BLUE).getAttackingBattlesWon(), is(2));
            assertThat(game.getWinner(), is(nullValue()));
        }
    }



