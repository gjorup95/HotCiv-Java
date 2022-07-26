package hotciv.standard.broker;
import frds.broker.Invoker;
import frds.broker.ipc.socket.SocketServerRequestHandler;
import hotciv.framework.Game;
import hotciv.standard.AlphaCivGameFactory;
import hotciv.standard.GameImpl;
import hotciv.standard.SemiCivGameFactory;
import hotciv.visual.CompositionTool;
import hotciv.visual.HotCivFactory4;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class HotCivServer {

    private static Thread daemon;

    public static void main(String[] args) throws Exception {
        // Command line argument parsing and validation
        if (args.length < 1) {
            explainAndDie();
        }
        new HotCivServer(args[0]); // No error handling!
    }

    private static void explainAndDie() {
        System.out.println("Usage: HotCivServer {db}");
        System.out.println("       db = 'memory' is the only type DB allowed");
        System.exit(-1);
    }

    public HotCivServer(String type) {
        int port = 3784;
        // Define the server side delegates

        Game game = new GameImpl(new SemiCivGameFactory());
        Invoker invoker = new RootInvoker(game);

        SocketServerRequestHandler ssrh = new SocketServerRequestHandler();
        ssrh.setPortAndInvoker(port, invoker);

        DrawingEditor editor =
                new MiniDrawApplication("The SemiCiv game",
                        new HotCivFactory4(game));
        editor.open();
        editor.setTool(new CompositionTool(editor,game));
        editor.showStatus("Play semiCiv");
        System.out.println("=== HotCiv Socket based Server Request Handler (port:"
                + port + ") ===");
        System.out.println(" Use ctrl-c to terminate!");
        ssrh.run();
    }
}

