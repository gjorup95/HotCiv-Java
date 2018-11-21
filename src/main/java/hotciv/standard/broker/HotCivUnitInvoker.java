package hotciv.standard.broker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;
import hotciv.stub.StubUnit;

import javax.servlet.http.HttpServletResponse;

public class HotCivUnitInvoker implements Invoker {

    private Gson gson;
    private final ObjectStorage objectStorage;

    public HotCivUnitInvoker(ObjectStorage objectStorage) {
        gson = new Gson();
        this.objectStorage = objectStorage;
    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject reply = null;

        // Demarshall parameters into something useful
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(payload).getAsJsonArray();

        Unit unit = lookUpUnit(objectId);

        if(operationName.equals(MarshallingConstants.UNIT_GET_TYPE_STRING)) {
            String unitType = unit.getTypeString();
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson(unitType));
        }

        if(operationName.equals(MarshallingConstants.UNIT_GET_OWNER)) {
            Player owner = unit.getOwner();
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson(owner));
        }

        if(operationName.equals(MarshallingConstants.UNIT_GET_MOVE_COUNT)) {
            int moveCount = unit.getMoveCount();
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson(moveCount));
        }

        if(operationName.equals(MarshallingConstants.UNIT_GET_DEFENSIVE_STRENGTH)) {
            int defStrength = unit.getDefensiveStrength();
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson(defStrength));
        }

        if(operationName.equals(MarshallingConstants.UNIT_GET_ATTACK_STRENGTH)) {
            int attackStrength = unit.getAttackingStrength();
            reply = new ReplyObject((HttpServletResponse.SC_OK), gson.toJson(attackStrength));
        }
        return reply;
    }

    private Unit lookUpUnit(String objectId) {
        Unit unit = objectStorage.getUnit(objectId);
        return unit;
    }
}
