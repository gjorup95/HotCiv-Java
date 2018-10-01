package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Utility;
import hotciv.framework.AttackingStrat;
import hotciv.framework.Position;

import java.util.Random;

/**
 * @ author Troels Gjørup
 * 28-09-2018
 */
public class AttackingStratEpsilonCiv implements AttackingStrat {
    private GameImpl game;
    private AttackCalculationStrat attackCalculationStrat;

    public AttackingStratEpsilonCiv(GameImpl game, AttackCalculationStrat strat) {
        this.game = game;
        this.attackCalculationStrat = strat;
    }

    @Override
    public int calculateAttackerStr(Position from) {
        if (game.getUnitAt(from) == null) {
            return 0;
        }
        int attStrUnits = game.getUnitAt(from).getAttackingStrength();
        int attTerrainMultiplyStr = 1;
        if (game.getTileAt(from).getTypeString().equals(GameConstants.HILLS) || game.getTileAt(from).getTypeString().equals(GameConstants.FOREST)) {
            attTerrainMultiplyStr = 2;
        }
        if (game.getCityAt(from) != null) {
            attTerrainMultiplyStr = 3;
        }
        for (Position p : Utility.get8neighborhoodOf(from)) {
            if (game.getUnitAt(p) != null && game.getUnitAt(p).getOwner().equals(game.getUnitAt(from).getOwner())) {
                attStrUnits += 1;
            }
        }
        return attStrUnits * attTerrainMultiplyStr;
    }

    @Override
    public int calculateDefensiveStr(Position to) {
        if (game.getUnitAt(to) == null) {
            return 0;
        }
        int defStrUnits = game.getUnitAt(to).getDefensiveStrength();
        int defTerrainMultiplyStr = 1;
        if (game.getTileAt(to).getTypeString().equals(GameConstants.HILLS) || game.getTileAt(to).getTypeString().equals(GameConstants.FOREST)) {
            defTerrainMultiplyStr = 2;
        }
        if (game.getCityAt(to) != null) {
            defTerrainMultiplyStr = 3;

        }
        for (Position p : Utility.get8neighborhoodOf(to)) {
            if (game.getUnitAt(p) != null && game.getUnitAt(p).getOwner().equals(game.getUnitAt(to).getOwner())) {
                defStrUnits += 1;
            }
        }
        return defStrUnits * defTerrainMultiplyStr;
    }

    @Override
    public int attackResult(Position from, Position to) {
        int attackerStr = calculateAttackerStr(from);
        int defenderStr = calculateDefensiveStr(to);
        int attRandom = attackCalculationStrat.getCalculationVariable();
        int defRandom = attackCalculationStrat.getCalculationVariable();
        attackerStr = attackerStr * attRandom;
        defenderStr = defenderStr * defRandom;
        attackerStr = attackerStr - defenderStr;
        return attackerStr;
    }

    @Override
    public boolean attack(Position from, Position to) {
        if (attackResult(from, to) > 0) {
            if (game.getUnitAt(to) != null) {
                game.incrementCurrentPlayersAttackBattlesWon(1);
            }
            return true;
        }
        game.removeUnit(from);
        return false;
    }

}

