package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.*;

import hotciv.standard.broker.HotCivGameInvoker;
import hotciv.standard.broker.LocalMethodClientRequestHandler;
import hotciv.standard.broker.GameProxy;
import hotciv.standard.broker.RootInvoker;
import hotciv.stub.StubGame3;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import frds.broker.Invoker;

public class TestBrokerGame {

    private Game game;
    private Game servant;

    @Before
    public void setup() {

        servant = new StubGame3();
        GameObserver nullObserver = new NullObserver();
        servant.addObserver(nullObserver);

        Invoker invoker = new RootInvoker(servant);

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
        boolean movedUnit = false;

        if(game.moveUnit(new Position(5,5), new Position(5,6))) {
            movedUnit = true;
        }
        assertThat(movedUnit, is(true));
    }

    @Test
    public void shouldChangeProductionInCity() {
        game.changeProductionInCityAt(new Position(10,10), GameConstants.ARCHER);
        StubGame3 localStubGame = (StubGame3) servant;
        assertThat(localStubGame.hasProductionChanged(),is(true ));
    }

    @Test
    public void shouldCouldEndOfTurn() {
        StubGame3 localStubGame = (StubGame3) servant;
        assertThat(localStubGame.hasEndOfTurnBeenCalled(), is(false));
        game.endOfTurn();
        assertThat(localStubGame.hasEndOfTurnBeenCalled(), is(true));
    }

    @Test
    public void shouldPerformUnitActionAt() {
        StubGame3 localStubGame = (StubGame3) servant;
        assertThat(localStubGame.hasPerformUnitActionAtBeenCalled(), is(false));
        game.performUnitActionAt(new Position(12,12));
        assertThat(localStubGame.hasPerformUnitActionAtBeenCalled(), is(true));
    }

    @Test
    public void shouldReturnCities() {
        game.getCityAt(new Position(1,1));
        game.getCityAt(new Position(15,15));
    }

    @Test
    public void shouldReturnTile() {
        game.getTileAt(new Position(8,8));
    }

    @Test
    public void shouldReturnUnit() {
        game.getUnitAt(new Position(5,5));
    }

}
