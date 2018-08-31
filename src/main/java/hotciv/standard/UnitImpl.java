package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Unit;

/**
 * @ author Troels Gjørup
 * 31-08-2018
 */
public class UnitImpl implements Unit {
    private final String unitType;
    private final Player owner;

    public UnitImpl(String s, Player owner) {
        this.unitType = s;
        this.owner = owner;
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
