package hotciv.standard;

import hotciv.framework.*;

public class ThirdPartyAdapterGameFactory implements GameFactory {
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
        return new ThirdPartyGeneratorAdapter(game);
    }

    @Override
    public AttackingStrat createAttackingStrat(GameImpl game) {
        return new AttackingStratAlphaCiv();
    }

    @Override
    public CityHandlingStrategy createCityHandlingStrategy(GameImpl game) {
        return new CityHandlingStrategyAlphaCiv();
    }
}