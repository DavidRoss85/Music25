package reaction.capture;

import reaction.Gesture;

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
    Gesture gest = Gesture.getNew(ink);
    Ink.getBuffer().clear();

    recognized= gest==null?"NULL":gest.shape.name;

    if(gest!=null){
      if(gest.shape.name.equals("N-N")){
        undo();
      }else{
        gest.doGesture();
      }
    }
  }

}
