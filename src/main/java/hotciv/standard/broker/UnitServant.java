package hotciv.standard.broker;

import frds.broker.Servant;
import hotciv.framework.Player;
import hotciv.framework.Unit;

import java.util.UUID;

public class UnitServant implements Unit, Servant {

    private final String unitType;
    private final Player owner;
    private final String objectId;
    private int defensiveStrength;
    private int attackStrength;

    public UnitServant(String s, Player owner) {
        this.unitType = s;
        this.owner = owner;
        objectId = UUID.randomUUID().toString();
        defensiveStrength = 1;
        attackStrength = 1;

    }

    public String getObjectId() {
        return objectId;
    }

    @Override
    public String getTypeString() {
        return unitType;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getMoveCount() {
        return 1;
    }

    @Override
    public int getDefensiveStrength() {
        return defensiveStrength;
    }

    @Override
    public int getAttackingStrength() {
        return attackStrength;
    }
}
