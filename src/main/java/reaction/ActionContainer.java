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
  private String name;

  public ActionContainer(String name, Gesture gesture, String stringInfo){
    this.name = name;
    this.gesture = gesture;
    this.stringInfo = stringInfo;
    if(this.gesture == null){
      this.box = new Box(0,0,1,1);
    }
  }

  public ActionContainer(String name, Box box, String stringInfo){
    this.name = name;
    this.stringInfo = stringInfo;
    if(box == null){
      this.box = new Box(0,0,1,1);
    }
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
