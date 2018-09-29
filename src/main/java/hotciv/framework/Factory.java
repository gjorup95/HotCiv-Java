package hotciv.framework;

import hotciv.standard.GameImpl;

public interface Factory {

    public Ageing createAgeingStrategy();

    public UnitActions createUnitActionsStrategy(Game game);

    public WinningCondition createWinningCondition(Game game);

    public WorldCreator createWorldCreator(Game game);

    public AttackingStrat createAttackingStrat(GameImpl game);
}
