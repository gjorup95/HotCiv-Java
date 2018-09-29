package hotciv.framework;

public interface Factory {

    public Ageing createAgeingStrategy();

    public UnitActions createUnitActionsStrategy(Game game);

    public WinningCondition createWinningCondition(Game game);

    public WorldCreator createWorldCreator(Game game);
}
