package hotciv.standard;

import hotciv.framework.Ageing;

public class AgeingAlphaCiv implements Ageing {

    @Override
    public int calculateAge(int getAge) {
        return 100;
    }
}
