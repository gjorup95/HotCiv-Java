package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.WinningCondition;

public class WinningConditionAlternating implements WinningCondition {

    private WinningCondition winningConditionBefore20Rounds;
    private WinningCondition winningConditionAfter20Rounds;
    private WinningCondition currentState;
    private GameImpl game;
    /** This winning condition is a combination of both Beta and Epsilon Winning conditions, so both WinningConditions are imported. */
    public WinningConditionAlternating(WinningCondition winningConditionBefore20Rounds, WinningCondition winningConditionAfter20Rounds, GameImpl game) {
        this.winningConditionBefore20Rounds = winningConditionBefore20Rounds;
        this.winningConditionAfter20Rounds = winningConditionAfter20Rounds;
        this.currentState = null;
        this.game = game;
    }

    @Override
    public Player getWinner() {
        if(game.isBefore20Rounds()) {
            currentState = winningConditionBefore20Rounds;
        }
        else {
            currentState = winningConditionAfter20Rounds;
        }
        return currentState.getWinner();
    }

    @Override
    public void conquerCity(Position toConquer) {
        if (game.getCityAt(toConquer) != null && game.getCityAt(toConquer).getOwner() != game.getPlayerInTurn()) {
            game.getCityAt(toConquer).setOwner(game.getPlayerInTurn());
        }
    }
}

