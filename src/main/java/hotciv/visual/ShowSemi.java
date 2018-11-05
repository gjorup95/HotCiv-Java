package hotciv.visual;

import hotciv.standard.GameImpl;
import hotciv.standard.SemiCivGameFactory;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

/**
 * @ author Troels Gjørup
 * 05-11-2018
 */
public class ShowSemi  {
    public static void main(String[] args) {
        GameImpl game = new GameImpl(new SemiCivGameFactory());
        DrawingEditor editor =
                new MiniDrawApplication("The SemiCiv game",
                        new HotCivFactory4(game));
        editor.open();
        editor.setTool(new CompositionTool(editor,game));
        editor.showStatus("Play semiCiv");
    }
}
