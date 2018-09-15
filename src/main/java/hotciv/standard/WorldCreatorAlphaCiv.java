package hotciv.standard;

import hotciv.framework.*;

public class WorldCreatorAlphaCiv implements WorldCreator {
    private Game game;
    public WorldCreatorAlphaCiv(Game game){
        this.game = game;

    }
    @Override
    public void createWorld() {
        for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
            for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
                worldMap.put(new Position(i, j), new TileImpl(GameConstants.PLAINS));
            }
        }

        // Places the cities in the worldMap
        cityMap.put(GameConstants.RED_CITY_POSITION, new CityImpl(Player.RED));
        cityMap.put(GameConstants.BLUE_CITY_POSITION, new CityImpl(Player.BLUE));
        // Places the unique spots for oceans, hills and mountains and overrides the positions in the worldMap.
        worldMap.put(GameConstants.OCEAN_POSITION, new TileImpl(GameConstants.OCEANS));
        worldMap.put(GameConstants.HILLS_POSITION, new TileImpl(GameConstants.HILLS));
        worldMap.put(GameConstants.MOUNTAINS_POSITION, new TileImpl(GameConstants.MOUNTAINS));
        // places the initial units on the unitMap
        unitMap.put(GameConstants.ARCHER_POSITION_RED, new UnitImpl(GameConstants.ARCHER, Player.RED));
        unitMap.put(GameConstants.LEGION_POSITION_BLUE, new UnitImpl(GameConstants.LEGION, Player.BLUE));
        unitMap.put(GameConstants.SETTLER_POSITION_RED, new UnitImpl(GameConstants.SETTLER, Player.RED));
    }
}
