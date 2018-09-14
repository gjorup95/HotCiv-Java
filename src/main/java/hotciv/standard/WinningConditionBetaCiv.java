package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.WinningCondition;
import hotciv.framework.Game;

/**
 * @ author Troels Gjørup
 * 14-09-2018
 */
public class WinningConditionBetaCiv implements WinningCondition {
    private final Game game;

    public WinningConditionBetaCiv(Game game) {
        this.game = game;

    }

    @Override
    public Player getWinner() {
        if (game.getCityAt(GameConstants.RED_CITY_POSITION).getOwner().equals(Player.RED) && game.getCityAt(GameConstants.BLUE_CITY_POSITION).getOwner().equals(Player.RED)){
            return Player.RED;
        }
        return null;
    }
}
