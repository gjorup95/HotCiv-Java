package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.WinningCondition;

public class WinningConditionAlphaCiv implements WinningCondition {

    public WinningConditionAlphaCiv() {
    }

    @Override
    public Player getWinner() {
        if (Game.getAge() == -3000) {
            return Player.RED;
        }
        return null;
    }
}
