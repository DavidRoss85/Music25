package reaction.recognition;

import graphics.elements.Box;

public class Gesture {

//  private static String recognized = "NULL";
//  private static List UNDO = new List();
  public Shape shape;
  public Box box;

  /**
   * Constructor
   * @param shape
   * @param box
   */
  public Gesture(Shape shape, Box box){
    this.shape=shape;
    this.box = box;
  }
}





//  /**
//   * Public facing static helper constructor.
//   * Ensures a new gesture isn't created unless shape is recognized
//   * @param ink
//   * @return
//   */
//  public static Gesture getNew(Ink ink){
//    Shape s = Shape.recognize(ink);
//    if (s == null){
//      recognized = "NULL";
//      return null;
//    }
//    recognized = s.getName();
//    return new Gesture(s,ink.getBox());
//  }

//  public static void setRecognized(Gesture gesture){
//    recognized= gesture==null?"NULL":gesture.shape.getName();
//  }
//
//  public static void undo(){
//    System.out.println("UNDO");
//    if(UNDO.size()>0){
//      UNDO.remove(UNDO.size()-1);
//      Layer.nuke();//eliminates all masses
//      Reaction.nuke(); //clears out byShape and reloads initial reactions
//      UNDO.redo();
//    }
//  }
//
//  private void redoGesture(){
//    Reaction r = Reaction.best(this);
//    if(r!=null){r.act(this);}
//  }
//  //************************
//  //THIS METHOD CONTROLS WHAT HAPPENS AFTER A GESTURE IS RECOGNIZED
//  private void doGesture(){
//    Reaction r = Reaction.best(this);
//    if(r!=null){UNDO.add(this);r.act(this);}else{recognized+=" NO BIDS";}
//  }
//
//  //********************************************




//  //---------------List-------------------------
//  public static class List extends ArrayList<Gesture> {
//    private void redo(){
//      for(Gesture gest: this){
//        gest.redoGesture();
//      }
//    }
//  }

