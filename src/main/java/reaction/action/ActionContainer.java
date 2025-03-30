package reaction.action;

import graphics.elements.Box;
import reaction.recognition.Gesture;

/**
 * Container to deliver the action to the Mass
 */
public class ActionContainer {

  private Gesture gesture;
  private String stringInfo;
  private Box box = new Box(0,0,1,1);
  private String name;
  private Boolean redo = false; //Determines if this action comes from the user or the history

  public ActionContainer(String name, Gesture gesture, String stringInfo){
    this.name = name;
    this.gesture = gesture;
    this.stringInfo = stringInfo;
    if(this.gesture != null) {
      this.box = gesture.getBox();
    }
  }

  public ActionContainer(String name, Box box, String stringInfo){
    this.name = name;
    this.stringInfo = stringInfo;
    if(this.gesture != null) {
      this.box = gesture.getBox();
    }
  }

  public void setRedo(Boolean isRedo){
    this.redo = isRedo;
  }

  public Boolean isRedo(){
    return this.redo;
  }

  public Gesture getGesture() {
    return gesture;
  }
  public String getName(){
    return name;
  }
  public String getStringInfo() {
    return stringInfo;
  }
  public Box getBox() {
    return box;
  }
}
