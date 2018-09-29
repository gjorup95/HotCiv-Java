package hotciv.standard;

import hotciv.framework.*;

public class ZetaCivFactory implements Factory {
    @Override
    public Ageing createAgeingStrategy() {
        return new AgeingAlphaCiv();
    }

    @Override
    public UnitActions createUnitActionsStrategy(Game game) {
        return new UnitActionsAlphaCiv();
    }

    @Override
    public WinningCondition createWinningCondition(Game game) {
        return new WinningConditionAlternating(new WinningConditionBetaCiv((GameImpl) game), new WinningConditionEpsilonCiv((GameImpl) game), (GameImpl) game);
    }

    @Override
    public WorldCreator createWorldCreator(Game game) {
        return new WorldCreatorAlphaCiv((GameImpl) game);
    }
}
