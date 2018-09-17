package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.UnitActions;

/**
 * @ author Troels Gjørup
 * 16-09-2018
 */
public class UnitActionsGammaCiv implements UnitActions {
    private Game game;

    public UnitActionsGammaCiv(Game game) {
        this.game = game;

    }

    @Override
    public boolean legalPerformSettlerActionAt(Position performPosition) {
        if (game.getUnitAt(performPosition) != null && game.getUnitAt(performPosition).getTypeString().equals(GameConstants.SETTLER) && game.getCityAt(performPosition) == null && game.getPlayerInTurn().equals(game.getUnitAt(performPosition).getOwner())) {
            return true;
        }

        return false;
    }

    @Override
    public boolean legalPerformArcherFortifyActionAt(Position performPosition) {
        if (game.getUnitAt(performPosition) != null && game.getUnitAt(performPosition).getTypeString().equals(GameConstants.ARCHER) && game.getUnitAt(performPosition).getOwner().equals(game.getPlayerInTurn())) {
            return true;

        }
        return false;
    }
}
