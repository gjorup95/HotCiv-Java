package hotciv.standard.broker;

import frds.broker.Servant;
import hotciv.framework.Player;
import hotciv.framework.Tile;

import java.util.UUID;

public class TileServant implements Tile, Servant {
    private final String tileType;
    private final String objectId;

    public TileServant(String tileType) {
        this.tileType = tileType;
        objectId = UUID.randomUUID().toString();
    }

    public String getObjectId() {
        return objectId;
    }

    @Override
    public String getTypeString() {
        return tileType;
    }
}
