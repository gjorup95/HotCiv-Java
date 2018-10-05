package hotciv.framework;

/**
 * @ author Troels Gjørup
 * 03-10-2018
 */
public interface CityHandlingStrategy {
public int calculateWorkforceProduction(Position city);
public int calculateWorkforceFood(Position city);
public int calculateCityGrowth(Position city);

}
