package hotciv.standard;

import hotciv.framework.AttackingStrat;
import hotciv.framework.Position;

/**
 * @ author Troels Gjørup
 * 28-09-2018
 */
public class AttackingStratAlphaCiv implements AttackingStrat {
    @Override
    public int calculateAttackerStr(Position from) {
        return 0;
    }

    @Override
    public int calculateDefensiveStr(Position to) {
        return 0;
    }

    @Override
    public int attackResult(Position from, Position to) {
        return 0;
    }

    @Override
    public boolean attack(Position from, Position to) {
        return true;
    }
}
