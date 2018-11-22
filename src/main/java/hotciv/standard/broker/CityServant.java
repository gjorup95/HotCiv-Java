package hotciv.standard.broker;

import frds.broker.Servant;
import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

import java.util.UUID;

public class CityServant implements City, Servant {

    private final String objectId;
    private final Player owner;
    private int cityTreasure;
    private int population;
    private String unitProductionFocus;

    public CityServant(Player owner) {
        this.owner = owner;
        objectId = UUID.randomUUID().toString();
        population = 1;
        unitProductionFocus = GameConstants.ARCHER;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getSize() {
        return population;
    }

    @Override
    public int getTreasury() {
        return cityTreasure;
    }

    @Override
    public String getProduction() {
        return unitProductionFocus;
    }

    @Override
    public String getWorkforceFocus() {
        return null;
    }

    @Override
    public String getId() {
        return objectId;
    }
}
