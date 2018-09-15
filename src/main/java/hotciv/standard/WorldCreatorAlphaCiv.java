package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

public class WorldCreatorAlphaCiv implements WorldCreator {
    private Game game;
    Map<Position, TileImpl> tempworldMap = new HashMap<>();
    Map<Position, CityImpl> tempcityMap = new HashMap<>();
    Map<Position, UnitImpl> tempunitMap = new HashMap<>();

    public WorldCreatorAlphaCiv(Game game) {
        this.game = game;
        for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
            for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
                tempworldMap.put(new Position(i, j), new TileImpl(GameConstants.PLAINS));
            }
        }

        // Places the cities in the worldMap
        tempcityMap.put(GameConstants.RED_CITY_POSITION, new CityImpl(Player.RED));
        tempcityMap.put(GameConstants.BLUE_CITY_POSITION, new CityImpl(Player.BLUE));
        // Places the unique spots for oceans, hills and mountains and overrides the positions in the worldMap.
        tempworldMap.put(GameConstants.OCEAN_POSITION, new TileImpl(GameConstants.OCEANS));
        tempworldMap.put(GameConstants.HILLS_POSITION, new TileImpl(GameConstants.HILLS));
        tempworldMap.put(GameConstants.MOUNTAINS_POSITION, new TileImpl(GameConstants.MOUNTAINS));
        // places the initial units on the unitMap
        tempunitMap.put(GameConstants.ARCHER_POSITION_RED, new UnitImpl(GameConstants.ARCHER, Player.RED));
        tempunitMap.put(GameConstants.LEGION_POSITION_BLUE, new UnitImpl(GameConstants.LEGION, Player.BLUE));
        tempunitMap.put(GameConstants.SETTLER_POSITION_RED, new UnitImpl(GameConstants.SETTLER, Player.RED));
    }


    @Override
    public Map<Position, TileImpl> getWorldMap() {
        return tempworldMap;
    }

    @Override
    public Map<Position, CityImpl> getCityMap() {
        return tempcityMap;
    }

    @Override
    public Map<Position, UnitImpl> getUnitMap() {
        return tempunitMap;
    }


}
