package hotciv.standard;

import hotciv.framework.Position;
import hotciv.framework.UnitActions;

/**
 * @ author Troels Gjørup
 * 16-09-2018
 */
public class UnitActionsAlphaCiv implements UnitActions {

    @Override
    public boolean performSettlerActionAt(Position performPosition) {
        return false;
    }

    @Override
    public boolean performArcherFortifyActionAt(Position performPosition) {
        return false;
    }

    @Override
    public boolean performBombActionAt(Position performPosition) {

        return false;
    }

    @Override
    public void performAction(Position performActionAt) {
    }
}
