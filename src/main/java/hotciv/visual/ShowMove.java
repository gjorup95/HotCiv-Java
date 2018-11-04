package hotciv.visual;

import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import hotciv.framework.*;
import hotciv.view.*;
import hotciv.stub.*;

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
      super.mouseDown(e, x, y);
    }// TODO: CHECKING FOR PROPER NOT NULL AND DRAGGING UNITS
  }

  @Override
  public void mouseUp(MouseEvent e, int x, int y) {
      if(game.getUnitAt(from)!= null) {
          super.mouseUp(e, x, y);
          game.moveUnit(from, GfxConstants.getPositionFromXY(x, y));

      }
  }
}


