package reaction.recognition;

import graphics.elements.Box;
import java.util.ArrayList;
import reaction.capture.Ink;

public class Gesture {

  private static String recognized = "NULL";
  private static List UNDO = new List();
  public Shape shape;
  public Box vs;

  /**
   * Private Constructor
   * @param shape
   * @param vs
   */
  private Gesture(Shape shape, Box vs){
    this.shape=shape;
    this.vs=vs;
  }

  /**
   * Public facing static helper constructor.
   * Ensures a new gesture isn't created unless shape is recognized
   * @param ink
   * @return
   */
  public static Gesture getNew(Ink ink){
    Shape s = Shape.recognize(ink);
    return s==null? null:new Gesture(s,ink.getBox());
  }

  public static void setRecognized(Gesture gesture){
    recognized= gesture==null?"NULL":gesture.shape.getName();
  }

  public static void undo(){
    System.out.println("UNDO");
    if(UNDO.size()>0){
      UNDO.remove(UNDO.size()-1);
      Layer.nuke();//eliminates all masses
      Reaction.nuke(); //clears out byShape and reloads initial reactions
      UNDO.redo();
    }
  }

  private void redoGesture(){
    Reaction r = Reaction.best(this);
    if(r!=null){r.act(this);}
  }
  //************************
  //THIS METHOD CONTROLS WHAT HAPPENS AFTER A GESTURE IS RECOGNIZED
  private void doGesture(){
    Reaction r = Reaction.best(this);
    if(r!=null){UNDO.add(this);r.act(this);}else{recognized+=" NO BIDS";}
  }

  //********************************************




  //---------------List-------------------------
  public static class List extends ArrayList<Gesture> {
    private void redo(){
      for(Gesture gest: this){
        gest.redoGesture();
      }
    }
  }
}
