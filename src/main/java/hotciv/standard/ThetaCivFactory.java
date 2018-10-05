package hotciv.standard;

import hotciv.framework.*;

/**
 * @ author Troels Gjørup
 * 05-10-2018
 */
public class ThetaCivFactory implements Factory {
    @Override
    public Ageing createAgeingStrategy() {
        return new AgeingAlphaCiv();
    }

    @Override
    public UnitActions createUnitActionsStrategy(GameImpl game) {
        return new UnitActionsThetaCiv(game, new  UnitActionsGammaCiv(game));
    }

    @Override
    public WinningCondition createWinningCondition(GameImpl game) {
        return new WinningConditionAlphaCiv(game);
    }

    @Override
    public WorldCreator createWorldCreator(GameImpl game) {
        return new WorldCreatorAlphaCiv(game);
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
