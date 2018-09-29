package hotciv.standard;

import hotciv.framework.*;

public class DeltaCivFactory implements Factory {
    @Override
    public Ageing createAgeingStrategy() {
        return new AgeingAlphaCiv();
    }

    @Override
    public UnitActions createUnitActionsStrategy(GameImpl game) {
        return new UnitActionsAlphaCiv();
    }

    @Override
    public WinningCondition createWinningCondition(GameImpl game) {
        return new WinningConditionAlphaCiv(game);
    }

    @Override
    public WorldCreator createWorldCreator(GameImpl game) {
        return new WorldCreatorDeltaCiv(game);
    }

    @Override
    public AttackingStrat createAttackingStrat(GameImpl game) {
        return new AttackingStratAlphaCiv();
    }
}
