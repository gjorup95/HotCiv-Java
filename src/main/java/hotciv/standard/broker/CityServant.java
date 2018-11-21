package hotciv.standard.broker;

import frds.broker.Servant;
import hotciv.framework.City;
import hotciv.framework.Player;

import java.util.UUID;

public class CityServant implements City, Servant {

    private final String objectId;
    private final Player owner;

    public CityServant(Player owner) {
        this.owner = owner;
        objectId = UUID.randomUUID().toString();
    }

    public String getObjectId() {
        return objectId;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public int getTreasury() {
        return 0;
    }

    @Override
    public String getProduction() {
        return null;
    }

    @Override
    public String getWorkforceFocus() {
        return null;
    }
}
