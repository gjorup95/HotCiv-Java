package hotciv.standard.broker;

import frds.broker.IPCException;
import frds.broker.Requestor;
import frds.broker.ClientProxy;
import hotciv.framework.*;

public class GameProxy implements Game, ClientProxy {

    private final Requestor requestor;

    public GameProxy(Requestor crh) {
        this.requestor = crh;
    }

    @Override
    public Player getWinner() {
        Player p = null;
        p = requestor.sendRequestAndAwaitReply("not_used",MarshallingConstants.GAME_GET_WINNER, Player.class, "no_argument");
        return p;
    }

    @Override
    public int getAge() {
        int age = 0;
        age = requestor.sendRequestAndAwaitReply("not_used",
                MarshallingConstants.GAME_GET_AGE,
                int.class, "no-argument");
        return age;
    }

    @Override
    public Player getPlayerInTurn() {
        Player p = null;
        p = requestor.sendRequestAndAwaitReply("not_used", MarshallingConstants.GAME_GET_PLAYERINTURN, Player.class, "no-argument");
        return p;
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        boolean unitMoved;
        unitMoved = requestor.sendRequestAndAwaitReply("not_used",
                "game_move_unit",
                boolean.class, from, to);
        return unitMoved;
    }

    @Override
    public void endOfTurn() {
        requestor.sendRequestAndAwaitReply("not_used", MarshallingConstants.GAME_END_OF_TURN, null, "no-arguments");
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        requestor.sendRequestAndAwaitReply("not_used", MarshallingConstants.GAME_CHANGE_PRODUCTION_IN_CITY_AT, String.class, p, unitType);
    }

    @Override
    public void performUnitActionAt(Position p) {
        requestor.sendRequestAndAwaitReply("not_used", MarshallingConstants.GAME_PERFORM_UNIT_ACTION_AT, null, p);
    }

    @Override
    public Tile getTileAt(Position p) {
        String id = requestor.sendRequestAndAwaitReply("none", MarshallingConstants.GAME_GET_TILE_AT, String.class, p);
        Tile proxy = new TileProxy(id, requestor);
        return proxy;
    }

    @Override
    public Unit getUnitAt(Position p) {
        String id = requestor.sendRequestAndAwaitReply("none", MarshallingConstants.GAME_GET_UNIT_AT, String.class, p);
        Unit proxy = new UnitProxy(id, requestor);
        return proxy;
    }


    @Override
    public City getCityAt(Position p) {
        String id = requestor.sendRequestAndAwaitReply("none", MarshallingConstants.GAME_GET_CITY_AT, String.class, p);
        City proxy = new CityProxy(id, requestor);
        return proxy;
    }


    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {

    }

    @Override
    public void addObserver(GameObserver observer) {

    }

    @Override
    public void setTileFocus(Position position) {

    }
}
