package hotciv.visual;

import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import hotciv.framework.*;
import hotciv.view.*;
import hotciv.stub.*;

/** Template code for exercise FRS 36.42.

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
public class ShowEndOfTurn {
  
  public static void main(String[] args) {
    Game game = new StubGame2();

    DrawingEditor editor = 
      new MiniDrawApplication( "Click top shield to end the turn",  
                               new HotCivFactory4(game) );
    editor.open();
    editor.showStatus("Click to shield to see Game's endOfTurn method being called.");

    // TODO: Replace the setting of the tool with your EndOfTurnTool implementation.
    editor.setTool( new EndOfTurnTurnTool(editor, game) );
  }
}

class EndOfTurnTurnTool extends NullTool{
  private Game game;
  private DrawingEditor drawingEditor;
  public EndOfTurnTurnTool (DrawingEditor drawingEditor, Game game){
    this.drawingEditor = drawingEditor;
    this.game = game;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    if (drawingEditor.drawing().findFigure(GfxConstants.TURN_SHIELD_X,GfxConstants.TURN_SHIELD_Y)== drawingEditor.drawing().findFigure(x,y)){

      game.endOfTurn();
      super.mouseDown(e, x, y);
    }
  }
}