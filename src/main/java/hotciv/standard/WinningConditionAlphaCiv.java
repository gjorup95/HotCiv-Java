package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.WinningCondition;

public class WinningConditionAlphaCiv implements WinningCondition {

    private final Game game;

    public WinningConditionAlphaCiv(Game game) {
        this.game = game;
    }

    @Override
    public Player getWinner() {
        if (game.getAge() == -3000) {
            return Player.RED;
        }
        return null;
    }

}

