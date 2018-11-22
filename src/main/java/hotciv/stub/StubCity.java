package hotciv.stub;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

import java.util.UUID;

public class StubCity implements City {
    private Player owner;
    private String productionFocus = GameConstants.foodFocus;
    private String workforceFocus = GameConstants.ARCHER;
    private int size;
    private final String objectId;

    public StubCity(Player owner, int size) {
        this.owner = owner;
        this.size = size;
        objectId = UUID.randomUUID().toString();
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getTreasury() {
        return 47;
    }

    @Override
    public String getProduction() {
        return productionFocus;
    }

    @Override
    public String getWorkforceFocus() {
        return workforceFocus;
    }

    @Override
    public String getId() {
        return objectId;
    }

    public void setWorkforceFocus(String workforceFocus) {
        this.workforceFocus = workforceFocus;
    }

    public void setProductionFocus(String productionFocus) {
        this.productionFocus = productionFocus;
    }
}
