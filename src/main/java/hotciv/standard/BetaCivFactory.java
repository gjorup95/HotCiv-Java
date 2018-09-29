package hotciv.standard;

import hotciv.framework.*;

public class BetaCivFactory implements Factory {
    @Override
    public Ageing createAgeingStrategy() {
        return new AgeingBetaCiv();
    }

    @Override
    public UnitActions createUnitActionsStrategy(Game game) {
        return new UnitActionsAlphaCiv();
    }

    @Override
    public WinningCondition createWinningCondition(Game game) {
        return new WinningConditionBetaCiv((GameImpl) game);
    }

    @Override
    public WorldCreator createWorldCreator(Game game) {
        return new WorldCreatorAlphaCiv((GameImpl) game);
    }
}
