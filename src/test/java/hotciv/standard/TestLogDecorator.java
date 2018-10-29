package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


/**
 * @ author Troels Gjørup
 * 24-10-2018
 */
public class TestLogDecorator {
    private GameImpl game;
    private LogDecorator logDecorator;

    @Before
    public void setUp(){
        game = new GameImpl(new AlphaCivFactory());
        logDecorator = new LogDecorator(game);}

        @Test
        public void shouldPostAllActionsToLog(){

        logDecorator.moveUnit(GameConstants.ARCHER_POSITION_RED, new Position(3,0));
        logDecorator.endOfTurn();
        logDecorator.changeWorkForceFocusInCityAt(GameConstants.RED_CITY_POSITION, "food");
        logDecorator.changeProductionInCityAt(GameConstants.RED_CITY_POSITION, GameConstants.ARCHER);
        logDecorator.performUnitActionAt(GameConstants.ARCHER_POSITION_RED);


        }

}