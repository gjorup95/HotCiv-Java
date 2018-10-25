package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;

/**
 * @ author Troels Gjørup
 * 25-10-2018
 */
public class TestLoggerState {
    private LoggerStateChangeable loggerStateChangeable;
    private GameImpl game;

    @Before
    public void setUp(){
        game = new GameImpl(new AlphaCivFactory());
        loggerStateChangeable = new LoggerStateChangeable(game);

    }
    @Test
    public void shouldChangeStateProperly(){
       // TODO IS IT POSSIBLE TO TEST THE STATE PATTERN?
        loggerStateChangeable.setStateOfLogger();
        Game tempLog = loggerStateChangeable.returnLogDecorator();
        tempLog.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3,0));
        tempLog.endOfTurn();
        tempLog.changeWorkForceFocusInCityAt(GameConstants.RED_CITY_POSITION, "food");
        tempLog.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        tempLog.performUnitActionAt(GameConstants.ARCHER_POSITION_RED);
        loggerStateChangeable.setStateOfLogger();

    }
}
