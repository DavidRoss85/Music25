package masses;

import graphics.drawing.Layer;
import graphics.interfaces.Show;
import java.awt.Graphics;
import java.util.HashMap;
import reaction.action.Action;
import reaction.action.ActionContainer;
import reaction.recognition.Gesture;
import state.ActionEntry;
import state.States;


public abstract class Mass implements IMass, Show {

  protected HashMap<String, Action> actions = new HashMap<>(); //Stores all actions and pairs with a name
  protected HashMap<String, String> gestureToActions = new HashMap<>();
  protected Layer layer;

  public Mass(String layerName) {
    layer = Layer.byName.get(layerName);
    if (layer != null) {
      layer.add(this);
    }else{
      System.out.println("Layer " + layerName + " not found");
    }
    States.massList.add(this);
  }

  /**
   * Bid on a gesture (Lower is better)
   * @param gesture
   * @return
   */
  public int bidOnGesture(Gesture gesture) {
    return 999999;
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
      if(!args.isRedo()) {
        ActionEntry<? extends Mass> actionEntry = new ActionEntry<>(args, this);
        States.actionHistory.add(actionEntry);
      }
    }else{
      System.out.println("No such action: " + action);
    }
  }

  public void reactOnGesture(Gesture gesture) {
    if(gesture==null) return;
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
    if(gesture!=null) {
      return gestureToActions.get(gesture.getShape().getName());
    }
    return null;
  }

  private Action getAction(String action) {
    return actions.get(action);
  }

  public void show(Graphics g) {}

  //Keeping the code below in case bug shows up again:
  //Fix a bug that shows up removing masses as I.Shows from layers
//  private static int M = 1;
//  private int hash = M++;
//
//  @Override
//  public int hashCode(){return hash;}
//  @Override
//  public boolean equals(Object o){return this==o;}
}
