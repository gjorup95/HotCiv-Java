package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ author Troels Gjørup
 * 15-09-2018
 */
public class WorldCreatorDeltaCiv implements WorldCreator {
    Map<Position, TileImpl> tempWorldMap = new HashMap<>();
    Map<Position, UnitImpl> tempUnitMap = new HashMap<>();
    Map<Position, CityImpl> tempCityMap = new HashMap<>();

    public WorldCreatorDeltaCiv() {
        tempUnitMap.put(new Position(5,5), new UnitImpl(GameConstants.SETTLER, Player.RED));
        tempUnitMap.put(new Position(4,4), new UnitImpl(GameConstants.LEGION, Player.BLUE));
        tempUnitMap.put(new Position(4,8), new UnitImpl(GameConstants.ARCHER, Player.RED));
        tempCityMap.put(new Position(8,12), new CityImpl(Player.RED));
        tempCityMap.put(new Position(4,5), new CityImpl(Player.BLUE));

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
                    tempWorldMap.put(p, new TileImpl(type));
                }
            }

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
