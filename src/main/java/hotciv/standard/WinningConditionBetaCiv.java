package hotciv.standard;

import hotciv.framework.*;

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
        if (game.getCityAt(GameConstants.RED_CITY_POSITION).getOwner().equals(Player.RED) && game.getCityAt(GameConstants.BLUE_CITY_POSITION).getOwner().equals(Player.RED)) {
            return Player.RED;
        } else if ((game.getCityAt(GameConstants.RED_CITY_POSITION).getOwner().equals(Player.BLUE) &&
                game.getCityAt(GameConstants.BLUE_CITY_POSITION).getOwner().equals(Player.BLUE))) {
            return Player.BLUE;
        }
        return null;
    }

    @Override
    public boolean legalConquerCity(Position toConquer) {
        if (game.getCityAt(toConquer) != null && game.getCityAt(toConquer).getOwner() != game.getPlayerInTurn()) {
           return true;

        }
        else {
            return false;
        }
    }
}
