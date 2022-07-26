package hotciv.standard.broker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.standard.CityImpl;
import hotciv.stub.StubCity;
import hotciv.stub.StubGame3;

import javax.servlet.http.HttpServletResponse;

public class HotCivCityInvoker implements Invoker {

    private Gson gson;
    private final ObjectStorage objectStorage;

    public HotCivCityInvoker(ObjectStorage objectStorage) {
        gson = new Gson();
        this.objectStorage = objectStorage;
    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject reply = null;

        // Demarshall parameters into something useful
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(payload).getAsJsonArray();

        City city = lookUpCity(objectId);

        if (operationName.equals(MarshallingConstants.CITY_GET_OWNER)) {
            Player owner = city.getOwner();
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson(owner));
        }

        if (operationName.equals(MarshallingConstants.CITY_GET_SIZE)) {
            int size = city.getSize();
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson(size));
        }

        if (operationName.equals(MarshallingConstants.CITY_GET_TREASURY)) {
            int treasury = city.getTreasury();
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson(treasury));
        }

        if (operationName.equals(MarshallingConstants.CITY_GET_PRODUCTION)) {
            String production = city.getProduction();
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson(production));
        }

        if(operationName.equals(MarshallingConstants.CITY_GET_WORKFORCE_FOCUS)) {
            String workforceFocus = city.getWorkforceFocus();
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson(workforceFocus));
        }

        return reply;
    }

    private City lookUpCity(String objectId) {
        City city = objectStorage.getCity(objectId);
        return city;
    }

}
