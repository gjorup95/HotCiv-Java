package hotciv.standard;

import hotciv.framework.*;

public class ZetaCivGameFactory implements GameFactory {
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
        return new WinningConditionAlternating(new WinningConditionBetaCiv(game), new WinningConditionEpsilonCiv(game), game);
    }

    @Override
    public WorldCreator createWorldCreator(GameImpl game) {
        return new WorldCreatorAlphaCiv(game);
    }

    @Override
    public AttackingStrat createAttackingStrat(GameImpl game) {
        return new AttackingStratAlternating(new AttackingStratAlphaCiv(), new AttackingStratEpsilonCiv(game, new RandomAttackCalculationStrat()), game);
    }

    @Override
    public CityHandlingStrategy createCityHandlingStrategy(GameImpl game) {
        return new CityHandlingStrategyAlphaCiv();
    }
}
