package hotciv.standard;

import hotciv.framework.*;


/**
 * @ author Troels Gjørup
 * 15-09-2018
 */
public class WorldCreatorDeltaCiv implements WorldCreator {
    private GameImpl game;


    public WorldCreatorDeltaCiv(GameImpl game) {
        this.game = game;
        game.addUnit(new Position(5, 5), GameConstants.SETTLER, Player.RED);
        game.addUnit(new Position(4, 4), GameConstants.LEGION, Player.BLUE);
        game.addUnit(new Position(4, 8), GameConstants.ARCHER, Player.RED);
        game.addCity(new Position(8, 12), Player.RED);
        game.addCity(new Position(4, 5), Player.BLUE);

        String[] layout =
                new String[]{
                        "...ooMooooo.....",
                        "..ohhoooofffoo..",
                        ".oooooMooo...oo.",
                        ".ooMMMoooo..oooo",
                        "...ofooohhoooo..",
                        ".ofoofooooohhoo.",
                        "...ooo..........",
                        ".ooooo.ooohooM..",
                        ".ooooo.oohooof..",
                        "offfoooo.offoooo",
                        "oooooooo...ooooo",
                        ".ooMMMoooo......",
                        "..ooooooffoooo..",
                        "....ooooooooo...",
                        "..ooohhoo.......",
                        ".....ooooooooo..",
                };
        // Conversion...
        String line;
        for (int r = 0; r < GameConstants.WORLDSIZE; r++) {
            line = layout[r];
            for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
                char tileChar = line.charAt(c);
                String type = "error";
                if (tileChar == '.') {
                    type = GameConstants.OCEANS;
                }
                if (tileChar == 'o') {
                    type = GameConstants.PLAINS;
                }
                if (tileChar == 'M') {
                    type = GameConstants.MOUNTAINS;
                }
                if (tileChar == 'f') {
                    type = GameConstants.FOREST;
                }
                if (tileChar == 'h') {
                    type = GameConstants.HILLS;
                }
                Position p = new Position(r, c);
                game.addTile(p, type);
            }
        }

    }
}
