package reaction.capture;

import masses.IMass;
import reaction.action.Reaction;
import reaction.recognition.Gesture;
import reaction.recognition.Shape;
import state.States;

/**
 * Gesture Area captures the mouse actions in the window.
 */
public class GestureArea implements Area{

  public static String recognized = "NULL";

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
    if(gesture == null) return;


    // Get the best bidder, map gesture to an action then execute action
    // Bidding should return a reaction which belongs to a specific object
    // Reactions should be bound to actions within the Mass
    Reaction bestReaction = States.massList.returnBestBidder(gesture);
    if (bestReaction != null && bestReaction != Reaction.NO_REACTION) {
      bestReaction.getOwner().doAction(bestReaction.getActionDetails());
    }else{
      System.out.println("No item found for " + gesture);
    }

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
