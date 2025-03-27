package reaction;

import graphics.elements.Box;
import reaction.recognition.Gesture;

/**
 * Container to deliver the action to the Mass
 */
public class ActionContainer {

  private Gesture gesture;
  private String stringInfo;
  private Box box;

  public ActionContainer(Gesture gesture, String stringInfo){
    this.gesture = gesture;
    this.stringInfo = stringInfo;
    if(this.gesture == null){
      this.box = new Box(0,0,1,1);
    }
  }

  public ActionContainer(Box box, String stringInfo){
    this.stringInfo = stringInfo;
    if(box == null){
      this.box = new Box(0,0,1,1);
    }
  }

  public Gesture getGesture() {
    return gesture;
  }

  public String getStringInfo() {
    return stringInfo;
  }
  public Box getBox() {
    return box;
  }
}
