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
        for (Player p : game.playerMap.values()) {
            if (p.getAttackingBattlesWon() >= 3) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void conquerCity(Position toConquer) {

    }
}
