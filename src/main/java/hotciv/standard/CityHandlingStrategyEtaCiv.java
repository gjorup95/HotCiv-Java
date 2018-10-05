package hotciv.standard;

import hotciv.framework.CityHandlingStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Utility;

import java.util.HashMap;

/**
 * @ author Troels Gjørup
 * 03-10-2018
 */
public class CityHandlingStrategyEtaCiv implements CityHandlingStrategy {
    private GameImpl game;

    public CityHandlingStrategyEtaCiv(GameImpl game) {
        this.game = game;
    }

    @Override
    public int calculateWorkforceProduction(Position city) {
        HashMap<Position, Boolean> citizenMap = new HashMap<>();
        int cityPopulation = game.getCityAt(city).getSize();
        int production = 0;
        int placedUnits = 0;
        // we always put one citizen on the city tile, seeing as the city always has >= 1 population.
        production += 1;
        citizenMap.put(city, true);
        placedUnits += 1;

        while (cityPopulation > placedUnits) {
            for (Position p : Utility.get8neighborhoodOf(city)) {
                Position mostValuableTile = null;
                if (game.getTileAt(p).getTypeString() == GameConstants.MOUNTAINS && citizenMap.get(p) == null) {
                    mostValuableTile = p;
                    citizenMap.put(mostValuableTile, true);
                }

               /* if (game.getTileAt(p).getTypeString() == GameConstants.MOUNTAINS && citizenMap.get(p)== null){
                    citizenMap.put(p,true);
                    production +=3;
                    placedUnits +=1;
                    break;}
                    if (game.getTileAt(p).getTypeString() == GameConstants.FOREST && citizenMap.get(p)== null){
                        citizenMap.put(p,true);
                        production +=2;
                        placedUnits +=1;
                        break;
                    }
                    if (citizenMap.get(p)== null) {
                        citizenMap.put(p, true);
                        production += 1;
                        placedUnits +=1;
                        break;
                    }
                }
            */
            }
        }
        return production;
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
