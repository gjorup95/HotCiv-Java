package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

/**
 * Skeleton class for AlphaCiv test cases
 * <p>
 * Updated Oct 2015 for using Hamcrest matchers
 * <p>
 * This source code is from the book
 * "Flexible, Reliable Software:
 * Using Patterns and Agile Development"
 * published 2010 by CRC Press.
 * Author:
 * Henrik B Christensen
 * Department of Computer Science
 * Aarhus University
 * <p>
 * Please visit http://www.baerbak.com/ for further information.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class TestAlphaCiv {

    private Game game;
    private CityImpl redCity;
    private GameConstants gameConstants;


    /**
     * Fixture for alphaciv testing.
     */

    @Before // Before is run before every @Test
    public void setUp() {
        game = new GameImpl(new WinningConditionAlphaCiv(game));
        redCity = new CityImpl(Player.RED);
    }

    /**
     * ======= ALPHA CIV ================================================================================ ///////////
     */

    @Test
    public void shouldBeRedAsStartingPlayer() {
        assertThat(game, is(notNullValue()));
        assertThat(game.getPlayerInTurn(), is(Player.RED));
    }

    @Test
    public void shouldBeBlueAfterEndTurn() {
        game.endOfTurn();
        assertThat(game, is(notNullValue()));
        assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    }

    @Test
    public void ageShouldStartAt4000YearsBeforeChrist() {
        assertEquals(-4000, game.getAge());
    }

    @Test
    public void ageShouldIncrementWith100AtEndOfTurn() {
        game.endOfTurn();
        assertEquals(-3900, game.getAge());
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertEquals(-3400, game.getAge());
    }

    @Test
    public void shouldHaveCityAt11() {
        assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION), notNullValue());
    }

    @Test
    public void shouldHaveRedCityAt11() {
        assertThat(game.getCityAt(new Position(1, 1)), is(notNullValue()));
        assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getOwner(), is(Player.RED));
    }

    @Test
    public void thereShouldNotBeACityOn01() {
        assertThat(game.getCityAt(new Position(0, 1)), is(nullValue()));
    }

    @Test
    public void thereShouldBeACityOn41() {
        assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION), is(notNullValue()));
    }

    @Test
    public void thereShouldBeABlueCityOn41() {
        assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION), is(notNullValue()));
        assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION).getOwner(), is(Player.BLUE));
    }

    @Test
    public void citiesShouldHaveZeroProductionInitiallyRedCity() {
        assertEquals(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), (0));
    }

    @Test
    public void shouldHave6ProductionAtEndTurnRedCity() {
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(0));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), is(6));
    }

    @Test
    public void shouldHave2ProductionAfterTwoEndTurnRedCity() {
        assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), is(0));
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), is(2));
    }

    @Test
    public void shouldHave6ProductionAtEndTurnBlueCity() {
        assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION).getTreasury(), is(0));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION).getTreasury(), is(6));
    }

    @Test
    public void shouldHave4ProductionAfter4EndTurnsBlue() {
        assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION).getTreasury(), is(0));
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION).getTreasury(), is(4));
    }

    @Test
    public void citiesShouldHave1Population() {
        assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getSize(), is(1));
    }

    @Test
    public void thereShouldBeAnOceanAt1_0() {
        assertThat(game.getTileAt(gameConstants.OCEAN_POSITION), is(notNullValue()));
        assertThat(game.getTileAt(gameConstants.OCEAN_POSITION).getTypeString(), is(gameConstants.OCEANS));
    }

    @Test
    public void thereShouldBeHillsAt0_1() {
        assertThat(game.getTileAt(gameConstants.HILLS_POSITION), is(notNullValue()));
        assertThat(game.getTileAt(gameConstants.HILLS_POSITION).getTypeString(), is(gameConstants.HILLS));
    }

    @Test
    public void thereShouldBeMountainsAt2_2() {
        assertThat(game.getTileAt(gameConstants.MOUNTAINS_POSITION), is(notNullValue()));
        assertThat(game.getTileAt(gameConstants.MOUNTAINS_POSITION).getTypeString(), is(gameConstants.MOUNTAINS));
    }

    @Test
    public void thereShouldBePlainsAt1_1() {
        assertThat(game.getTileAt(new Position(1, 1)).getTypeString(), is(gameConstants.PLAINS));
    }

    @Test
    public void thereShouldAlsoBePlainsAt4_2() {
        assertThat(game.getTileAt(new Position(4, 2)).getTypeString(), is(gameConstants.PLAINS));
    }

    @Test
    public void thereShouldLastlyBePlainsAt15_15() {
        assertThat(game.getTileAt(new Position(15, 15)).getTypeString(), is(gameConstants.PLAINS));
    }

    @Test
    public void thereShouldBeAUnitPlacedOn2_0() {
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED), is(notNullValue()));
    }

    @Test
    public void thereShouldBeAnArcherOn2_0() {
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED).getTypeString(), is(gameConstants.ARCHER));
    }

    @Test
    public void thereShouldBeALegionAt3_2() {
        assertThat(game.getUnitAt(gameConstants.LEGION_POSITION_BLUE), is(notNullValue()));
        assertThat(game.getUnitAt(gameConstants.LEGION_POSITION_BLUE).getTypeString(), is(gameConstants.LEGION));
    }

    @Test
    public void thereShouldBeASettlerAt4_3() {
        assertThat(game.getUnitAt(gameConstants.SETTLER_POSITION_RED), is(notNullValue()));
        assertThat(game.getUnitAt(gameConstants.SETTLER_POSITION_RED).getTypeString(), is(gameConstants.SETTLER));
    }

    @Test
    public void redShouldOwnAnArcherAt2_0() {
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED).getOwner(), is(notNullValue()));
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED).getOwner(), is(Player.RED));
    }

    @Test
    public void blueShouldOwnALegion3_2() {
        assertThat(game.getUnitAt(gameConstants.LEGION_POSITION_BLUE).getOwner(), is(notNullValue()));
        assertThat(game.getUnitAt(gameConstants.LEGION_POSITION_BLUE).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldBeRedPlayerWinningIn3000BC() {
        assertThat(game.getAge(), is(-4000));
        // for loop that decrements the worldAge by 500 years by calling endOfTurn() five times
        for (int i = 0; i < 5; i++) {
            game.endOfTurn();
        }
        assertThat(game.getWinner(), is(not(Player.RED)));
        // for loop that decrements the worldAge by 500 years by calling endOfTurn() five times
        for (int i = 0; i < 5; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-3000));
        assertThat(game.getWinner(), is(Player.RED));
    }

    // Testing for unit at (2,0) and moving to (2,1)
    @Test
    public void unitsShouldBeAbleToMove() {
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED), is(notNullValue()));
        game.moveUnit(gameConstants.ARCHER_POSITION_RED, new Position(2, 1));
        assertThat(game.getUnitAt(new Position(2, 1)), is(notNullValue()));
    }

    @Test
    public void unitsShouldBeAbleToMoveFrom2_0TO3_0() {
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED), is(notNullValue()));
        game.moveUnit(gameConstants.ARCHER_POSITION_RED, new Position(3, 0));
        assertThat(game.getUnitAt(new Position(3, 0)), is(notNullValue()));
    }

    @Test
    public void unitsShouldNotExistInSamePositionAfterMoveUnit() {
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED), is(notNullValue()));
        game.moveUnit(gameConstants.ARCHER_POSITION_RED, new Position(3, 0));
        assertThat(game.getUnitAt(new Position(2, 0)), is(nullValue()));
    }

    @Test
    public void unitsShouldNotBeAbleToMoveOverMountains() {
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED), is(notNullValue()));
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        game.moveUnit(gameConstants.ARCHER_POSITION_RED, new Position(2, 2));
        // there should be a mountain at (2,2)
        assertThat(game.getUnitAt(new Position(2, 2)), is(nullValue()));
        assertThat(game.getUnitAt(new Position(2, 0)), is(game.getUnitAt(gameConstants.ARCHER_POSITION_RED)));
    }

    @Test
    public void shouldBeAbleToMoveLegionUnits() {
        game.endOfTurn();
        assertThat(game.getUnitAt(gameConstants.LEGION_POSITION_BLUE), is(notNullValue()));
        game.moveUnit(gameConstants.LEGION_POSITION_BLUE, new Position(3, 3));
        assertThat(game.getUnitAt(new Position(3, 3)), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(3, 3)).getTypeString(), is(gameConstants.LEGION));

    }

    @Test
    public void redShouldNotBeAbleToMoveBluesUnits() {
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        assertThat(game.getUnitAt(gameConstants.LEGION_POSITION_BLUE), is(notNullValue()));
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).getOwner(), is(Player.BLUE));
        game.moveUnit(gameConstants.LEGION_POSITION_BLUE, new Position(2, 1));
        assertThat(game.getUnitAt(new Position(2, 1)), is(nullValue()));
    }

    @Test
    public void shouldProduceOneArcherUnitAfterEndOfRoundForPlayerRedAt1_1() {
        assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), is(0));
        assertThat(game.getUnitAt(gameConstants.RED_CITY_POSITION), is(nullValue()));
        game.changeProductionInCityAt(gameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), is(2));
        assertThat(game.getUnitAt(gameConstants.RED_CITY_POSITION).getTypeString(), is(gameConstants.ARCHER));
        assertThat(game.getUnitAt(gameConstants.RED_CITY_POSITION).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldProduceOneArcherUnitAfterTwoEndOfRoundForPlayerBlueAt4_1() {
        assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION).getTreasury(), is(0));
        assertThat(game.getUnitAt(gameConstants.BLUE_CITY_POSITION), is(nullValue()));
        game.changeProductionInCityAt(gameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION).getTreasury(), is(2));
        assertThat(game.getUnitAt(gameConstants.BLUE_CITY_POSITION).getTypeString(), is(gameConstants.ARCHER));
        assertThat(game.getUnitAt(gameConstants.BLUE_CITY_POSITION).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldProduceOneLegionUnitAfterTwoEndOfRoundForPlayerRed() {
        assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), is(0));
        assertThat(game.getUnitAt(gameConstants.RED_CITY_POSITION), is(nullValue()));
        game.changeProductionInCityAt(gameConstants.RED_CITY_POSITION, GameConstants.LEGION);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), is(2));
        assertThat(game.getUnitAt(gameConstants.RED_CITY_POSITION).getTypeString(), is(gameConstants.LEGION));
        assertThat(game.getUnitAt(gameConstants.RED_CITY_POSITION).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldUpdateTreasuryAfterUnitProduction() {
        assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), is(0));
        game.changeProductionInCityAt(gameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), is(6));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(gameConstants.RED_CITY_POSITION), is(notNullValue()));
        assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), is(2));
    }

    @Test
    public void shouldHaveRedUnitsDestroyingBlueUnitsWhenAttacking() {
        // checking both units exists
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED), is(notNullValue()));
        assertThat(game.getUnitAt(gameConstants.LEGION_POSITION_BLUE), is(notNullValue()));
        // attacking the blue legion unit with the red archer unit
        game.moveUnit(gameConstants.ARCHER_POSITION_RED, gameConstants.LEGION_POSITION_BLUE);
        // checking that the red unit moved from its original position
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED), is(nullValue()));
        assertThat(game.getUnitAt(gameConstants.LEGION_POSITION_BLUE).getTypeString(), is(gameConstants.ARCHER));
        assertThat(game.getUnitAt(gameConstants.LEGION_POSITION_BLUE).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldHaveBlueUnitsDestroyingRedUnitsWhenAttacking() {
        game.endOfTurn();
        // attacking the blue legion unit with the red archer unit
        game.moveUnit(gameConstants.LEGION_POSITION_BLUE, gameConstants.ARCHER_POSITION_RED);
        // checking that the red unit moved from its original position
        assertThat(game.getUnitAt(gameConstants.LEGION_POSITION_BLUE), is(nullValue()));
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED).getTypeString(), is(gameConstants.LEGION));
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED).getOwner(), is(Player.BLUE));
    }

    @Test
    public void checkForEligibleSpawnPositionsAtRedCityWithRelationToUnits() {
        assertThat(game.getUnitAt(GameConstants.RED_CITY_POSITION), is(nullValue()));
        game.changeProductionInCityAt(gameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(gameConstants.RED_CITY_POSITION).getTypeString(), is(GameConstants.ARCHER));
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(0, 1)), is(notNullValue()));
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(0, 2)), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(0, 1)), is(notNullValue()));
    }

    @Test
    public void redShouldAlwaysProduceUnitOnCityTileIfPossible() {
        assertThat(game.getUnitAt(GameConstants.RED_CITY_POSITION), is(nullValue()));
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(gameConstants.RED_CITY_POSITION), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(0, 1)), is(nullValue()));
    }

    @Test
    public void blueShouldAlwaysProduceUnitOnCityTileIfPossible() {
        assertThat(game.getUnitAt(GameConstants.BLUE_CITY_POSITION), is(nullValue()));
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(gameConstants.BLUE_CITY_POSITION), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(3, 1)), is(nullValue()));
    }

    @Test
    public void blueShouldAlwaysProduceUnitNorthOfCityTileIfItIsOccupiedByAUnit() {
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(3, 1)), notNullValue());
    }

    @Test
    public void redShouldNotBeAbleToSpawnUnitsOnMountains() {
        assertThat(game.getUnitAt(gameConstants.RED_CITY_POSITION), is(nullValue()));
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(2));
        assertThat(game.getUnitAt(gameConstants.RED_CITY_POSITION), notNullValue());
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(4));
        assertThat(game.getUnitAt(new Position(0, 1)), notNullValue());
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(6));
        assertThat(game.getUnitAt(new Position(0, 2)), notNullValue());
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), is(8));
        assertThat(game.getUnitAt(new Position(1, 2)), notNullValue());
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getTileAt(new Position(2, 2)).getTypeString(), is(gameConstants.MOUNTAINS));
        assertThat(game.getUnitAt(new Position(2, 2)), is(nullValue()));
        assertThat(game.getUnitAt(new Position(2, 1)), is(notNullValue()));
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(0));
    }

    @Test
    public void unitsShouldNotBeAbleToMoveOnOceanTiles() {
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED), is(notNullValue()));
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, GameConstants.OCEAN_POSITION);
        assertThat(game.getUnitAt(GameConstants.OCEAN_POSITION), is(nullValue()));
    }

    @Test
    public void citiesShouldOnlyProduceUnitsIfItHasEnoughTreasury() {
        assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), is(0));
        game.changeProductionInCityAt(gameConstants.RED_CITY_POSITION, gameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(6));
        assertThat(game.getUnitAt(gameConstants.RED_CITY_POSITION), is(nullValue()));
    }
    @Test
    public void unitsShouldHaveMoveCount() {
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED).getMoveCount(), is(not(0)));
        assertThat(game.getUnitAt(gameConstants.LEGION_POSITION_BLUE).getMoveCount(), is(not(0)));
    }

    @Test
    public void unitsShouldHaveTheirMovedCountReducedByOneWhenMovingOneTile() {
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED).getMoveCount(), is(1));
        game.moveUnit(gameConstants.ARCHER_POSITION_RED, new Position(1,2));
        assertThat(game.getUnitAt(new Position(1,2)).getMoveCount(), is(0));
    }

    @Test
    public void unitsShouldOnlyBeAbleToMoveTheirMoveCount() {
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED).getMoveCount(), is(1));
        game.moveUnit(gameConstants.ARCHER_POSITION_RED, new Position(1,2));
        assertThat(game.getUnitAt(new Position(1,2)).getMoveCount(), is(0));
        game.moveUnit(new Position(1,2), gameConstants.ARCHER_POSITION_RED);
        assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED),is(nullValue()));
    }

    @Test
    public void unitsShouldHaveTheirMoveCountResetAfterEndOfRound() {
        game.moveUnit(gameConstants.ARCHER_POSITION_RED, new Position(1,2));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(1,2)).getMoveCount(), is(1));

    }

    @Test
    public void shouldDefinetelyBeRemoved() {
        // Matching null and not null values
        // 'is' require an exact match
        String s = null;
        assertThat(s, is(nullValue()));
        s = "Ok";
        assertThat(s, is(notNullValue()));
        assertThat(s, is("Ok"));

        // If you only validate substrings, use containsString
        assertThat("This is a dummy test", containsString("dummy"));

        // Match contents of Lists
        List<String> l = new ArrayList<String>();
        l.add("Bimse");
        l.add("Bumse");
        // Note - ordering is ignored when matching using hasItems
        assertThat(l, hasItems(new String[]{"Bumse", "Bimse"}));

        // Matchers may be combined, like is-not
        assertThat(l.get(0), is(not("Bumse")));
    }
}
