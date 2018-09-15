package hotciv.framework;

import hotciv.standard.CityImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;
import java.util.Map;

public interface WorldCreator {
    public Map<Position, TileImpl> getWorldMap();

    public Map<Position, CityImpl> getCityMap();

    public Map<Position, UnitImpl> getUnitMap();
}
