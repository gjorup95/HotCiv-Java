package hotciv.standard.broker;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.City;
import hotciv.framework.Player;

public class CityProxy implements City, ClientProxy {

    private final Requestor requestor;
    private final String objectId;

    public CityProxy(String objectId, Requestor crh) {
        this.requestor = crh;
        this.objectId = objectId;
    }

    @Override
    public Player getOwner() {
        Player p = null;
        p = requestor.sendRequestAndAwaitReply(objectId, MarshallingConstants.CITY_GET_OWNER, Player.class, "no-arguments");
        return p;
    }

    @Override
    public int getSize() {
        int size = 0;
        size = requestor.sendRequestAndAwaitReply(objectId, MarshallingConstants.CITY_GET_SIZE, int.class, "no-arguments");
        return size;
    }

    @Override
    public int getTreasury() {
        int treasury = 0;
        treasury = requestor.sendRequestAndAwaitReply(objectId, MarshallingConstants.CITY_GET_TREASURY, int.class, "no-arguments");
        return treasury;
    }

    @Override
    public String getProduction() {
        String production = null;
        production = requestor.sendRequestAndAwaitReply(objectId, MarshallingConstants.CITY_GET_PRODUCTION, String.class, "no-arguments");
        return production;
    }

    @Override
    public String getWorkforceFocus() {
        String workforceFocus = null;
        workforceFocus = requestor.sendRequestAndAwaitReply(objectId, MarshallingConstants.CITY_GET_PRODUCTION, String.class, "no-arguments");
        return workforceFocus;
    }

    @Override
    public String getId() {
        return objectId;
    }
}
