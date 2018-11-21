package hotciv.standard.broker;

import hotciv.framework.City;

public interface ObjectStorage {

    public void putCity(String objectId, City city);

    public City getCity(String objectId);
}
