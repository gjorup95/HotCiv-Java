package hotciv.standard.broker;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.Game;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ author Troels Gjørup
 * 21-11-2018
 */
public class RootInvoker implements Invoker {
    private final Game game;
    private final ObjectStorage objectStorage;
    private final Map<String,Invoker> invokerMap;
    private Gson gson;

    public RootInvoker(Game game){
        this.game = game;
        gson = new Gson();
        objectStorage = new InMemoryStorage();
        invokerMap = new HashMap<>();
        Invoker gameInvoker = new HotCivGameInvoker(game, objectStorage);
        invokerMap.put(MarshallingConstants.GAME_PREFIX, gameInvoker);
        Invoker cityInvoker = new HotCivCityInvoker(objectStorage);
        invokerMap.put(MarshallingConstants.CITY_PREFIX, cityInvoker);
        Invoker tileInvoker = new HotCivTileInvoker(objectStorage);
        invokerMap.put(MarshallingConstants.TILE_PREFIX, tileInvoker);
        Invoker unitInvoker = new HotCivUnitInvoker(objectStorage);
        invokerMap.put(MarshallingConstants.UNIT_PREFIX, unitInvoker);
    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject reply = null;

        String type = operationName.substring(0,operationName.indexOf('_'));
        Invoker subInvoker = invokerMap.get(type);

        try {
            reply = subInvoker.handleRequest(objectId, operationName, payload);
        } catch (RuntimeException e){
            reply =
                    new ReplyObject(HttpServletResponse.SC_NOT_FOUND, e.getMessage());

        }
        return reply;
    }
}
