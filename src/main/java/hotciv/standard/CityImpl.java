package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Player;

/**
 * @ author Troels Gjørup
 * 29-08-2018
 */
public class CityImpl implements City {
    private Player owner;
    private int cityTreasure;
    private int population;

    public CityImpl(Player owner){
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
        return "0";
    }

    @Override
    public String getWorkforceFocus() {
        return null;
    }

    public void addTreasury(int i) {
        cityTreasure+= i;
    }
}
