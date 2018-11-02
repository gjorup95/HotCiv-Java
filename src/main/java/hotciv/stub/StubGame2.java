package hotciv.stub;

import hotciv.framework.*;
import hotciv.standard.CityImpl;

import java.util.*;

/**
 * Test stub for game for visual testing of
 * minidraw based graphics.
 * <p>
 * SWEA support code.
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

public class StubGame2 implements Game {

    // === City handling ===
    private Position pos_city_red;
    private Position pos_city_blue;

    private City red_city;
    private City blue_city;

    // === Unit handling ===
    private Position pos_archer_red;
    private Position pos_legion_blue;
    private Position pos_settler_red;
    private Position pos_bomb_red;

    private Unit red_archer;
    private int age = -4000;

    public StubGame2() {
        defineWorld(1);
        // AlphaCiv configuration
        pos_archer_red = new Position(2, 0);
        pos_legion_blue = new Position(3, 2);
        pos_settler_red = new Position(4, 3);
        pos_bomb_red = new Position(6, 4);

        // the only one I need to store for this stub
        red_archer = new StubUnit(GameConstants.ARCHER, Player.RED);

        inTurn = Player.RED;

        pos_city_red = new Position(0, 0);
        pos_city_blue = new Position(1, 1);

        red_city = new StubCity(Player.RED);
        blue_city = new StubCity(Player.BLUE);
    }

    public Unit getUnitAt(Position p) {
        if (p.equals(pos_archer_red)) {
            return red_archer;
        }
        if (p.equals(pos_settler_red)) {
            return new StubUnit(GameConstants.SETTLER, Player.RED);
        }
        if (p.equals(pos_legion_blue)) {
            return new StubUnit(GameConstants.LEGION, Player.BLUE);
        }
        if (p.equals(pos_bomb_red)) {
            return new StubUnit(ThetaConstants.BOMB, Player.RED);
        }
        return null;
    }

    // this somehow renders the citys in the graphics
    public City getCityAt(Position p) {
        if (p.equals(pos_city_red)) {
            return red_city;
        }
        if (p.equals(pos_city_blue)) {
            return blue_city;
        }
        return null;
    }

    // Stub only allows moving red archer
    public boolean moveUnit(Position from, Position to) {
        System.out.println("-- StubGame2 / moveUnit called: " + from + "->" + to);
        if (from.equals(pos_archer_red)) {
            pos_archer_red = to;
        }
        // notify our observer(s) about the changes on the tiles
        gameObserver.worldChangedAt(from);
        gameObserver.worldChangedAt(to);
        return true;
    }

    // === Turn handling ===
    private Player inTurn;

    public void endOfTurn() {
        System.out.println("-- StubGame2 / endOfTurn called.");
        inTurn = (getPlayerInTurn() == Player.RED ?
                Player.BLUE :
                Player.RED);
        // no age increments

        gameObserver.turnEnds(inTurn, age);
        age = age+100;
    }

    public Player getPlayerInTurn() {
        return inTurn;
    }


    // === Observer handling ===
    protected GameObserver gameObserver;

    // observer list is only a single one...
    public void addObserver(GameObserver observer) {
        gameObserver = observer;
    }

    // A simple implementation to draw the map of DeltaCiv
    protected Map<Position, Tile> world;

    public Tile getTileAt(Position p) {
        return world.get(p);
    }

    /**
     * define the world.
     *
     * @param worldType 1 gives one layout while all other
     *                  values provide a second world layout.
     */

    protected void defineWorld(int worldType) {
        world = new HashMap<Position, Tile>();
        for (int r = 0; r < GameConstants.WORLDSIZE; r++) {
            for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
                Position p = new Position(r, c);
                world.put(p, new StubTile(GameConstants.PLAINS));
            }
        }
    }

    // TODO: Add more stub behaviour to test MiniDraw updating

    public Player getWinner() {
        return null;
    }

    public int getAge() {
        return age;
    }

    public void changeWorkForceFocusInCityAt(Position p, String balance) {
    }

    public void changeProductionInCityAt(Position p, String unitType) {
    }

    public void performUnitActionAt(Position p) {
    }

    public void setTileFocus(Position position) {
        // TODO: setTileFocus implementation pending.
        System.out.println("-- StubGame2 / setTileFocus called.");
        System.out.println(" *** IMPLEMENTATION PENDING ***");
    }

}

class StubUnit implements Unit {
    private String type;
    private Player owner;

    public StubUnit(String type, Player owner) {
        this.type = type;
        this.owner = owner;
    }

    public String getTypeString() {
        return type;
    }

    public Player getOwner() {
        return owner;
    }

    public int getMoveCount() {
        return 1;
    }

    public int getDefensiveStrength() {
        return 0;
    }

    public int getAttackingStrength() {
        return 0;
    }
}

class StubCity implements City {
    private Player owner;

    public StubCity(Player owner) {
        this.owner = owner;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public int getTreasury() {
        return 0;
    }

    @Override
    public String getProduction() {
        return null;
    }

    @Override
    public String getWorkforceFocus() {
        return null;
    }
}
