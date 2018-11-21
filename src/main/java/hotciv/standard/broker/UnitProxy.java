package hotciv.standard.broker;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitProxy implements Unit, ClientProxy {

    private final Requestor requestor;
    private final String objectId;

    public UnitProxy (String objectId, Requestor crh) {
        this.requestor = crh;
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    @Override
    public String getTypeString() {
        String unitType = null;
        unitType = requestor.sendRequestAndAwaitReply("not_used", MarshallingConstants.UNIT_GET_TYPE_STRING, String.class, "no-arguments");
        return unitType;
    }

    @Override
    public Player getOwner() {
        Player owner = null;
        owner = requestor.sendRequestAndAwaitReply("not_used", MarshallingConstants.UNIT_GET_OWNER, Player.class, "no-arguments");
        return owner;
    }

    @Override
    public int getMoveCount() {
        int moveCount = 0;
        moveCount = requestor.sendRequestAndAwaitReply("not_used", MarshallingConstants.UNIT_GET_MOVE_COUNT, int.class, "no-arguments");
        return moveCount;
    }

    @Override
    public int getDefensiveStrength() {
        int defStrength = 0;
        defStrength = requestor.sendRequestAndAwaitReply("not_used", MarshallingConstants.UNIT_GET_DEFENSIVE_STRENGTH, int.class, "no-arguments");
        return defStrength;
    }

    @Override
    public int getAttackingStrength() {
        int attackStrength = 0;
        attackStrength = requestor.sendRequestAndAwaitReply("not_used", MarshallingConstants.UNIT_GET_ATTACK_STRENGTH, int.class, "no-arguments");
        return attackStrength;
    }

}
