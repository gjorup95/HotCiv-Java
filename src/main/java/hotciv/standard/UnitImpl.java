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
    private int moveCount;
    private int defensiveStrength;
    private boolean actionUsed;


    public UnitImpl(String s, Player owner) {
        this.unitType = s;
        this.owner = owner;
        defensiveStrength = 1;
        actionUsed = false;
        moveCount = 1;
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
        return moveCount;
    }

    @Override
    public int getDefensiveStrength() {
        return defensiveStrength;
    }

    @Override
    public int getAttackingStrength() {
        return 0;
    }

    public void setMoveCount(int moveCountAfterMovement) {
        moveCount = moveCountAfterMovement;
    }

    public void setDefensiveStrength(int defensiveStrength) {
        this.defensiveStrength = defensiveStrength;
    }

    public void changeIfActionUsed(boolean state) {

        actionUsed = state;
    }

    public boolean getIsActionUsed() {

        return actionUsed;

    }
}
