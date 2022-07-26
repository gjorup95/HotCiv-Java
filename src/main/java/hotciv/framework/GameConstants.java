package hotciv.framework;

/**
 * Collection of constants used in HotCiv Game. Note that strings are
 * used instead of enumeration types to keep the set of valid
 * constants open to extensions by future HotCiv variants.  Enums can
 * only be changed by compile time modification.
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
public class GameConstants {
    // PLAYER COLORS
    public static final String RED = "RED";
    public static final String BLUE = "BLUE";
    public static final String YELLOW = "YELLOW";
    public static final String GREEN = "GREEN";
    // The size of the world is set permanently to a 16x16 grid
    public static final int WORLDSIZE = 16;
    // The age which the game begins
    public static final int STARTING_AGE = -4000;
    // Valid unit types
    public static final String ARCHER = "archer";
    public static final String LEGION = "legion";
    public static final String SETTLER = "settler";
    public static final String BOMB = "bomb";
    // Unit Costs
    public static final int ARCHER_COST = 10;
    public static final int BOMB_COST = 60;
    // Fixed Production
    public static final int PRODUCTION_FIXED6 = 6;
    // Valid terrain types
    public static final String PLAINS = "plains";
    public static final String OCEANS = "ocean";
    public static final String FOREST = "forest";
    public static final String HILLS = "hills";
    public static final String MOUNTAINS = "mountain";
    // Valid production balance types
    public static final String productionFocus = "hammer";
    public static final String foodFocus = "apple";
    // Positions for cities
    public static final Position RED_CITY_POSITION = new Position(1, 1);
    public static final Position BLUE_CITY_POSITION = new Position(4, 1);
    // Positions for mountains, oceans etc.
    public static final Position OCEAN_POSITION = new Position(1, 0);
    public static final Position HILLS_POSITION = new Position(0, 1);
    public static final Position MOUNTAINS_POSITION = new Position(2, 2);
    // Positions for Archers, legionaries and Settlers
    public static final Position ARCHER_POSITION_RED = new Position(2, 0);
    public static final Position LEGION_POSITION_BLUE = new Position(3, 2);
    public static final Position SETTLER_POSITION_RED = new Position(4, 3);
}
