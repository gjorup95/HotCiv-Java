package hotciv.framework;

import hotciv.standard.AttackCalculationStrat;
import hotciv.standard.GameImpl;
import hotciv.standard.RandomAttackCalculationStrat;

public interface GameFactory {

    public Ageing createAgeingStrategy();

    public UnitActions createUnitActionsStrategy(GameImpl game);

    public WinningCondition createWinningCondition(GameImpl game);

    public WorldCreator createWorldCreator(GameImpl game);

    public AttackingStrat createAttackingStrat(GameImpl game);

    public CityHandlingStrategy createCityHandlingStrategy(GameImpl game);
}
