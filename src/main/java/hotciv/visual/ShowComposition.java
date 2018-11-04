package hotciv.visual;

import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import hotciv.framework.*;
import hotciv.view.*;
import hotciv.stub.*;

/**
 * Template code for exercise FRS 36.44.
 * <p>
 * This source code is from the book
 * "Flexible, Reliable Software:
 * Using Patterns and Agile Development"
 * published 2010 by CRC Press.
 * Author:
 * Henrik B Christensen
 * Computer Science Department
 * Aarhus University
 * <p>
 * This source code is provided WITHOUT ANY WARRANTY either
 * expressed or implied. You may study, use, modify, and
 * distribute it for non-commercial purposes. For any
 * commercial use, see http://www.baerbak.com/
 */
public class ShowComposition {

    public static void main(String[] args) {
        Game game = new StubGame2();

        DrawingEditor editor =
                new MiniDrawApplication("Click and/or drag any item to see all game actions",
                        new HotCivFactory4(game));
        editor.open();
        editor.showStatus("Click and drag any item to see Game's proper response.");

        // TODO: Replace the setting of the tool with your CompositionTool implementation.
        editor.setTool(new CompositionTool(editor, game));
    }
}

class CompositionTool extends NullTool {
    private Game game;
    private DrawingEditor drawingEditor;
    private Position positionFrom;
    private NullTool specificTool;
    private SelectionTool selectionTool;


    public CompositionTool(DrawingEditor editor, Game game) {
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
        if (game.getTileAt(GfxConstants.getPositionFromXY(x, y)) != null) {

            positionFrom = GfxConstants.getPositionFromXY(x, y);
        }
    }

    public void mouseUp(MouseEvent e, int x, int y) {
        if (game.getTileAt(positionFrom) != game.getTileAt(GfxConstants.getPositionFromXY(x, y))) {
            game.moveUnit(positionFrom, GfxConstants.getPositionFromXY(x, y));
        }
    }
}
