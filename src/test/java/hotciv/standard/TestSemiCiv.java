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

    /** ========== BETACIV INTEGRATION ================================ */

    @Test
    public void shouldIntegrateBetaCivWorldAgeingCorrectly() {
        assertThat(game.getAge(), is(GameConstants.STARTING_AGE));
        game.changeProductionInCityAt(new Position(8,12), GameConstants.ARCHER);
        game.changeProductionInCityAt(new Position(4, 5), GameConstants.ARCHER);
        for (int i = 0; i < 78; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-100));
    }

    /** ========== DELTACIV INTEGRATION ================================ */

    @Test
    public void shouldUpdateTheUnitMapWithWorldCreatorDeltaCivUnitConfiguration() {
        assertThat(game.getUnitAt(new Position(2,0)), is(nullValue()));
        assertThat(game.getUnitAt(new Position(4,8)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(4,8)).getOwner(), is(game.getPlayer(GameConstants.RED)));
    }

    /** ========== GAMMACIV INTEGRATION ================================ */

    @Test
    public void shouldIntegrateTheArchersFromWorldCreatorDeltaCivWithGammaCivUnitActions() {
        assertThat(game.getUnitAt(new Position(4,8)).getDefensiveStrength(), is(1));
        game.performUnitActionAt(new Position(4,8));
        assertThat(game.getUnitAt(new Position(4,8)).getDefensiveStrength(), is(2));
    }

    @Test
    public void shouldIntegrateTheSettlersFromWorldCreatorDeltaCivWithGammaCivUnitActions() {
        assertThat(game.getUnitAt(new Position(5,5)).getTypeString(), is(GameConstants.SETTLER));
        game.performUnitActionAt(new Position(5,5));
        assertThat(game.getUnitAt(new Position(5,5)), is(nullValue()));
        assertThat(game.getCityAt(new Position(5,5)).getOwner(), is(game.getPlayer(GameConstants.RED)));
    }

    @Test
    public void shouldIntegrateTheArchersFromWorldCreatorDeltaCivWithGammaCivUnitActionsButBlueShouldntBeAbleToUseRedsArchers() {
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(4,8)).getDefensiveStrength(), is(1));
        game.performUnitActionAt(new Position(4,8));
        assertThat(game.getUnitAt(new Position(4,8)).getDefensiveStrength(), is(1));
    }

    /** ========== EPSILONCIV WINNING CONDITION INTEGRATION ================================ */

    @Test
    public void shouldIntegrateTheEpsilonCivWinningConditionProperlyAndNotAlphaCiv() {
        assertThat(game.getAge(), is(GameConstants.STARTING_AGE));
        game.changeProductionInCityAt(new Position(8,12), GameConstants.ARCHER);
        game.changeProductionInCityAt(new Position(4, 5), GameConstants.ARCHER);
        for(int i = 0; i<20; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-3000));
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldIntegrateTheEpsilonCivWinningConditionProperlyAndNotBetaCiv() {
        assertThat(game.getCityAt(new Position(4,5)).getOwner(), is(game.getPlayer(GameConstants.BLUE)));
        // removes the blueArcher unit so it is easier to attack the city.
        game.removeUnit(new Position(4,4));
        game.addUnit(new Position(4,4), GameConstants.ARCHER, game.getPlayer(GameConstants.RED));
    }

    @Test
    public void shouldIntegrateTheEpsilonCivWinningConditionAndWorkWhenConditionsAreMet() {
        assertThat(game.getPlayer(GameConstants.RED).getAttackingBattlesWon(), is(0));
        assertThat(game.getWinner(), is(nullValue()));
        game.getPlayer(GameConstants.RED).setAttackingBattlesWon(3);
        assertThat(game.getWinner(), is(game.getPlayer(GameConstants.RED)));
    }

    /** ========== EPSILONCIV ATTACKSTRAT INTEGRATION ================================ */

    @Test
    public void blueLegionUnitShouldDestroyRedSettlerUnitWithWorldCreatorDeltaCivCoordinatesWithAssistance() {
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(5,5)).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(new Position(4,4)).getTypeString(), is(GameConstants.LEGION));
        game.addUnit(new Position(5,6), GameConstants.ARCHER, game.getPlayer(GameConstants.BLUE));
        game.addUnit(new Position(5,4), GameConstants.ARCHER, game.getPlayer(GameConstants.BLUE));
        game.moveUnit(new Position(4,4), new Position(5,5));
        //TODO Test percentage of how many times Blue wins.
        int redWins = 0;
        int blueWins = 0;
        for (int i = 0; i < 10000; i++) {
            if (game.attackResult(new Position(4, 4), new Position(5, 5)) > 0) {
                redWins += 1;
            } else {
                blueWins += 1;
            }
        }
        assertThat(blueWins > redWins, is(true));
        assertThat(blueWins > 99, is(true));
    }


}
