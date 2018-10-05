package hotciv.standard;

import hotciv.framework.CityHandlingStrategy;
import hotciv.framework.Position;

/**
 * @ author Troels Gjørup
 * 03-10-2018
 */
public class CityHandlingStrategyAlphaCiv implements CityHandlingStrategy {
    @Override
    public int calculateWorkforceProduction(Position city) {
        return 0;
    }

    @Override
    public int calculateWorkforceFood(Position city) {
        return 0;
    }

    @Override
    public int calculateCityGrowth(Position city) {
        return 0;
    }
}
