package hotciv.standard.broker;

import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import javax.servlet.http.HttpServletResponse;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.standard.CityImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;
import hotciv.stub.StubGame3;
import javafx.geometry.Pos;

public class HotCivGameInvoker implements Invoker {

    private final Game game;
    private final ObjectStorage objectStorage;

    public HotCivGameInvoker(Game servant, ObjectStorage objectStorage) {
        this.game = servant;
        this.objectStorage = objectStorage;
    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject reply = null;
        Gson gson = new Gson();

        // Demarshall arguments into something useful
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(payload).getAsJsonArray();

        if(operationName.equals(MarshallingConstants.GAME_GET_WINNER)) {
            Player winner = game.getWinner();
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson(winner));
        }

        if(operationName.equals(MarshallingConstants.GAME_GET_AGE)) {
            int age = game.getAge();
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson(age));
        }

        if(operationName.equals(MarshallingConstants.GAME_GET_PLAYERINTURN)) {
            Player inTurn = game.getPlayerInTurn();
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson(inTurn));
        }

        if(operationName.equals(MarshallingConstants.GAME_END_OF_TURN)) {
            game.endOfTurn();
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson("The turn has ended. Next player's turn."));
        }

        if(operationName.equals(MarshallingConstants.GAME_MOVE_UNIT)) {
            boolean movedUnit = false;
            Position from = gson.fromJson(array.get(0), Position.class);
            Position to = gson.fromJson(array.get(1), Position.class);
            movedUnit = game.moveUnit(from, to);
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson(movedUnit));
        }

        if(operationName.equals(MarshallingConstants.GAME_CHANGE_PRODUCTION_IN_CITY_AT)) {
            boolean hasChangedProduction = false;
            Position p = gson.fromJson(array.get(0), Position.class);
            String production = gson.fromJson(array.get(1), String.class);
            game.changeProductionInCityAt(p, production);
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson(null));
        }

        if(operationName.equals(MarshallingConstants.GAME_PERFORM_UNIT_ACTION_AT)) {
            Position p = gson.fromJson(array.get(0), Position.class);
            game.performUnitActionAt(p);
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson("The unit performed the action at " + p));
        }

        if(operationName.equals(MarshallingConstants.GAME_GET_CITY_AT)) {
            Position p = gson.fromJson(array.get(0), Position.class);

            Player owner = game.getCityAt(p).getOwner();
            CityImpl city = new CityImpl(owner);
            String id = city.getId();
            objectStorage.putCity(id,city);

            reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(id));
        }

        if(operationName.equals(MarshallingConstants.GAME_GET_TILE_AT)) {
            Position p = gson.fromJson(array.get(0), Position.class);

            TileImpl tile = new TileImpl(game.getTileAt(p).getTypeString());
            String id = tile.getId();
            objectStorage.putTile(id,tile);

            reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(id));
        }

        if(operationName.equals(MarshallingConstants.GAME_GET_UNIT_AT)) {
            Position p = gson.fromJson(array.get(0), Position.class);

            Player owner = game.getUnitAt(p).getOwner();
            String unitType = game.getUnitAt(p).getTypeString();
            UnitImpl unit = new UnitImpl(unitType, owner);
            String id = unit.getId();
            objectStorage.putUnit(id,unit);

            reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(id));
        }
        return reply;
    }
}
