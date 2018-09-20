package hotciv.standard;

import hotciv.framework.Ageing;
import hotciv.framework.Game;

/**
 * @ author Troels Gjørup
 * 14-09-2018
 */
public class AgeingBetaCiv implements Ageing {


    public AgeingBetaCiv() {

    }

    @Override
    public int calculateAge(int getAge) {
        if (getAge < -100) {
            return 100;
        }
        if (getAge == -100) {
            return 99;
        }
        if (getAge == -1) {
            return 2;
        }
        if (getAge == 1) {
            return 49;
        }
        if (getAge >= 50 && getAge < 1750) {
            return 50;
        }
        if (getAge >= 1750 && getAge < 1900) {
            return 25;
        }
        if (getAge >= 1900 && getAge < 1970) {
            return 5;
        }
        if (getAge >= 1970) {
            return 1;
        }
        return 0;
    }

}
