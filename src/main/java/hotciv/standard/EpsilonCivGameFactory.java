package hotciv.standard;

import hotciv.framework.*;

public class EpsilonCivGameFactory implements GameFactory {
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
        return new WinningConditionEpsilonCiv(game);
    }

    @Override
    public WorldCreator createWorldCreator(GameImpl game) {
        return new WorldCreatorAlphaCiv(game);
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
