package hotciv.standard;

import hotciv.framework.CityHandlingStrategy;

/**
 * @ author Troels Gjørup
 * 03-10-2018
 */
public class CityHandlingStrategyAlphaCiv implements CityHandlingStrategy {
    @Override
    public int calculateWorkforceProduction() {
        return 0;
    }

    @Override
    public int calculateWorkforceFood() {
        return 0;
    }

    @Override
    public int calculateCityGrowth() {
        return 0;
    }
}
