package hotciv.visual;

import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import hotciv.framework.*;
import hotciv.view.*;
import hotciv.stub.*;
import minidraw.standard.handlers.DragTracker;
import minidraw.standard.handlers.SelectAreaTracker;

/**
 * Template code for exercise FRS 36.39.
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
public class ShowMove {

    public static void main(String[] args) {
        Game game = new StubGame2();

        DrawingEditor editor =
                new MiniDrawApplication("Move any unit using the mouse",
                        new HotCivFactory4(game));
        editor.open();
        editor.showStatus("Move units to see Game's moveUnit method being called.");

        // TODO: Replace the setting of the tool with your UnitMoveTool implementation.

        editor.setTool(new UnitMoveTool(editor, game));
    }
}

class UnitMoveTool extends SelectionTool {
    private DrawingEditor editor;
    private Game game;
    private Position from = null;
    protected Tool fChild;
    protected Tool cachedNullTool;
    protected Figure draggedFigure;



    public UnitMoveTool(DrawingEditor editor, Game game) {
        super(editor);
        this.game = game;
        this.editor = editor;
        fChild = cachedNullTool = new NullTool();
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        if (game.getUnitAt(GfxConstants.getPositionFromXY(x, y)) != null) {
            Drawing model = editor().drawing();
            model.lock();
            draggedFigure = model.findFigure(e.getX(), e.getY());
            if (!e.isAltDown()) {
                model.clearSelection();
            }
                fChild = createDragTracker(draggedFigure);
                fChild.mouseDown(e, x, y);
                from = GfxConstants.getPositionFromXY(x, y);


        }
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
            fChild.mouseDrag(e, x, y);
        }


    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
         if (game.getTileAt(GfxConstants.getPositionFromXY(x, y))!= null && game.getTileAt(from)!= null) {

                 game.moveUnit(from, GfxConstants.getPositionFromXY(x, y));
             editor.drawing().unlock();
             fChild.mouseUp(e, x, y);
             fChild = cachedNullTool;
             draggedFigure = null;
             from = null;
             }
        if (game.getTileAt(GfxConstants.getPositionFromXY(x, y)) == null ) {

            editor.drawing().unlock();
            fChild.mouseUp(e, x, y);
            /** Redrawing the picture so that non allowed moves are updated immediately */
            Drawing model = editor().drawing();
            model.requestUpdate();
            fChild = cachedNullTool;
            draggedFigure = null;
            from = null;

        }
        }
    }



