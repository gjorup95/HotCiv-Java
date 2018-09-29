package hotciv.standard;

import hotciv.framework.*;

public class GammaCivFactory implements Factory {
    @Override
    public Ageing createAgeingStrategy() {
        return new AgeingAlphaCiv();
    }

    @Override
    public UnitActions createUnitActionsStrategy(Game game) {
        return new UnitActionsGammaCiv((GameImpl) game);
    }

    @Override
    public WinningCondition createWinningCondition(Game game) {
        return new WinningConditionAlphaCiv((GameImpl) game);
    }

    @Override
    public WorldCreator createWorldCreator(Game game) {
        return new WorldCreatorAlphaCiv((GameImpl) game);
    }

    @Override
    public AttackingStrat createAttackingStrat(GameImpl game) {
        return new AttackingStratAlphaCiv();
    }
}
