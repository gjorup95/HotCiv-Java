package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Tile;

/**
 * @ author Troels Gjørup
 * 30-08-2018
 */
public class TileImpl implements Tile {
    private final String tileType;

    public TileImpl(String s) {
        this.tileType = s;

    }

    @Override
    public String getTypeString() {
        return tileType;
    }
}
