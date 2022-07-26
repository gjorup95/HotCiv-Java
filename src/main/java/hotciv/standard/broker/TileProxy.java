package hotciv.standard.broker;

import frds.broker.ClientProxy;
import frds.broker.IPCException;
import frds.broker.Requestor;
import hotciv.framework.Tile;

import javax.servlet.http.HttpServletResponse;

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
        try {
            tileType = requestor.sendRequestAndAwaitReply(objectId, MarshallingConstants.TILE_GET_TYPE_STRING,
                    String.class, "no-arguments");
        } catch (IPCException exc) {
            if (exc.getStatusCode() != HttpServletResponse.SC_NOT_FOUND) {
                throw exc;
            }
        }
        return tileType;
    }


    @Override
    public String getId() {
        return objectId;
    }
}
