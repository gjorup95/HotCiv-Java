package hotciv.standard;

import hotciv.framework.Ageing;
import hotciv.framework.Game;

/**
 * @ author Troels Gjørup
 * 14-09-2018
 */
public class AgeingBetaCiv implements Ageing {
    private final Game game;

    public AgeingBetaCiv(Game game) {
        this.game = game;
    }

    @Override
    public int calculateAge() {
        if (game.getAge() < -100) {
            return 100;
        }
        if (game.getAge() == -100) {
            return 99;
        }
        if (game.getAge() == -1) {
            return 2;
        }
        if (game.getAge() == 1) {
            return 49;
        }
        if (game.getAge() >= 50 && game.getAge() < 1750) {
            return 50;
        }
        if (game.getAge() >= 1750 && game.getAge() < 1900) {
            return 25;
        }
        if (game.getAge() >= 1900 && game.getAge() < 1970) {
            return 5;
        }
        if (game.getAge() >= 1970) {
            return 1;
        }
        return 0;
    }
}
