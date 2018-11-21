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

    private Unit unit;

    @Before
    public void setUp() {
        Game servant = new StubGame3();
        GameObserver nullObserver = new NullObserver();
        servant.addObserver(nullObserver);

        Invoker invoker = new RootInvoker(servant);

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        unit = new UnitProxy("", requestor);
    }

    @Test
    public void shouldReturnTypeString() {
        String unitType = unit.getTypeString();
        assertThat(unitType, is(GameConstants.ARCHER));
    }

    @Test
    public void shouldReturnOwner() {
        Player owner = unit.getOwner();
        assertThat(owner, is(Player.GREEN));
    }

    @Test
    public void shouldReturnMoveCount() {
        int moveCount = unit.getMoveCount();
        assertThat(moveCount, is(1));
    }

    @Test
    public void shouldReturnDefensiveStrength() {
        int defStrength = unit.getDefensiveStrength();
        assertThat(defStrength, is(0));
    }

    @Test
    public void shouldReturnAttackStrength() {
        int attackStregth = unit.getAttackingStrength();
        assertThat(attackStregth, is(0));
    }
}
