package hotciv.stub;

import frds.broker.Servant;
import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.reflect.*;
import hotciv.standard.NullObserver;
import hotciv.standard.TileImpl;

public class StubGame3 implements Game, Servant {
    // Positions
    private Position position_of_green_city = new Position(1,1);
    private Position position_of_green_archer;
    private GameObserver gameObserver;
    private City green_city;

    // Units
    private Unit green_archer;

    public StubGame3() {
        green_archer = new StubUnit(GameConstants.ARCHER, Player.GREEN);
        position_of_green_archer = new Position(5,5);
        gameObserver = new NullObserver();

        green_city = new CityImpl(Player.GREEN);
    }

    @Override
    public Tile getTileAt(Position p) {
        return null;
    }

    @Override
    public Unit getUnitAt(Position p) {
        return null;
    }

    @Override
    public City getCityAt(Position p) {
        if(p.equals(position_of_green_city)) {
            return new StubCity(Player.GREEN, 4);
        }
        return null;
    }

    @Override
    public Player getPlayerInTurn() {
        return Player.BLUE;
    }

    @Override
    public Player getWinner() {
        return Player.YELLOW;
    }

    @Override
    public int getAge() {
        return 42;
    }


    @Override
    public boolean moveUnit(Position from, Position to) {
        System.out.println("-- StubGame3 / moveUnit called: " + from + "->" + to);

        // notify our observer(s) about the changes on the tiles
        gameObserver.worldChangedAt(from);
        gameObserver.worldChangedAt(to);
        return true;
    }

    @Override
    public void endOfTurn() {

    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {

    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {

    }

    @Override
    public void performUnitActionAt(Position p) {

    }

    @Override
    public void addObserver(GameObserver observer) {

    }

    @Override
    public void setTileFocus(Position position) {

    }
}
