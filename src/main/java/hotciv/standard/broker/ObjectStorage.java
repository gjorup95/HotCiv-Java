package hotciv.standard.broker;

import hotciv.framework.City;
import hotciv.framework.Tile;
import hotciv.framework.Unit;

public interface ObjectStorage {

    public void putCity(String objectId, City city);

    public City getCity(String objectId);

    public void putTile(String objectId, Tile tile);

    public Tile getTile(String objectId);

    public void putUnit(String objectId, Unit unit);

    public Unit getUnit(String objectId);
}
