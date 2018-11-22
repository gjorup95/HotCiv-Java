package hotciv.standard.broker;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.Tile;

public class TileProxy implements Tile, ClientProxy {

    private final Requestor requestor;
    private final String objectId;

    public TileProxy(String objectId, Requestor crh) {
        this.requestor = crh;
        this.objectId = objectId;
    }

    @Override
    public String getTypeString() {
        String tileType = null;
        tileType = requestor.sendRequestAndAwaitReply(objectId, MarshallingConstants.TILE_GET_TYPE_STRING,
                String.class, "no-arguments");

        return tileType;
    }
}
