package hotciv.standard;

import hotciv.framework.Position;
import hotciv.framework.UnitActions;

/**
 * @ author Troels Gjørup
 * 16-09-2018
 */
public class UnitActionsAlphaCiv implements UnitActions {


    @Override
    public boolean legalPerformSettlerActionAt(Position performPosition) {
        return false;
    }

    @Override
    public boolean legalPerformArcherFortifyActionAt(Position performPosition) {
        return false;
    }

}
