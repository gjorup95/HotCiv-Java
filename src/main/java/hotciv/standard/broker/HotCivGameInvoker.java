package hotciv.standard.broker;

import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.Game;
import hotciv.framework.Player;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import javax.servlet.http.HttpServletResponse;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.Position;
import hotciv.framework.Tile;
import hotciv.standard.GameImpl;
import javafx.geometry.Pos;

public class HotCivGameInvoker implements Invoker {

    private final Game game;

    public HotCivGameInvoker(Game servant) {
        this.game = servant;
    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject reply = null;
        Gson gson = new Gson();

        // Demarshall parameters into something useful..
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
            Position from = gson.fromJson(array.get(0), Position.class);
            Position to = gson.fromJson(array.get(1), Position.class);
            game.moveUnit(from, to);
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson("the unit was moved from" + from + "to " + to));
        }

        if(operationName.equals(MarshallingConstants.GAME_CHANGE_PRODUCTION_IN_CITY_AT)) {
            Position p = gson.fromJson(array.get(0), Position.class);
            String production = gson.fromJson(array.get(1), String.class);
            game.changeProductionInCityAt(p, production);
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson("The production was changed to " + production + " in city at" + p));
        }

        if(operationName.equals(MarshallingConstants.GAME_PERFORM_UNIT_ACTION_AT)) {
            Position p = gson.fromJson(array.get(0), Position.class);
            game.performUnitActionAt(p);
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson("The unit performed the action at " + p));
        }

        return reply;
    }
}
