package masses;

import java.util.HashMap;
import reaction.Action;
import reaction.ActionContainer;
import reaction.recognition.Gesture;
import state.States;


public abstract class Mass implements IMass {

  protected HashMap<String, Action> actions = new HashMap<>(); //Stores all actions and pairs with a name
  protected HashMap<String, String> gestureToActions = new HashMap<>();

  public Mass() {
    States.massList.add(this);
  }

  /**
   * Bid on a gesture (Lower is better)
   * @param gesture
   * @return
   */
  public int bidOnGesture(Gesture gesture) {
    return 0;
  }


  /**
   * Searches the map of actions to find the appropriate action to take
   * @param args container for details to pass to function
   */
  public void doAction(ActionContainer args) {
    String action = args.getName();
    Action actionObj = getAction(action);
    if (actionObj != null) {
      actionObj.accept(args);
    }else{
      System.out.println("No such action: " + action);
    }
  }

  public void reactOnGesture(Gesture gesture) {
    String actionName = getActionFromGesture(gesture);
    if(actionName != null) {
      ActionContainer action = new ActionContainer(actionName, gesture, gesture.getShape().getName());
      this.doAction(action);
    }else{
      System.out.println("No action for gesture " + gesture.getShape().getName());
    }
  }

  /**
   * Map gesture to an action
   * @param gesture
   * @return
   */
  private String getActionFromGesture(Gesture gesture) {

    return gestureToActions.get(gesture.getShape().getName());
  }

  private Action getAction(String action) {
    return actions.get(action);
  }
}
