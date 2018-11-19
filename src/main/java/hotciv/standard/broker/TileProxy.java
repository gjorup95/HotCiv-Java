package hotciv.standard.broker;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.Tile;

public class TileProxy implements Tile, ClientProxy {

    private final Requestor requestor;

    public TileProxy(Requestor crh) {
        this.requestor = crh;
    }

    @Override
    public String getTypeString() {
        String tileType = null;
        tileType = requestor.sendRequestAndAwaitReply("not_used",
                MarshallingConstants.TILE_GET_TYPE_STRING,
                String.class, "no-arguments");
        return tileType;
    }
}
