package hotciv.standard;

import hotciv.framework.*;

public class SemiCivFactory implements Factory {
    @Override
    public Ageing createAgeingStrategy() {
        return new AgeingBetaCiv();
    }

    @Override
    public UnitActions createUnitActionsStrategy(GameImpl game) {
        return new UnitActionsGammaCiv(game);
    }

    @Override
    public WinningCondition createWinningCondition(GameImpl game) {
        return new WinningConditionEpsilonCiv(game);
    }

    @Override
    public WorldCreator createWorldCreator(GameImpl game) {
        return new WorldCreatorDeltaCiv(game);
    }

    @Override
    public AttackingStrat createAttackingStrat(GameImpl game) {
        return new AttackingStratEpsilonCiv(game, new RandomAttackCalculationStrat());
    }

    @Override
    public CityHandlingStrategy createCityHandlingStrategy(GameImpl game) {
        return new CityHandlingStrategyAlphaCiv();
    }
}
