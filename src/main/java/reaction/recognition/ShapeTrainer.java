package reaction.recognition;

import config.UConstants;
import graphics.window.WinApp;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class ShapeTrainer extends WinApp {
  public ShapeTrainer(){
    super("Shape Trainer", UConstants.screenWidth, UConstants.screenHeight);
  }
  public void paintComponent(Graphics g){Shape.TRAINER.show(g);}

  public void mousePressed(MouseEvent me){Shape.TRAINER.cursorDown(me.getX(),me.getY()); repaint();}
  public void mouseDragged(MouseEvent me){Shape.TRAINER.cursorDrag(me.getX(),me.getY()); repaint();}
  public void mouseReleased(MouseEvent me){Shape.TRAINER.cursorUp(me.getX(),me.getY()); repaint();}
  public void keyTyped(KeyEvent ke){Shape.TRAINER.keyTyped(ke); repaint();}

  public static void main(String[] args){
    PANEL=new ShapeTrainer();
    WinApp.launch();
  }
}