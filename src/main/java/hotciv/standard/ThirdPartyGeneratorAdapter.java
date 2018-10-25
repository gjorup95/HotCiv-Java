package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.WorldCreator;
import thirdparty.ThirdPartyFractalGenerator;

import java.util.ArrayList;

public class ThirdPartyGeneratorAdapter implements WorldCreator {
    private ThirdPartyFractalGenerator generator;
    private GameImpl game;

    public ThirdPartyGeneratorAdapter(GameImpl game) {
        this.game = game;
        ThirdPartyFractalGenerator generator = new ThirdPartyFractalGenerator();
        char tileChar;
        String type = null;

        for (int r = 0; r < 16; r++) {
            for (int c = 0; c < 16; c++) {
                char line = generator.getLandscapeAt(r, c);
                if (line == '.') {
                    type = GameConstants.OCEANS;
                }
                if (line == 'o') {
                    type = GameConstants.PLAINS;
                }
                if (line == 'M') {
                    type = GameConstants.MOUNTAINS;
                }
                if (line == 'f') {
                    type = GameConstants.FOREST;
                }
                if (line == 'h') {
                    type = GameConstants.HILLS;
                }
                if(type != null) {
                    Position p = new Position(r, c);
                    game.addTile(p, type);
                }
            }
        }
    }
}


