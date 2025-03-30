package sandbox.massTests;

import config.UConstants;
import graphics.drawing.G;
import graphics.drawing.Layer;
import graphics.elements.Box;
import graphics.window.WinApp;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import masses.page.Page;
import masses.testMass.TestMass;
import reaction.action.ActionContainer;
import reaction.capture.GestureArea;
import reaction.capture.Ink;
import state.States;

public class ReactionTest extends WinApp {

  static{
    new Layer("BACK"); new Layer("FRONT");

  }

  private GestureArea drawArea = new GestureArea();

  TestMass mass = new TestMass();


  Page page;

  public ReactionTest() {
    super("Reaction Test", UConstants.screenWidth, UConstants.screenHeight);
    page = new Page(100);
//    ActionContainer action = new ActionContainer("ADD_NEW_STAFF",new Box(1,200,10,10),"none");
//    page.doAction(action);
//    page.doAction();
  }

  public void mousePressed(MouseEvent me){drawArea.cursorDown(me.getX(),me.getY());repaint();}
  public void mouseDragged(MouseEvent me){drawArea.cursorDrag(me.getX(),me.getY());repaint();}
  public void mouseReleased(MouseEvent me){drawArea.cursorUp(me.getX(),me.getY());repaint();}
  public void keyPressed(KeyEvent ke){
    if(ke.getKeyCode() == KeyEvent.VK_0){
      System.out.println(States.actionHistory.size());
    }else if(ke.getKeyCode()==KeyEvent.VK_R){
      States.actionHistory.executeHistory();
    }

  }

  public void paintComponent(Graphics g) {
    G.fillBack(g);
    g.setColor(Color.BLUE);
    Layer.ALL.show(g);
    Ink.getBuffer().show(g);
    g.drawString(GestureArea.recognized,900,30);
  }

  public static void main(String[] args) {
    PANEL = new ReactionTest();
    WinApp.launch();
  }
}
