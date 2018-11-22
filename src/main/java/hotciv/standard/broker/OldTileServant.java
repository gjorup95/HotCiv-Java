package hotciv.standard.broker;

import frds.broker.Servant;
import hotciv.framework.Player;
import hotciv.framework.Tile;

import java.util.UUID;

public class OldTileServant implements Tile, Servant {
    private final String tileType;
    private final String objectId;

    public OldTileServant(String tileType) {
        this.tileType = tileType;
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
