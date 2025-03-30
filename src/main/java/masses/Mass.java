package masses;

import java.util.HashMap;
import reaction.Action;
import reaction.ActionContainer;
import reaction.recognition.Gesture;
import state.States;


public abstract class Mass implements IMass {

  protected HashMap<String, Action> actions = new HashMap<>(); //Stores all actions and pairs with a name
  protected HashMap<Gesture, String> gestureToActions = new HashMap<>();

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
   * Map gesture to an action
   * @param gesture
   * @return
   */
  public String getActionFromGesture(Gesture gesture) {
    return gestureToActions.get(gesture);
  }

  public Action getAction(String action) {
    return actions.get(action);
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
}
