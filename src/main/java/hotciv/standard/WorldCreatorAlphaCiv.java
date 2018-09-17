package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

public class WorldCreatorAlphaCiv implements WorldCreator {
    Map<Position, TileImpl> tempWorldMap = new HashMap<>();
    Map<Position, CityImpl> tempCityMap = new HashMap<>();
    Map<Position, UnitImpl> tempUnitMap = new HashMap<>();

    public WorldCreatorAlphaCiv() {
        for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
            for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
                tempWorldMap.put(new Position(i, j), new TileImpl(GameConstants.PLAINS));
            }
        }

        // Places the cities in the worldMap
        tempCityMap.put(GameConstants.RED_CITY_POSITION, new CityImpl(Player.RED));
        tempCityMap.put(GameConstants.BLUE_CITY_POSITION, new CityImpl(Player.BLUE));
        // Places the unique spots for oceans, hills and mountains and overrides the positions in the worldMap.
        tempWorldMap.put(GameConstants.OCEAN_POSITION, new TileImpl(GameConstants.OCEANS));
        tempWorldMap.put(GameConstants.HILLS_POSITION, new TileImpl(GameConstants.HILLS));
        tempWorldMap.put(GameConstants.MOUNTAINS_POSITION, new TileImpl(GameConstants.MOUNTAINS));
        // places the initial units on the unitMap
        tempUnitMap.put(GameConstants.ARCHER_POSITION_RED, new UnitImpl(GameConstants.ARCHER, Player.RED));
        tempUnitMap.put(GameConstants.LEGION_POSITION_BLUE, new UnitImpl(GameConstants.LEGION, Player.BLUE));
        tempUnitMap.put(GameConstants.SETTLER_POSITION_RED, new UnitImpl(GameConstants.SETTLER, Player.RED));
    }


    @Override
    public Map<Position, TileImpl> getWorldMap() {
        return tempWorldMap;
    }

    @Override
    public Map<Position, CityImpl> getCityMap() {
        return tempCityMap;
    }

    @Override
    public Map<Position, UnitImpl> getUnitMap() {
        return tempUnitMap;
    }
}
