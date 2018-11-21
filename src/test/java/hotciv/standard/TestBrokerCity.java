package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.*;

import hotciv.standard.broker.*;
import hotciv.stub.StubGame3;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import frds.broker.Invoker;

import static org.junit.Assert.assertThat;

public class TestBrokerCity {

    private City city;

    @Before
    public void setup() {

        Game servant = new StubGame3();
        GameObserver nullObserver = new NullObserver();
        servant.addObserver(nullObserver);

        Invoker invoker = new HotCivCityInvoker();

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        city = new CityProxy("", requestor);
    }

    @Test
    public void shouldReturnGreenOwnerOfCity() {
        Player owner = city.getOwner();
        assertThat(owner, is(Player.GREEN));
    }

    @Test
    public void shouldReturnSizeOfCity() {
        int size = city.getSize();
        assertThat(size, is(7));
    }

    @Test
    public void shouldReturnTreasuryOfCity() {
        int treasury = city.getTreasury();
        assertThat(treasury, is(47));
    }

    @Test
    public void shouldReturnProductionFocus() {
        String production = city.getProduction();
        assertThat(production, is(GameConstants.foodFocus));
    }
}
