package hotciv.standard;

import hotciv.framework.*;

/**
 * @ author Troels Gjørup
 * 05-10-2018
 */
public class UnitActionsThetaCiv implements UnitActions {
    private GameImpl game;
    private UnitActionsGammaCiv unitActionsGammaCiv;

    public UnitActionsThetaCiv(GameImpl game, UnitActionsGammaCiv unitActionsGammaCiv ){
        this.game= game;
        this.unitActionsGammaCiv = unitActionsGammaCiv;
    }
    @Override
    public boolean performSettlerActionAt(Position performPosition) {
        return false;
    }

    @Override
    public boolean performArcherFortifyActionAt(Position performPosition) {
        return false;
    }

    @Override
    public boolean performBombActionAt(Position performPosition) {
        if (game.playerInTurnIsNotOwnerOfUnit(performPosition)) {
            return false;
        }
        for (Position p: Utility.get8neighborhoodOf(performPosition)){
            if(game.getUnitAt(p)!= null){
                game.removeUnit(p);
            }
            if (game.getCityAt(p)!= null && game.getCityAt(p).getSize()==1){
                game.removeCity(p);
            }
            if (game.getCityAt(p)!= null && game.getCityAt(p).getSize()>1){
                game.getCityAt(p).setPopulation(game.getCityAt(p).getSize() -1);
            }
        }
        game.removeUnit(performPosition);
        return true;
    }

    @Override
    public void performAction(Position performActionAt) {
        // todo: IS THERE A BETTER WAY TO Implement this reducing code duplicity?
        if (game.getUnitAt(performActionAt) != null) {
            switch (game.getUnitAt(performActionAt).getTypeString()) {
                case GameConstants.BOMB:
                    performBombActionAt(performActionAt);
                    break;
                default:
                    unitActionsGammaCiv.performAction(performActionAt);
                    break;
            }
        }
    }
}


