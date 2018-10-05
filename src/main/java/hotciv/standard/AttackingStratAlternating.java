package hotciv.standard;

import hotciv.framework.AttackingStrat;
import hotciv.framework.Position;
import hotciv.framework.WinningCondition;

public class AttackingStratAlternating implements AttackingStrat {

    private AttackingStrat attackingStratBefore20Rounds;
    private AttackingStrat attackingStratAfter20Rounds;
    private AttackingStrat currentState;
    private GameImpl game;

    public AttackingStratAlternating(AttackingStrat attackingStratBefore20Rounds, AttackingStrat attackingStratAfter20Rounds, GameImpl game){
        this.attackingStratBefore20Rounds = attackingStratBefore20Rounds;
        this.attackingStratAfter20Rounds = attackingStratAfter20Rounds;
        this.game = game;
        this.currentState = attackingStratBefore20Rounds;
    }

    @Override
    public int calculateAttackerStr(Position from) {
        if(game.getNoOfRounds() >= 20 && currentState == attackingStratBefore20Rounds) {
            currentState = attackingStratAfter20Rounds;
        }
        return currentState.calculateAttackerStr(from);
    }

    @Override
    public int calculateDefensiveStr(Position to) {
        if(game.getNoOfRounds() >= 20 && currentState == attackingStratBefore20Rounds) {
            currentState = attackingStratAfter20Rounds;
        }
        return currentState.calculateDefensiveStr(to);
    }

    @Override
    public int attackResult(Position from, Position to) {
        if(game.getNoOfRounds() >= 20 && currentState == attackingStratBefore20Rounds) {
            currentState = attackingStratAfter20Rounds;
        }
        return currentState.attackResult(from, to);
    }

    @Override
    public boolean attack(Position from, Position to) {
        if(game.getNoOfRounds() >= 20 && currentState == attackingStratBefore20Rounds) {
            currentState = attackingStratAfter20Rounds;
        }
        return currentState.attack(from, to);
    }
}
