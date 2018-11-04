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

/** Template code for exercise FRS 36.39.

   This source code is from the book
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author:
     Henrik B Christensen
     Computer Science Department
     Aarhus University

   This source code is provided WITHOUT ANY WARRANTY either
   expressed or implied. You may study, use, modify, and
   distribute it for non-commercial purposes. For any
   commercial use, see http://www.baerbak.com/
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
class UnitMoveTool extends NullTool{
    private DrawingEditor drawingEditor;
    private Game game;
    private Position fromPosition;
    private Tool unitFigure;
    private Tool dragFigure;
    private Tool movingFigure;
    private Figure lockedFigure;
    UnitMoveTool(DrawingEditor drawingEditor, Game game){
        this.drawingEditor= drawingEditor;
        this.game = game;
    }

    public void mouseDown(MouseEvent e, int x, int y) {
        if (game.getUnitAt(GfxConstants.getPositionFromXY(x,y))!= null){
            fromPosition = GfxConstants.getPositionFromXY(x,y);
            Drawing model = drawingEditor.drawing();
            model.lock();
            lockedFigure = model.findFigure(e.getX(),e.getY());
            unitFigure = new SelectAreaTracker(drawingEditor);
            dragFigure = new DragTracker(drawingEditor, lockedFigure);



        }
    }

    public void mouseUp(MouseEvent e, int x, int y) {
        drawingEditor.drawing().unlock();
        dragFigure = null;
        game.moveUnit(fromPosition,GfxConstants.getPositionFromXY(x,y));

    }
}
/*
class UnitMoveTool extends SelectionTool {
  private DrawingEditor editor;
  private Game game;
  private Position from;


  public UnitMoveTool(DrawingEditor editor, Game game) {
    super(editor);
    this.game = game;
    this.editor = editor;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    if (game.getUnitAt(GfxConstants.getPositionFromXY(x, y)) != null) {
      from = GfxConstants.getPositionFromXY(x, y);
    }// TODO: CHECKING FOR PROPER NOT NULL AND DRAGGING UNITS
  }

  @Override
  public void mouseUp(MouseEvent e, int x, int y) {

          game.moveUnit(from, GfxConstants.getPositionFromXY(x, y));


  }


}
*/


