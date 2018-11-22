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

import java.util.UUID;

import static org.junit.Assert.assertThat;

public class TestBrokerCity {

    private Game game;
    private City city;

    @Before
    public void setup() {

        Game servant = new StubGame3();
        GameObserver nullObserver = new NullObserver();
        servant.addObserver(nullObserver);

        Invoker invoker = new RootInvoker(servant);

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        game = new GameProxy(requestor);
        game.addObserver(nullObserver);

    }

    @Test
    public void shouldReturnGreenOwnerOfCity() {
        // Teststub 3 has a green city on position (1,1)
        Player owner = game.getCityAt(new Position(1,1)).getOwner();
        assertThat(owner, is(Player.GREEN));
    }

    @Test
    public void shouldReturnSizeOfCity() {
        int size = game.getCityAt(new Position(1,1)).getSize();
        assertThat(size, is(1));
    }

    @Test
    public void shouldReturnTreasuryOfCity() {
        int treasury = game.getCityAt(new Position(1,1)).getTreasury();
        assertThat(treasury, is(0));
    }

    @Test
    public void shouldReturnProductionFocus() {
        String production = game.getCityAt(new Position(1,1)).getProduction();
        assertThat(production, is(GameConstants.ARCHER));
    }
}
