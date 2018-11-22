package hotciv.visual;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;
import minidraw.standard.SelectionTool;

import java.awt.event.MouseEvent;

public class CompositionTool extends SelectionTool {
    private Game game;
    private DrawingEditor drawingEditor;
    private NullTool specificTool;
    private SelectionTool selectionTool;
    private Position from;

    public CompositionTool(DrawingEditor editor, Game game) {
        super(editor);
        this.game = game;
        drawingEditor = editor;
    }

    public void mouseDown(MouseEvent e, int x, int y) {
        if (drawingEditor.drawing().findFigure(GfxConstants.TURN_SHIELD_X, GfxConstants.TURN_SHIELD_Y) == drawingEditor.drawing().findFigure(x, y)) {
            specificTool = new EndOfTurnTurnTool(drawingEditor, game);
            specificTool.mouseDown(e, x, y);
        }
        if (game.getUnitAt(GfxConstants.getPositionFromXY(x, y)) != null && e.isShiftDown()) {
            specificTool = new ActionTool(drawingEditor, game);
            specificTool.mouseDown(e, x, y);

        }
        specificTool = new SetFocusTool(drawingEditor, game);
        specificTool.mouseDown(e, x, y);
        // TODO make setfocustool of type selectiontool
        selectionTool = new UnitMoveTool(drawingEditor,game);
        //specificTool = null;
        selectionTool.mouseDown(e, x, y);
            from = GfxConstants.getPositionFromXY(x, y);
        }



    public void mouseDrag(MouseEvent e, int x, int y) {
        selectionTool.mouseDrag(e, x, y);
    }

    public void mouseUp(MouseEvent e, int x, int y) {
        if (drawingEditor.drawing().findFigure(GfxConstants.REFRESH_BUTTON_X,GfxConstants.REFRESH_BUTTON_Y)== drawingEditor.drawing().findFigure(x,y)) {
            editor.drawing().requestUpdate();
        }
        if (game.getTileAt(from) != game.getTileAt(GfxConstants.getPositionFromXY(x, y))) {
            selectionTool.mouseUp(e, x, y);
        }
    }
}
