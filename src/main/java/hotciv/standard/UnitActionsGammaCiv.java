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
    public boolean performSettlerActionAt(Position performActionAt) {

        if (!game.unitIsNotNull(performActionAt)) {
            return false;
        }
        if (game.playerInTurnIsNotOwnerOfUnit(performActionAt)) {
            return false;
        }
        if (game.cityIsNotNull(performActionAt)) {
            return false;
        }
        game.addCity(performActionAt, game.getPlayerInTurn());
        game.removeUnit(performActionAt);
        return true;
    }

    @Override
    public boolean performArcherFortifyActionAt(Position performActionAt) {
        if (!game.unitIsNotNull(performActionAt)) {
            return false;
        }

        if (game.playerInTurnIsNotOwnerOfUnit(performActionAt)) {
            return false;
        }

        if (game.getUnitAt(performActionAt).getIsActionUsed()) {
            game.getUnitAt(performActionAt).setDefensiveStrength(game.getUnitAt(performActionAt).getDefensiveStrength() / 2);
            game.getUnitAt(performActionAt).setMoveCount(1);
            game.getUnitAt(performActionAt).changeIfActionUsed(false);
        } else {
            game.getUnitAt(performActionAt).setDefensiveStrength(game.getUnitAt(performActionAt).getDefensiveStrength() * 2);
            game.getUnitAt(performActionAt).setMoveCount(0);
            game.getUnitAt(performActionAt).changeIfActionUsed(true);
        }
        return true;
    }


    @Override
    public void performAction(Position p) {
        if (game.getUnitAt(p) != null) {
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
}
