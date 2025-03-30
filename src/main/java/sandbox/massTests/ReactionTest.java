package sandbox.massTests;

import config.UConstants;
import graphics.drawing.G;
import graphics.drawing.Layer;
import graphics.window.WinApp;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import masses.TestMass;
import reaction.capture.GestureArea;
import reaction.capture.Ink;

public class ReactionTest extends WinApp {

  static{
    new Layer("BACK"); new Layer("FRONT");
  }

  private GestureArea drawArea = new GestureArea();

  TestMass mass = new TestMass();

  public ReactionTest() {
    super("Reaction Test", UConstants.screenWidth, UConstants.screenHeight);
  }

  public void mousePressed(MouseEvent me){drawArea.cursorDown(me.getX(),me.getY());repaint();}
  public void mouseDragged(MouseEvent me){drawArea.cursorDrag(me.getX(),me.getY());repaint();}
  public void mouseReleased(MouseEvent me){drawArea.cursorUp(me.getX(),me.getY());repaint();}

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
