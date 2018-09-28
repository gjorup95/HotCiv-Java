package hotciv.standard;

import hotciv.framework.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * @ author Troels Gjørup
 * 14-09-2018
 */
public class TestGammaCiv {
    private GameImpl game;
    private GameConstants gameConstants;


    @Before // Before is run before every @Test
    public void setUp() {
        game = new GameImpl(GameType.GAMMA);

    }

    @Test
    public void onlySettlersShouldBeAbleToSpawnCities() {
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getTypeString(), is(GameConstants.ARCHER));
        game.performUnitActionAt(GameConstants.ARCHER_POSITION_RED);
        assertThat(game.getCityAt(GameConstants.ARCHER_POSITION_RED), is(nullValue()));
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED), is(notNullValue()));
    }

    @Test
    public void redShouldBeAbleToPerformSettlerActionAt() {
        assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED).getOwner(), is(game.getPlayer(GameConstants.RED)));
        game.performUnitActionAt(GameConstants.SETTLER_POSITION_RED);
        assertThat(game.getCityAt(GameConstants.SETTLER_POSITION_RED).getOwner(), is(game.getPlayer(GameConstants.RED)));
        assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED), is(nullValue()));
    }

    @Test
    public void blueShouldNotBeAbleToPerformUnitActionsOnRedUnits() {
        assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED).getOwner(), is(game.getPlayer(GameConstants.RED)));
        game.endOfTurn();
        game.performUnitActionAt(GameConstants.SETTLER_POSITION_RED);
        assertThat(game.getCityAt(GameConstants.SETTLER_POSITION_RED), is(nullValue()));
        assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED).getTypeString(), is(GameConstants.SETTLER));

    }

    @Test
    public void shouldNotBeAbleToSpawnCitiesOnCities() {
        assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED).getOwner(), is(game.getPlayer(GameConstants.RED)));
        game.moveUnit(GameConstants.SETTLER_POSITION_RED, new Position(4, 2));
        assertThat(game.getUnitAt(new Position(4, 2)).getTypeString(), is(gameConstants.SETTLER));
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(new Position(4, 2), gameConstants.BLUE_CITY_POSITION);
        assertThat(game.getUnitAt(new Position(4, 1)).getTypeString(), is(gameConstants.SETTLER));
        assertThat(game.getUnitAt(GameConstants.BLUE_CITY_POSITION), is(notNullValue()));
        game.performUnitActionAt(GameConstants.BLUE_CITY_POSITION);
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION).getOwner(), is(game.getPlayer(GameConstants.BLUE)));
        assertThat(game.getUnitAt(GameConstants.BLUE_CITY_POSITION).getTypeString(), is(GameConstants.SETTLER));
    }

    @Test
    public void shouldRemoveSettlerProperlyAfterSpawnedCity() {
        assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED).getOwner(), is(game.getPlayer(GameConstants.RED)));
        game.performUnitActionAt(GameConstants.SETTLER_POSITION_RED);
        assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED), is(nullValue()));
    }

    @Test
    public void shouldSpawnCityWithProperAttributesAndOwner() {
        assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED).getOwner(), is(game.getPlayer(GameConstants.RED)));
        game.performUnitActionAt(GameConstants.SETTLER_POSITION_RED);
        assertThat(game.getCityAt(GameConstants.SETTLER_POSITION_RED).getSize(), is(1));
        assertThat(game.getCityAt(GameConstants.SETTLER_POSITION_RED).getOwner(), is(game.getPlayer(GameConstants.RED)));
    }

    @Test
    public void shouldToggleArcherFortifyProperly() {
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getDefensiveStrength(), is(1));
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getMoveCount(), is(1));
        game.performUnitActionAt(GameConstants.ARCHER_POSITION_RED);
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getDefensiveStrength(), is(2));
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getMoveCount(), is(0));
    }

    @Test
    public void shouldDisableArcherFortifyProperly() {
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getDefensiveStrength(), is(1));
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getMoveCount(), is(1));
        game.performUnitActionAt(GameConstants.ARCHER_POSITION_RED);
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getDefensiveStrength(), is(2));
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getMoveCount(), is(0));
        game.performUnitActionAt(GameConstants.ARCHER_POSITION_RED);
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getDefensiveStrength(), is(1));
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getMoveCount(), is(1));

    }

    @Test
    public void blueShouldNotBeAbleToApplyFortificationOnRedArcher() {
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(game.getPlayer(GameConstants.BLUE)));
        game.performUnitActionAt(GameConstants.ARCHER_POSITION_RED);
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getDefensiveStrength(), is(1));
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getMoveCount(), is(1));
    }
}