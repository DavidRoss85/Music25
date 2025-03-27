package state;

import reaction.ActionContainer;

/**
 * Action Entries contain different object types that actions are performed on.
 * Hence a generic type is used.
 * @param <T> Type of object which action can be peformed on
 */
public class ActionEntry<T> {
  private ActionContainer action;
  private T target;

  public ActionEntry(ActionContainer action, T target) {
    this.action = action;
    this.target = target;
  }

  public ActionContainer getAction() {
    return action;
  }

  public T getTarget() {
    return target;
  }
}
