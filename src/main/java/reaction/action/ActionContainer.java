package reaction.action;

import graphics.elements.Box;
import graphics.interfaces.Act;
import reaction.recognition.Gesture;

/**
 * Container to deliver the action to the Mass
 */
public class ActionContainer {

  public static final ActionContainer EMPTY_ACTION = new ActionContainer(
      null,Box.EMPTY_BOX,null
  );

  private Gesture gesture;
  private String stringInfo;
  private Box box = new Box(0,0,1,1);
  private String name;
  private Boolean redo = false; //Determines if this action comes from the user or the history
  private String actionType = "gesture";

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
    this.box = box;
  }

  public void setRedo(Boolean isRedo){
    this.redo = isRedo;
  }

  public Boolean isRedo(){
    return this.redo;
  }

  public void setActionType(String actionType){
    this.actionType = actionType;
  }

  public String getActionType(){
    return this.actionType;
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
