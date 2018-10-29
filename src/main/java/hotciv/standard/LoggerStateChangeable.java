package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.LoggerState;

/**
 * @ author Troels Gjørup
 * 25-10-2018
 */
public class LoggerStateChangeable implements LoggerState {
    private Game decoratee;
    private Game game;
    public LoggerStateChangeable(Game game){
        this.game = game;
        this.decoratee = game;
    }

    @Override
    public void setStateOfLogger() {
        if(game == decoratee){
            decoratee = game;
            game =  new LogDecorator(game);
        }
        else {
            game = decoratee;
        }

    }

}
