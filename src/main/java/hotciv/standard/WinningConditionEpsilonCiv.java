package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.WinningCondition;


/**
 * @ author Troels Gjørup
 * 27-09-2018
 */
public class WinningConditionEpsilonCiv implements WinningCondition {
    private final GameImpl game;

    public WinningConditionEpsilonCiv(GameImpl game) {
        this.game = game;
    }

    @Override
    public Player getWinner() {
        if (game.getRedBattlesWon() >= 3){
            return Player.RED;
        }
        if (game.getBlueBattlesWon() >= 3){
            return Player.BLUE;
        }
        return null;
    }

    @Override
    public void conquerCity(Position toConquer) {

    }
}
