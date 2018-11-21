package hotciv.standard.broker;

import frds.broker.Servant;
import hotciv.framework.Player;
import hotciv.framework.Unit;

import java.util.UUID;

public class UnitServant implements Unit, Servant {

    private final String unitType;
    private final Player owner;
    private final String objectId;

    public UnitServant(String s, Player owner) {
        this.unitType = s;
        this.owner = owner;
        objectId = UUID.randomUUID().toString();
    }

    public String getObjectId() {
        return objectId;
    }

    @Override
    public String getTypeString() {
        return null;
    }

    @Override
    public Player getOwner() {
        return null;
    }

    @Override
    public int getMoveCount() {
        return 0;
    }

    @Override
    public int getDefensiveStrength() {
        return 0;
    }

    @Override
    public int getAttackingStrength() {
        return 0;
    }
}
