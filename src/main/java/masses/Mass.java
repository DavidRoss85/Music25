package masses;

import java.util.HashMap;
import reaction.Action;
import reaction.ActionContainer;


public abstract class Mass {

  protected HashMap<String, Action> actions = new HashMap<>(); //Stores all actions and pairs with a name


  public Mass() {}

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
