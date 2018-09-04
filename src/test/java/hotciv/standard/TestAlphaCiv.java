package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

/** Skeleton class for AlphaCiv test cases

    Updated Oct 2015 for using Hamcrest matchers

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Department of Computer Science
     Aarhus University
   
   Please visit http://www.baerbak.com/ for further information.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
public class TestAlphaCiv {

  private Game game;
  private CityImpl redCity;
  private GameConstants gameConstants;

  /** Fixture for alphaciv testing. */

  @Before // Before is run before every @Test
  public void setUp() {
    game = new GameImpl();
    redCity = new CityImpl(Player.RED);
  }

  /**
   *  ======= ALPHA CIV ================================================================================ ///////////
   */

  @Test
  public void shouldBeRedAsStartingPlayer() {
    assertThat(game, is(notNullValue()));
    assertThat(game.getPlayerInTurn(), is(Player.RED));
  }

  @Test
  public void shouldBeBlueAfterEndTurn(){
    game.endOfTurn();
    assertThat(game, is(notNullValue()));
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
  }

  @Test
  public void ageShouldStartAt4000YearsBeforeChrist(){
    assertEquals( -4000, game.getAge());
  }

  @Test
  public void ageShouldIncrementWith100AtEndOfTurn(){
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
    assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION), notNullValue() );
  }

  @Test
  public void shouldHaveRedCityAt11(){
    assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION), is(notNullValue()));
    assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getOwner(), is(Player.RED)  );
  }

  @Test
  public void thereShouldNotBeACityOn01(){
    assertThat(game.getCityAt(new Position(0,1)), is(nullValue())  );
  }

  @Test
  public void thereShouldBeACityOn41(){
    assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION), is(notNullValue()));
  }

  @Test
  public void thereShouldBeABlueCityOn41(){
    assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION), is(notNullValue()));
    assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION).getOwner(), is(Player.BLUE));
  }

  @Test
  public void CitiesShouldHaveZeroProductionInitiallyRedCity(){
    assertEquals(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), (0));
  }

  @Test
  public void ShouldHave6ProductionAtEndTurnRedCity() {
    assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), is(0));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), is(6));
  }

  @Test
  public void ShouldHave12ProductionAfterTwoEndTurnRedCity(){
    assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), is(0));
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getTreasury(), is(12));
  }

  @Test
  public void ShouldHave6ProductionAtEndTurnBlueCity() {
    assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION).getTreasury(), is(0));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION).getTreasury(), is(6));
  }

  @Test
  public void ShouldHave12ProductionAfterTwoEndTurnsBlue(){
    assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION).getTreasury(), is(0));
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getCityAt(gameConstants.BLUE_CITY_POSITION).getTreasury(), is(12));
  }

  @Test
  public void citiesShouldHave1Population(){
    assertThat(game.getCityAt(gameConstants.RED_CITY_POSITION).getSize(), is(1));
  }

  @Test
  public void thereShouldBeAnOceanAt1_0(){
    assertThat(game.getTileAt(gameConstants.OCEAN_POSITION), is(notNullValue()));
    assertThat(game.getTileAt(gameConstants.OCEAN_POSITION).getTypeString(), is(gameConstants.OCEANS));
  }

  @Test
  public void thereShouldBeHillsAt0_1(){
    assertThat(game.getTileAt(gameConstants.HILLS_POSITION), is(notNullValue()));
    assertThat(game.getTileAt(gameConstants.HILLS_POSITION).getTypeString(),is(gameConstants.HILLS));
  }

  @Test
  public void thereShouldBeMountainsAt2_2(){
    assertThat(game.getTileAt(gameConstants.MOUNTAINS_POSITION), is(notNullValue()));
    assertThat(game.getTileAt(gameConstants.MOUNTAINS_POSITION).getTypeString(), is(GameConstants.MOUNTAINS));
  }

  @Test
  public void thereShouldBePlainsAt1_1(){
    assertThat(game.getTileAt(new Position(1,1)).getTypeString(), is(gameConstants.PLAINS));
  }

  @Test
  public void thereShouldAlsoBePlainsAt4_2(){
    assertThat(game.getTileAt(new Position(4,2)).getTypeString(), is(gameConstants.PLAINS));
  }

  @Test
  public void thereShouldLastlyBePlainsAt15_15(){
    assertThat(game.getTileAt(new Position(15,15)).getTypeString(), is(gameConstants.PLAINS));
  }

  @Test
  public void thereShouldBeAUnitPlacedOn2_0(){
    assertThat(game.getUnitAt(gameConstants.ARCHER_POSITION_RED), is(notNullValue()));
  }

  @Test
  public void thereShouldBeAnArcherOn2_0(){
    assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getTypeString(), is(gameConstants.ARCHER));
  }

  @Test
  public void thereShouldBeALegionAt3_2(){
    assertThat(game.getUnitAt(gameConstants.LEGION_POSITION_BLUE), is(notNullValue()));
    assertThat(game.getUnitAt(gameConstants.LEGION_POSITION_BLUE).getTypeString(), is(GameConstants.LEGION) );
  }

  @Test
  public void thereShouldBeASettlerAt4_3(){
    assertThat(game.getUnitAt(gameConstants.SETTLER_POSITION_RED), is(notNullValue()) );
    assertThat(game.getUnitAt(GameConstants.SETTLER_POSITION_RED).getTypeString(), is(GameConstants.SETTLER));
  }

  @Test
  public void redShouldOwnAnArcherAt2_0(){
    assertThat(game.getUnitAt(GameConstants.ARCHER_POSITION_RED).getOwner(), is(notNullValue()));
  }

  @Test
  public void blueShouldOwnALegion3_2(){
    assertThat(game.getUnitAt(GameConstants.LEGION_POSITION_BLUE).getOwner(), is(notNullValue()));
    assertThat(game.getUnitAt(gameConstants.LEGION_POSITION_BLUE).getOwner(), is(Player.BLUE));
  }

  @Test
  public void shouldBeRedPlayerWinningIn3000BC(){
    assertThat(game.getAge(), is(-4000));
    for (int i=0; i<5; i++){
      game.endOfTurn();
    }
    assertThat(game.getWinner(), is(not(Player.RED)));
    for (int i=0; i<5; i++){
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(-3000));
    assertThat(game.getWinner(), is(Player.RED));
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
    assertThat(l, hasItems(new String[] {"Bumse","Bimse"}));

    // Matchers may be combined, like is-not
    assertThat(l.get(0), is(not("Bumse")));
  }
}