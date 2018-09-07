package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

/**
 * @ author Troels Gjørup
 * 29-08-2018
 */
public class CityImpl implements City {
    private Player owner;
    private int cityTreasure;
    private int population;
    private String unitProductionFocus;

    public CityImpl(Player owner) {
        this.owner = owner;
        population = 1;
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

    public void setUnitProductionFocus (String unitFocus) {
        unitProductionFocus = unitFocus;
    }

    public void addTreasury(int i) {
        cityTreasure += i;
    }
}
