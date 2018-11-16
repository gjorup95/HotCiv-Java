package hotciv.standard.broker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.GameConstants;
import hotciv.framework.Tile;
import hotciv.stub.StubTile;

import javax.servlet.http.HttpServletResponse;

public class HotCivTileInvoker implements Invoker {

    private Gson gson;

    public HotCivTileInvoker() {
        gson = new Gson();
    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject reply = null;

        // Demarshall parameters into something useful
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(payload).getAsJsonArray();

        Tile tile = lookUpTile(objectId);

        if(operationName.equals(MarshallingConstants.TILE_GET_TYPE_STRING)) {
            String tileType = tile.getTypeString();
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson(tileType));
        }
        return reply;
    }

    private Tile lookUpTile(String objectId) {
        Tile tile = new StubTile(GameConstants.OCEANS);
        return tile;
    }
}
