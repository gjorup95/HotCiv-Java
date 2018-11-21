package hotciv.standard.broker;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.Game;

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
        Invoker gameInvoker = new HotCivGameInvoker(game);
        Invoker cityInvoker = new HotCivCityInvoker();

    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        return null;
    }
}
