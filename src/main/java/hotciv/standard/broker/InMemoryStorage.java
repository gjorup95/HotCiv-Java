package hotciv.standard.broker;

import hotciv.framework.City;
import hotciv.framework.Tile;
import hotciv.framework.Unit;
import org.eclipse.jetty.websocket.server.MappedWebSocketCreator;

import java.util.HashMap;
import java.util.Map;

/**
 * @ author Troels Gjørup
 * 21-11-2018
 */
public class InMemoryStorage implements ObjectStorage {
    private final Map<String,City> cityMap;
    private final Map<String, Tile> tileMap;
    private final Map<String,Unit> unitMap;
    public InMemoryStorage (){
        this.cityMap = new HashMap<>();
        this.tileMap = new HashMap<>();
        this.unitMap = new HashMap<>();
    }
    @Override
    public void putCity(String objectId, City city) {
        cityMap.put(objectId, city);
    }

    @Override
    public City getCity(String objectId) {
        return cityMap.get(objectId);
    }

    @Override
    public void putTile(String objectId, Tile tile) {
        tileMap.put(objectId, tile);

    }

    @Override
    public Tile getTile(String objectId) {
        return tileMap.get(objectId);
    }

    @Override
    public void putUnit(String objectId, Unit unit) {
    unitMap.put(objectId, unit);
    }

    @Override
    public Unit getUnit(String objectId) {
        return unitMap.get(objectId);
    }
}
