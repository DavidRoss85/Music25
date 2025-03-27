package reaction.capture;

import reaction.recognition.Gesture;
import reaction.recognition.Shape;

/**
 * Gesture Area captures the mouse actions in the window.
 */
public class GestureArea implements Area{

  private String recognized = "NULL";

  public GestureArea() {
  }

  public boolean hit(int x, int y) {
    return true;
  }


  public void cursorDown(int x, int y) {
    Ink.getBuffer().cursorDown(x,y);
  }


  public void cursorDrag(int x, int y) {
    Ink.getBuffer().cursorDrag(x,y);
  }

  /**
   * Takes the ink buffer and calls getNew() which references the shape database and
   * returns a gesture which contains a recognized shape plus a vs (bbox of gesture)
   * @param x
   * @param y
   */
  public void cursorUp(int x, int y) {
    Ink.getBuffer().cursorUp(x,y);
    Ink ink = new Ink();
    Gesture gesture = createGesture(ink);
    Ink.getBuffer().clear();

//    Gesture.setRecognized(gesture);
//
//    if(gesture !=null){
//      // MAKE A CALL TO THE ACTION HERE
//      if(gesture.shape.getName().equals("N-N")){
//        gesture.undo();
//      }else{
//        gesture.doGesture();
//      }
//    }
  }

  private Gesture createGesture(Ink ink){
    Shape s = Shape.recognize(ink);
    if (s == null){
      recognized = "NULL";
      return null;
    }
    recognized = s.getName();
    return new Gesture(s,ink.getBox());
  }

}
