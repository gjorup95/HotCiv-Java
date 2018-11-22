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


public class TestBrokerUnit {

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
    public void shouldReturnTypeString() {
        // Teststub 3 green_archer_position = new Position(5,5)
        String unitType = game.getUnitAt(new Position(5,5)).getTypeString();
        assertThat(unitType, is(GameConstants.ARCHER));
    }

    @Test
    public void shouldReturnOwner() {
        Player owner = game.getUnitAt(new Position(5,5)).getOwner();
        assertThat(owner, is(Player.GREEN));
    }

    @Test
    public void shouldReturnMoveCount() {
        int moveCount = game.getUnitAt(new Position(5,5)).getMoveCount();
        assertThat(moveCount, is(1));
    }

    @Test
    public void shouldReturnDefensiveStrength() {
        int defStrength = game.getUnitAt(new Position(5,5)).getDefensiveStrength();
        assertThat(defStrength, is(1));
    }

    @Test
    public void shouldReturnAttackStrength() {
        int attackStregth = game.getUnitAt(new Position(5,5)).getAttackingStrength();
        assertThat(attackStregth, is(1));
    }
}
