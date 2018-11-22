package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Tile;

import java.util.UUID;

/**
 * @ author Troels Gjørup
 * 30-08-2018
 */
public class TileImpl implements Tile {
    private final String tileType;
    private final String objectId;

    public TileImpl(String s) {
        this.tileType = s;
        objectId = UUID.randomUUID().toString();
    }

    @Override
    public String getTypeString() {
        return tileType;
    }

    @Override
    public String getId() {
        return objectId;
    }
}
