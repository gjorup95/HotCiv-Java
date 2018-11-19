package hotciv.standard.broker;

import frds.broker.*;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.Game;
import hotciv.framework.GameObserver;
import hotciv.framework.Position;
import hotciv.standard.NullObserver;
import hotciv.stub.StubGame3;

import java.io.IOException;

public class HotCivManuelClientTest {

    public static void main(String[] args) throws Exception {
        new HotCivManuelClientTest(args[0]);
    }

    public HotCivManuelClientTest(String hostname) {
        System.out.println("=== HotCiv MANUEL TEST Client  (Socket)  (host:" + hostname + ") ===");

        // Setup broker part

        ClientRequestHandler crh = new SocketClientRequestHandler();
        crh.setServer(hostname, 3784);

        Requestor requestor = new StandardJSONRequestor(crh);

        Game game = new GameProxy(requestor);
        testSimpleMethods(game);
    }

    private void testSimpleMethods(Game game) {

        System.out.println("=== Testing simple methods ====");
        System.out.println(" -> Game age      " + game.getAge());
        System.out.println(" -> Game winner   " + game.getWinner());
        System.out.println(" -> Game PIT      " + game.getPlayerInTurn());
        System.out.println(" -> Game move     " + game.moveUnit(new Position(2, 0), new Position(3,3)));

        game.endOfTurn();
        System.out.println(" -> Game PIT after endOfTurn    " + game.getPlayerInTurn());
    }

}
