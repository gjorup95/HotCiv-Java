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

    private GameImpl game;

    /**
     * Fixture for alphaciv testing.
     */

    @Before // Before is run before every @Test
    public void setUp() {
        game = new GameImpl(new AlphaCivFactory());
    }

    /**
     * ======= ALPHA CIV ================================================================================ ///////////
     */

    /**
     * UNITCOST ARE A FIXED AMOUNT
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
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        assertEquals(-3900, game.getAge());
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertEquals(-3600, game.getAge());
    }

    @Test
    public void shouldHaveCityAt11() {
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION), notNullValue());
    }

    @Test
    public void shouldHaveRedCityAt11() {
        assertThat(game.getCityAt(new Position(1, 1)), is(notNullValue()));
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getOwner(), is(Player.RED));
    }

    @Test
    public void thereShouldNotBeACityOn01() {
        assertThat(game.getCityAt(new Position(0, 1)), is(nullValue()));
    }

    @Test
    public void thereShouldBeACityOn41() {
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION), is(notNullValue()));
    }

    @Test
    public void thereShouldBeABlueCityOn41() {
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION), is(notNullValue()));
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION).getOwner(), is(Player.BLUE));
    }

    @Test
    public void citiesShouldHaveZeroProductionInitiallyRedCity() {
        assertEquals(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), (0));
    }

    @Test
    public void shouldHave6ProductionAtEndTurnRedCity() {
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(0));
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(6));
    }

    @Test
    public void shouldHave2ProductionAfterTwoEndTurnRedCity() {
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(0));
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(2));
    }

    @Test
    public void shouldHave6ProductionAtEndTurnBlueCity() {
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION).getTreasury(), is(0));
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION).getTreasury(), is(6));
    }

    @Test
    public void shouldHave4ProductionAfter4EndTurnsBlue() {
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION).getTreasury(), is(0));
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION).getTreasury(), is(4));
    }

    @Test
    public void citiesShouldHave1Population() {
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getSize(), is(1));
    }

    @Test
    public void thereShouldBeAnOceanAt1_0() {
        assertThat(game.getTileAt(GameConstants.OCEAN_POSITION), is(notNullValue()));
        assertThat(game.getTileAt(GameConstants.OCEAN_POSITION).getTypeString(), is(GameConstants.OCEANS));
    }

    @Test
    public void thereShouldBeHillsAt0_1() {
        assertThat(game.getTileAt(GameConstants.HILLS_POSITION), is(notNullValue()));
        assertThat(game.getTileAt(GameConstants.HILLS_POSITION).getTypeString(), is(GameConstants.HILLS));
    }

    @Test
    public void thereShouldBeMountainsAt2_2() {
        assertThat(game.getTileAt(GameConstants.MOUNTAINS_POSITION), is(notNullValue()));
        assertThat(game.getTileAt(GameConstants.MOUNTAINS_POSITION).getTypeString(), is(GameConstants.MOUNTAINS));
    }

    @Test
    public void thereShouldBePlainsAt1_1() {
        assertThat(game.getTileAt(new Position(1, 1)).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void thereShouldAlsoBePlainsAt4_2() {
        assertThat(game.getTileAt(new Position(4, 2)).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void thereShouldLastlyBePlainsAt15_15() {
        assertThat(game.getTileAt(new Position(15, 15)).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void thereShouldBeAUnitPlacedOn2_0() {
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED), is(notNullValue()));
    }

    @Test
    public void thereShouldBeAnArcherOn2_0() {
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void thereShouldBeALegionAt3_2() {
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE), is(notNullValue()));
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void thereShouldBeASettlerAt4_3() {
        assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED), is(notNullValue()));
        assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED).getTypeString(), is(GameConstants.SETTLER));
    }

    @Test
    public void redShouldOwnAnArcherAt2_0() {
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getOwner(), is(notNullValue()));
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getOwner(), is(Player.RED));
    }

    @Test
    public void blueShouldOwnALegion3_2() {
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).getOwner(), is(notNullValue()));
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldBeRedPlayerWinningIn3000BC() {
        assertThat(game.getAge(), is(-4000));
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.LEGION);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.LEGION);
        // for loop that decrements the worldAge by 500 years by calling endOfTurn() five times
        for (int i = 0; i < 9; i++) {
            game.endOfTurn();
        }
        assertThat(game.getWinner(), is(not(Player.RED)));
        // for loop that decrements the worldAge by 500 years by calling endOfTurn() five times
        for (int i = 0; i < 10; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-3000));
        assertThat(game.getWinner(), is(Player.RED));
    }

    // Testing for unit at (2,0) and moving to (2,1)
    @Test
    public void unitsShouldBeAbleToMove() {
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED), is(notNullValue()));
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(2, 1));
        assertThat(game.getUnitAt(new Position(2, 1)), is(notNullValue()));
    }

    @Test
    public void unitsShouldBeAbleToMoveFrom2_0TO3_0() {
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(3,0)), is(nullValue()));
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 0));
        assertThat(game.getUnitAt(new Position(3, 0)), is(notNullValue()));
    }

    @Test
    public void unitsShouldNotExistInSamePositionAfterMoveUnit() {
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED), is(notNullValue()));
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 0));
        assertThat(game.getUnitAt(new Position(2, 0)), is(nullValue()));
    }

    @Test
    public void unitsShouldNotBeAbleToMoveOverMountains() {
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED), is(notNullValue()));
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(2, 2));
        // there should be a mountain at (2,2)
        assertThat(game.getUnitAt(new Position(2, 2)), is(nullValue()));
        assertThat(game.getUnitAt(new Position(2, 0)), is(game.getUnitAt(GameConstants.ARCHER_POSITION_RED)));
    }

    @Test
    public void shouldBeAbleToMoveLegionUnits() {
        game.endOfTurn();
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE), is(notNullValue()));
        game.moveUnit(GameConstants.LEGION_POSITION_BLUE, new Position(3, 3));
        assertThat(game.getUnitAt(new Position(3, 3)), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(3, 3)).getTypeString(), is(GameConstants.LEGION));

    }

    @Test
    public void redShouldNotBeAbleToMoveBluesUnits() {
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE), is(notNullValue()));
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).getOwner(), is(Player.BLUE));
        game.moveUnit(GameConstants.LEGION_POSITION_BLUE, new Position(2, 1));
        assertThat(game.getUnitAt(new Position(2, 1)), is(nullValue()));
    }

    @Test
    public void shouldProduceOneArcherUnitAfterEndOfRoundForPlayerRedAt1_1() {
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(0));
        assertThat(game.getUnitAt(GameConstants.RED_CITY_POSITION), is(nullValue()));
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(2));
        assertThat(game.getUnitAt(GameConstants.RED_CITY_POSITION).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(GameConstants.RED_CITY_POSITION).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldProduceOneArcherUnitAfterTwoEndOfRoundForPlayerBlueAt4_1() {
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION).getTreasury(), is(0));
        assertThat(game.getUnitAt(GameConstants.BLUE_CITY_POSITION), is(nullValue()));
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION).getTreasury(), is(2));
        assertThat(game.getUnitAt(GameConstants.BLUE_CITY_POSITION).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(GameConstants.BLUE_CITY_POSITION).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldProduceOneLegionUnitAfterTwoEndOfRoundForPlayerRed() {
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(0));
        assertThat(game.getUnitAt(GameConstants.RED_CITY_POSITION), is(nullValue()));
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.LEGION);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.LEGION);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(2));
        assertThat(game.getUnitAt(GameConstants.RED_CITY_POSITION).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(GameConstants.RED_CITY_POSITION).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldUpdateTreasuryAfterUnitProduction() {
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(0));
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(6));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(GameConstants.RED_CITY_POSITION), is(notNullValue()));
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(2));
    }

    @Test
    public void shouldHaveRedUnitsDestroyingBlueUnitsWhenAttacking() {
        // checking both units exists
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED), is(notNullValue()));
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE), is(notNullValue()));
        // attacking the blue legion unit with the red archer unit
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 1));
        assertThat(game.getUnitAt(new Position(3, 1)).getTypeString(), is(GameConstants.ARCHER));
        // Needs endOfRound to reset Red Players moveCount
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.moveUnit(new Position(3, 1), GameConstants.LEGION_POSITION_BLUE);
        // checking that the red unit moved from its original position
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED), is(nullValue()));
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).getOwner(), is(Player.RED));
    }

    @Test
    public void shouldHaveBlueUnitsDestroyingRedUnitsWhenAttacking() {
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        // moving the blue legion unit closer to the red archer unit
        game.moveUnit(GameConstants.LEGION_POSITION_BLUE, new Position(3, 1));
        assertThat(game.getUnitAt(new Position(3, 1)).getTypeString(), is(GameConstants.LEGION));
        game.endOfTurn();
        game.endOfTurn();
        // checking that the blue unit moved from its original position
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE), is(nullValue()));
        game.moveUnit(new Position(3, 1), GameConstants.ARCHER_POSITION_RED);
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getOwner(), is(Player.BLUE));
    }

    @Test
    public void checkForEligibleSpawnPositionsAtRedCityWithRelationToUnits() {
        assertThat(game.getUnitAt(GameConstants.RED_CITY_POSITION), is(nullValue()));
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(GameConstants.RED_CITY_POSITION).getTypeString(), is(GameConstants.ARCHER));
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
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(GameConstants.RED_CITY_POSITION), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(0, 1)), is(nullValue()));
    }

    @Test
    public void blueShouldAlwaysProduceUnitOnCityTileIfPossible() {
        assertThat(game.getUnitAt(GameConstants.BLUE_CITY_POSITION), is(nullValue()));
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getUnitAt(GameConstants.BLUE_CITY_POSITION), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(3, 1)), is(nullValue()));
    }

    @Test
    public void blueShouldAlwaysProduceUnitNorthOfCityTileIfItIsOccupiedByAUnit() {
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
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
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        assertThat(game.getUnitAt(GameConstants.RED_CITY_POSITION), is(nullValue()));
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(2));
        assertThat(game.getUnitAt(GameConstants.RED_CITY_POSITION), notNullValue());
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
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(8));
        assertThat(game.getUnitAt(new Position(1, 2)), notNullValue());
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getTileAt(new Position(2, 2)).getTypeString(), is(GameConstants.MOUNTAINS));
        assertThat(game.getUnitAt(new Position(2, 2)), is(nullValue()));
        assertThat(game.getUnitAt(new Position(2, 1)), is(notNullValue()));
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(0));
    }

    @Test
    public void redShouldNotBeAbleToSpawnUnitsOnOceanTile() {
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        assertThat(game.getUnitAt(new Position(2, 1)), is(nullValue()));
        assertThat(game.getUnitAt(new Position(0, 0)), is(nullValue()));
        assertThat(game.getTileAt(new Position(1, 0)).getTypeString(), is(GameConstants.OCEANS));
        // After 10 endOfRounds the redCity should have accumulated enough production to produce 2 archerUnits
        for (int i = 0; i < 19; i++) {
            game.endOfTurn();
        }
        assertThat(game.getUnitAt(GameConstants.OCEAN_POSITION), is(nullValue()));
        // redCity should also produce units on the tiles not occupied by redArcherUnit(2,0) and the ocean tile (1,1)
        assertThat(game.getUnitAt(new Position(2, 1)), is(not(nullValue())));
        assertThat(game.getUnitAt(new Position(0, 0)), is(not(nullValue())));
    }

    @Test
    public void unitsShouldNotBeAbleToMoveOnOceanTiles() {
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED), is(notNullValue()));
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, GameConstants.OCEAN_POSITION);
        assertThat(game.getUnitAt(GameConstants.OCEAN_POSITION), is(nullValue()));
    }

    @Test
    public void citiesShouldOnlyProduceUnitsIfItHasEnoughTreasury() {
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(0));
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        assertThat(game.getCityAt(GameConstants.RED_CITY_POSITION).getTreasury(), is(6));
        assertThat(game.getUnitAt(GameConstants.RED_CITY_POSITION), is(nullValue()));
    }

    @Test
    public void unitsShouldHaveMoveCount() {
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getMoveCount(), is(not(0)));
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).getMoveCount(), is(not(0)));
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getMoveCount(), is((1)));
        assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).getMoveCount(), is((1)));
    }

    @Test
    public void unitsShouldHaveTheirMovedCountReducedByOneWhenMovingOneTile() {
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getMoveCount(), is(1));
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(2, 1));
        assertThat(game.getUnitAt(new Position(2, 1)).getMoveCount(), is(0));
    }

    @Test
    public void unitsShouldOnlyBeAbleToMoveTheirMoveCount() {
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getMoveCount(), is(1));
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(2, 1));
        assertThat(game.getUnitAt(new Position(2, 1)).getMoveCount(), is(0));
        game.moveUnit(new Position(2, 1), GameConstants.ARCHER_POSITION_RED);
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED), is(nullValue()));
    }

    @Test
    public void unitsShouldHaveTheirMoveCountResetAfterEndOfRound() {
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(2, 1));
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        assertThat(game.getUnitAt(new Position(2, 1)).getMoveCount(), is(1));

    }

    @Test
    public void shouldNotBePossibleToConquerCitiesInAlphaCiv() {
        game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3, 1));
        game.endOfTurn();
        game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.ARCHER);
        game.endOfTurn();
        game.moveUnit(new Position(3, 1), GameConstants.BLUE_CITY_POSITION);
        assertThat(game.getUnitAt(GameConstants.BLUE_CITY_POSITION), is(notNullValue()));
        assertThat(game.getCityAt(GameConstants.BLUE_CITY_POSITION).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldBeAbleToMoveSouthThenEastInSameRound(){
    game.getUnitAt(GameConstants.ARCHER_POSITION_RED).setMoveCount(2);
    assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getMoveCount(),is(2));
    game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3,0));
    assertThat(game.getUnitAt(new Position(3,0)).getMoveCount(), is(1));
    game.moveUnit(new Position(3,0), new Position(3,1));
    assertThat(game.getUnitAt(new Position(3,1)),is(notNullValue()));
    assertThat(game.getUnitAt(new Position(3,1)).getMoveCount(),is(0));
    }

    @Test
    public void canMoveTheNumberOfMoveCountsButOnly1TileAtATime(){
        game.getUnitAt(GameConstants.ARCHER_POSITION_RED).setMoveCount(2);
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3,0));
        game.moveUnit(new Position(3,0), new Position(4,0));
        assertThat(game.getUnitAt(new Position(4,0)), is(notNullValue()));
    }

    @Test
    public void has6MoveCountsAndCanUseThem(){
        game.getUnitAt(GameConstants.ARCHER_POSITION_RED).setMoveCount(6);
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3,0));
        game.moveUnit(new Position(3,0), new Position(4,0));
        game.moveUnit(new Position(4,0), new Position(5,0));
        game.moveUnit(new Position(5,0), new Position(6,0));
        game.moveUnit(new Position(6,0), new Position(7,0));
        game.moveUnit(new Position(7,0), new Position(8,0));
        assertThat(game.getUnitAt(new Position(8,0)), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(8,0)).getMoveCount(), is(0));
    }

    @Test
    public void canMove4TimesDiagonal(){
        game.getUnitAt(GameConstants.ARCHER_POSITION_RED).setMoveCount(6);
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3,1));
        game.moveUnit(new Position(3,1), new Position(4,2));
        game.moveUnit(new Position(4,2), new Position(5,3));
        game.moveUnit(new Position(5,3), new Position(6,4));
        game.moveUnit(new Position(6,4), new Position(7,5));
        game.moveUnit(new Position(7,5), new Position(8,6));
        assertThat(game.getUnitAt(new Position(8,6)), is(notNullValue()));
        assertThat(game.getUnitAt(new Position(8,6)).getMoveCount(), is(0));

    }

    @Test
    public void hasMoveCount2ButShouldOnlyBeAbleToMove1TileAtATime(){
        game.getUnitAt(GameConstants.ARCHER_POSITION_RED).setMoveCount(2);
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(4,2));
        assertThat(game.getUnitAt(new Position(4,2)), is(nullValue()));
        assertThat(game.getUnitAt(new Position(2,0)).getMoveCount(), is(2));

    }

    @Test
    public void cannotMoveInTheSameSpot(){
        game.getUnitAt(GameConstants.ARCHER_POSITION_RED).setMoveCount(6);
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(2,0));
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getMoveCount(), is(6));
    }

    @Test
    public void settlerUnitShouldPerformUnitActionButNothingShouldHappen() {
        game.performUnitActionAt(GameConstants.SETTLER_POSITION_RED);
        assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getCityAt(GameConstants.SETTLER_POSITION_RED), is(nullValue()));
    }

    @Test
    public void archerUnitShouldPerformUnitActionButNothingShouldHappen() {
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getDefensiveStrength(), is(1));
        game.performUnitActionAt(GameConstants.ARCHER_POSITION_RED);
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getDefensiveStrength(), is(1));
    }
    @Test
    public void notAbleToMoveOnToTilesWhereOwnUnitsAre(){
        game.addUnit(new Position(3,0),GameConstants.LEGION, Player.RED);
        assertThat(game.getUnitAt(new Position(3,0)).getTypeString(),is(GameConstants.LEGION));
        assertThat(game.getUnitAt(new Position(3,0)).getOwner(), is(Player.RED));
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3,0));
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(3,0)).getTypeString(), is(GameConstants.LEGION));
    }


    @Test
    public void shouldReturnFalseWhenCallingPerformSettlerActionAlphaCiv() {
       UnitActionsAlphaCiv unitActions = new UnitActionsAlphaCiv();
       assertThat(unitActions.performSettlerActionAt(new Position(1,1)), is(false));
    }

    @Test
    public void shouldReturnFalseWhenCallingPerformArcherActionAlphaCiv() {
        UnitActionsAlphaCiv unitActions = new UnitActionsAlphaCiv();
        assertThat(unitActions.performArcherFortifyActionAt(new Position(1,1)), is(false));
    }
   @Test
   public void constructorForUnitsShouldBeAbleToCreateAUniqueBomb(){
        game.addUnit(new Position(1,1),GameConstants.BOMB, Player.RED);
        assertThat(game.getUnitAt(new Position(1,1)).getTypeString(), is(GameConstants.BOMB));
        assertThat(game.getUnitAt(new Position(1,1)).getMoveCount(), is(2));
   }
   @Test
   public void shouldBeAbleToProduceBombs(){
       game.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.BOMB);
       game.endOfTurn();
       game.changeProductionInCityAt(GameConstants.BLUE_CITY_POSITION, GameConstants.BOMB);
       for (int i=0; i<20; i++){
           game.endOfTurn();
       }

       assertThat(game.getUnitAt(GameConstants.RED_CITY_POSITION).getTypeString(), is(GameConstants.BOMB));
   }

   @Test
   public void test(){
        assertThat(game.calculateLegalMove(new Position(-1,5), new Position(0,5)), is(true));
   }

   @Test
   public void shouldBeAbleToMoveOnHills(){
        game.addUnit(new Position(0,0),GameConstants.ARCHER, Player.RED);
        assertThat(game.getUnitAt(new Position(0,0)).getTypeString(), is(GameConstants.ARCHER));
        game.moveUnit(new Position(0,0), GameConstants.HILLS_POSITION);
        assertThat(game.getUnitAt(GameConstants.HILLS_POSITION).getTypeString(),is(GameConstants.ARCHER));
   }
   @Test
   public void shouldNotBeAbleToMoveWhenMovecountIs0(){
        game.getUnitAt(GameConstants.ARCHER_POSITION_RED).setMoveCount(0);
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getMoveCount(), is(0));
        game.moveUnit(GameConstants.ARCHER_POSITION_RED,new Position(3,0));
        assertThat(game.getUnitAt(new Position(3,0)), is(nullValue()));
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getTypeString(),is(GameConstants.ARCHER));
   }
   @Test
   public void shouldNotBeAbleToMove2TilesAtOnce(){
        game.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(4,0));
        assertThat(game.getUnitAt(new Position(4,0)), is(nullValue()));
        assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getTypeString(), is(GameConstants.ARCHER));
   }

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
