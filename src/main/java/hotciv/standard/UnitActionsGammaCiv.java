package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.UnitActions;

/**
 * @ author Troels Gjørup
 * 16-09-2018
 */
public class UnitActionsGammaCiv implements UnitActions {
    private GameImpl game;

    public UnitActionsGammaCiv(GameImpl game) {
        this.game = game;
    }

    @Override
    public boolean performSettlerActionAt(Position performPosition) {

        if (!game.unitIsNotNull(performPosition)) {
            return false;
        }
        if (game.playerInTurnIsNotOwnerOfUnit(performPosition)) {
            return false;
        }
        if (game.cityIsNotNull(performPosition)) {
            return false;
        }
        game.addCity(performPosition, game.getPlayerInTurn());
        game.removeUnit(performPosition);
        return true;
    }

    @Override
    public boolean performArcherFortifyActionAt(Position performPosition) {
        if (!game.unitIsNotNull(performPosition)) {
            return false;
        }

        if (game.playerInTurnIsNotOwnerOfUnit(performPosition)) {
            return false;
        }

        if (game.getUnitAt(performPosition).getIsActionUsed()) {
            game.getUnitAt(performPosition).setDefensiveStrength(game.getUnitAt(performPosition).getDefensiveStrength() / 2);
            game.getUnitAt(performPosition).setMoveCount(1);
            game.getUnitAt(performPosition).changeIfActionUsed(false);
        } else {
            game.getUnitAt(performPosition).setDefensiveStrength(game.getUnitAt(performPosition).getDefensiveStrength() * 2);
            game.getUnitAt(performPosition).setMoveCount(0);
            game.getUnitAt(performPosition).changeIfActionUsed(true);
        }
        return true;
    }


    @Override
    public void performAction(Position p) {
        switch (game.getUnitAt(p).getTypeString()) {
            case GameConstants.ARCHER:
                performArcherFortifyActionAt(p);
                break;
            case GameConstants.SETTLER:
                performSettlerActionAt(p);
                break;
            default:
        }
    }
}
