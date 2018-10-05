package hotciv.standard;

import hotciv.framework.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @ author Troels Gjørup
 * 05-10-2018
 */
public class TestThetaCiv {

    private GameImpl game;

    @Before // Before is run before every @Test
    public void setUp() {
        game = new GameImpl(new ThetaCivFactory());
        // adding bomb unit i can use in my test cases at 5,1 for red player
        game.addUnit(new Position(5,0), GameConstants.BOMB, game.getPlayer(GameConstants.RED));
        // adding mountain and ocean tiles near the bomb placement. Mountain 4,0 and 5,1 Ocean
        game.addTile(new Position(4,0),GameConstants.MOUNTAINS);
        game.addTile(new Position(5,1), GameConstants.OCEANS);
    }


    @Test
    public void shouldBeAbleToMoveBombOverMountains(){
        game.moveUnit(new Position(5,0), new Position(4,0));
        assertThat(game.getUnitAt(new Position(4,0)).getTypeString(), is(GameConstants.BOMB));
    }
    @Test
    public void shouldBeAbleToMoveOverOceans(){
        game.moveUnit(new Position(5,0), new Position(5,1));
        assertThat(game.getUnitAt(new Position(5,1)).getTypeString(), is(GameConstants.BOMB));
    }
    @Test
    public void shouldResetMoveCountForBombsProperly(){
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.BOMB);
        game.moveUnit(new Position(5,0), new Position(4,0));
        game.moveUnit(new Position(4,0), new Position(3,0));
        assertThat(game.getUnitAt(new Position(3,0)).getMoveCount(), is(0));
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION,GameConstants.BOMB);
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(3,0)).getMoveCount(), is(2));

    }
    @Test
    public void shouldPerformBombActionProperly() {
        assertThat(game.getUnitAt(new Position(5,0)).getTypeString(),is(GameConstants.BOMB));
        game.performUnitActionAt(new Position(5,0));
        assertThat(game.getUnitAt(new Position(5,0)),is(nullValue()));


    }
    @Test
    public void shouldProperlyImplementStatePattern(){
        game.addUnit(new Position(8,0), GameConstants.ARCHER, game.getPlayer(GameConstants.RED));
        assertThat(game.getUnitAt(new  Position(8,0)).getDefensiveStrength(),is(1));
        game.performUnitActionAt(new Position(8,0));
        assertThat(game.getUnitAt(new Position(8,0)).getDefensiveStrength(), is(2));
        assertThat(game.getUnitAt(new Position(8,0)).getIsActionUsed(), is(true));
    }
    @Test
    public void bombShouldDestroyAllUnitsAroundItAndItself(){
        //adding units around the bomb
        game.addUnit(new Position(4,0), GameConstants.ARCHER, game.getPlayer(GameConstants.RED));
        game.addUnit(new Position(4,1), GameConstants.ARCHER, game.getPlayer(GameConstants.RED));
        game.addUnit(new Position(5,1), GameConstants.ARCHER, game.getPlayer(GameConstants.RED));
        game.addUnit(new Position(6,1), GameConstants.ARCHER, game.getPlayer(GameConstants.RED));
        game.addUnit(new Position(6,0), GameConstants.ARCHER, game.getPlayer(GameConstants.RED));
        game.performUnitActionAt(new Position(5,0));
        for (Position p: Utility.get8neighborhoodOf(new Position(5,0))){
            assertThat(game.getUnitAt(p), is(nullValue()));
        }
        assertThat(game.getUnitAt(new Position(5,0)), is(nullValue()));
        assertThat(game.getUnitAt(new Position(6,0)), is(nullValue()));
    }
    @Test
    public void bombShouldDestroyAdjacentCitiesIfPopulationIs1(){
        // Adding cities to adjacent tiles using iterator
        for (Position p: Utility.get8neighborhoodOf(new Position(5,0))){
          game.addCity(p, game.getPlayer(GameConstants.RED));
          assertThat(game.getCityAt(p), is(notNullValue()));
          assertThat(game.getCityAt(p).getSize(), is(1));
        }
        game.performUnitActionAt(new Position(5,0));
        for (Position p: Utility.get8neighborhoodOf(new Position(5,0))){
            assertThat(game.getCityAt(p), is(nullValue()));
        }

    }
    @Test
    public void bombShouldDecrementPopulationAndDestroyCityIfBothAreApplicable(){
        game.addCity(new Position(6,0), game.getPlayer(GameConstants.RED));
        game.addCity(new Position(4,0), game.getPlayer(GameConstants.RED));
        game.getCityAt(new Position(4,0)).setPopulation(2);
        game.performUnitActionAt(new Position(5,0));
        assertThat(game.getCityAt(new Position(6,0)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,0)).getSize(), is(1));

    }
}
