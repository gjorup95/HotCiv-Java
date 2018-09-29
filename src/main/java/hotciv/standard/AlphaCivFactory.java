package hotciv.standard;

import hotciv.framework.*;

public class AlphaCivFactory implements Factory {

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
        return new WinningConditionAlphaCiv((GameImpl) game);
    }

    @Override
    public WorldCreator createWorldCreator(Game game) {
        return new WorldCreatorAlphaCiv((GameImpl) game);
    }
}
