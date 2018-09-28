package hotciv.standard;

import hotciv.framework.*;

public class WinningConditionAlphaCiv implements WinningCondition {

    private final GameImpl game;

    public WinningConditionAlphaCiv(GameImpl game) {
        this.game = game;
    }

    @Override
    public Player getWinner() {
        if (game.getAge() == -3000) {
            return game.getPlayer(GameConstants.RED);
        }
        return null;
    }

    @Override
    public void conquerCity(Position toConquer) {

    }

}

