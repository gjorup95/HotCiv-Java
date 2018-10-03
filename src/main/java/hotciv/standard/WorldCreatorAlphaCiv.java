package hotciv.standard;

import hotciv.framework.*;


public class WorldCreatorAlphaCiv implements WorldCreator {
    private GameImpl game;

    public WorldCreatorAlphaCiv(GameImpl game) {
        this.game = game;

        for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
            for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
                game.addTile(new Position(i, j), GameConstants.PLAINS);
            }
        }

        // Places the current players in the playerMap
        // Places the cities in the worldMap
        game.addCity(GameConstants.RED_CITY_POSITION, game.getPlayer(GameConstants.RED));
        game.addCity(GameConstants.BLUE_CITY_POSITION, game.getPlayer(GameConstants.BLUE));
        // Places the unique spots for oceans, hills and mountains and overrides the positions in the worldMap.
        game.addTile(GameConstants.OCEAN_POSITION, GameConstants.OCEANS);
        game.addTile(GameConstants.HILLS_POSITION, GameConstants.HILLS);
        game.addTile(GameConstants.MOUNTAINS_POSITION, GameConstants.MOUNTAINS);
        // places the initial units on the unitMap
        game.addUnit(GameConstants.ARCHER_POSITION_RED, GameConstants.ARCHER, game.getPlayer(GameConstants.RED));
        game.addUnit(GameConstants.LEGION_POSITION_BLUE, GameConstants.LEGION, game.getPlayer(GameConstants.BLUE));
        game.addUnit(GameConstants.SETTLER_POSITION_RED, GameConstants.SETTLER, game.getPlayer(GameConstants.RED));
    }
}


