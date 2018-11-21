package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.GameObserver;
import hotciv.framework.Tile;
import hotciv.standard.broker.HotCivTileInvoker;
import hotciv.standard.broker.HotCivUnitInvoker;
import hotciv.standard.broker.LocalMethodClientRequestHandler;
import hotciv.standard.broker.TileProxy;
import hotciv.stub.StubGame3;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import org.junit.*;
import static org.hamcrest.CoreMatchers.*;

public class TestBrokerTile {

    private Tile tile;

    @Before
    public void setUp() {
        Game servant = new StubGame3();
        GameObserver nullObserver = new NullObserver();
        servant.addObserver(nullObserver);

        Invoker invoker = new HotCivTileInvoker();

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        tile = new TileProxy("", requestor);
    }

    @Test
    public void shouldReturnTileType() {
        String tileType = tile.getTypeString();
        assertThat(tileType, is(GameConstants.OCEANS));
    }
}
