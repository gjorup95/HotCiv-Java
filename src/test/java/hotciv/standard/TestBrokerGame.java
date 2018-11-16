package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.*;

import hotciv.standard.broker.HotCivGameInvoker;
import hotciv.standard.broker.LocalMethodClientRequestHandler;
import hotciv.standard.broker.GameProxy;
import hotciv.stub.StubGame3;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import frds.broker.Invoker;

public class TestBrokerGame {

    private Game game;

    @Before
    public void setup() {

        Game servant = new StubGame3();
        GameObserver nullObserver = new NullObserver();
        servant.addObserver(nullObserver);

        Invoker invoker = new HotCivGameInvoker(servant);

        ClientRequestHandler crh = new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        game = new GameProxy(requestor);
        game.addObserver(nullObserver);
    }

    @Test
    public void shouldHaveYellowPlayerWinTheGame() {
        Player winner = game.getWinner();
        assertThat(winner, is(Player.YELLOW));
    }

    @Test
    public void shouldHaveAge42() {
        int age = game.getAge();
        assertThat(age, is(42));
    }

    @Test
    public void shouldHaveBluePlayerInTurn() {
        Player inTurn = game.getPlayerInTurn();
        assertThat(inTurn, is(Player.BLUE));
    }

    @Test
    public void shouldMoveUnit() {
        game.moveUnit(new Position(5,5), new Position(5,6));
    }

    @Test
    public void shouldChangeProductionInCity() {
        game.changeProductionInCityAt(new Position(10,10), GameConstants.ARCHER);
    }

    @Test
    public void shouldCouldEndOfTurn() {
        game.endOfTurn();
    }

    @Test
    public void shouldPerformUnitActionAt() {
        game.performUnitActionAt(new Position(12,12));
    }

}
