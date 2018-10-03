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
    public void shouldUpdateTheUnitMapWithWorldCreatorDeltaCivUnitConfiguration() {
        assertThat(game.getUnitAt(new Position(2,0)), is(nullValue()));
        assertThat(game.getUnitAt(new Position(4,8)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(4,8)).getOwner(), is(game.getPlayer(GameConstants.RED)));
    }

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

    @Test
    public void shouldIntegrateTheEpsilonCivWinningConditionProperlyAndNotAlphaCiv() {
        assertThat(game.getAge(), is(GameConstants.STARTING_AGE));
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


}
