package hotciv.standard.broker;

import frds.broker.*;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.Game;
import hotciv.framework.GameObserver;
import hotciv.framework.Position;
import hotciv.standard.NullObserver;
import hotciv.stub.StubGame3;
import hotciv.visual.CompositionTool;
import hotciv.visual.HotCivFactory4;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

import java.io.IOException;

public class HotCivManuelClientTest {

    private String operation;
    private String name;
    private String objectId;
    private String hostname;

    public static void main(String[] args) throws Exception {
        new HotCivManuelClientTest(args);
    }

    public HotCivManuelClientTest(String args[]) throws Exception {
        // parseCommandlineParameters(args);

        System.out.println("=== HotCiv MANUEL TEST Client  (Socket)  (host:" + hostname + ") ===");

        // Setup broker part



       /* ClientRequestHandler crh = new SocketClientRequestHandler();
=======
        ClientRequestHandler crh = new SocketClientRequestHandler();
>>>>>>> 742175f1455f780051f62b3d9a0fc908fdb51bfb
        crh.setServer(hostname, 3784);

        Requestor requestor = new StandardJSONRequestor(crh); */
        ClientRequestHandler clientRequestHandler
                = new SocketClientRequestHandler(hostname, 3784);
        Requestor requestor = new StandardJSONRequestor(clientRequestHandler);

        Game game = new GameProxy(requestor);

            DrawingEditor editor =
                    new MiniDrawApplication("The SemiCiv game",
                            new HotCivFactory4(game));
            editor.open();
            editor.setTool(new CompositionTool(editor, game));
            editor.showStatus("Play semiCiv");


        }

    private void parseCommandlineParameters(String[] args) {
        if (args.length < 4) {
            explainAndFail();
        }
        operation = args[0];
        // name = args[1];
        // objectId = args[2];
        // hostname = args[3];
    }

    private static void explainAndFail() {
        System.out.println("Usage: LobbyClient <operation> <name> <objectId> <host>");
        System.out.println("  operation is either 'create' or 'join' or 'move'");
        System.out.println("  objectId is only used in join or move");
        System.out.println("    for join, it is the joinToken");
        System.out.println("    for move, it is the game's objectId");

    }


    /*  private void testSimpleMethods(Game game) {

        System.out.println("=== Testing simple methods ====");
        System.out.println(" -> Game age      " + game.getAge());
        System.out.println(" -> Game winner   " + game.getWinner());
        System.out.println(" -> Game PIT      " + game.getPlayerInTurn());
        // System.out.println(" -> Game move     " + game.moveUnit(new Position(2, 0), new Position(3,3)));

        game.endOfTurn();
        System.out.println(" -> Game PIT after endOfTurn    " + game.getPlayerInTurn());
    }
    */

}
