package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.*;
import hotciv.standard.broker.*;
import hotciv.stub.StubGame3;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import org.junit.*;
import static org.hamcrest.CoreMatchers.*;

public class TestBrokerTile {

    private Game game;

    @Before
    public void setUp() {
        Game servant = new StubGame3();
        GameObserver nullObserver = new NullObserver();
        servant.addObserver(nullObserver);

        Invoker invoker = new RootInvoker(servant);

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        game = new GameProxy(requestor);
    }

    @Test
    public void shouldReturnTileType() {
        // There is ocean tile at (8,8) in StubGame3.
        String tileType = game.getTileAt(new Position(8,8)).getTypeString();
        assertThat(tileType, is(GameConstants.OCEANS));
    }
}
