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
    private GameImpl game;

    public UnitActionsGammaCiv(GameImpl game) {
        this.game = game;
    }

    @Override
    public void performSettlerActionAt(Position performPosition) {
        if (game.getUnitAt(performPosition) != null && game.getUnitAt(performPosition).getTypeString().equals(GameConstants.SETTLER) && game.getCityAt(performPosition) == null && game.getPlayerInTurn().equals(game.getUnitAt(performPosition).getOwner())) {
            game.addCity(performPosition, game.getPlayerInTurn());
            game.removeUnit(performPosition);
        }
    }

    @Override
    public void performArcherFortifyActionAt(Position performPosition) {
        if (game.getUnitAt(performPosition) != null && game.getUnitAt(performPosition).getTypeString().equals(GameConstants.ARCHER) && game.getUnitAt(performPosition).getOwner().equals(game.getPlayerInTurn())) {
            if(game.getUnitAt(performPosition).getIsActionUsed()) {
                game.getUnitAt(performPosition).setDefensiveStrength(game.getUnitAt(performPosition).getDefensiveStrength()/2);
                game.getUnitAt(performPosition).setMoveCount(1);
                game.getUnitAt(performPosition).changeIfActionUsed(false);
            }
            else {
                game.getUnitAt(performPosition).setDefensiveStrength(game.getUnitAt(performPosition).getDefensiveStrength()*2);
                game.getUnitAt(performPosition).setMoveCount(0);
                game.getUnitAt(performPosition).changeIfActionUsed(true);
            }
        }
    }
}
