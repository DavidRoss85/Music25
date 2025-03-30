package reaction.action;

/**
 * Functional interface for calling functions from Mass Hashmap
 */
public interface Action {
  /**
   * Accepts the arguments to the called function
   * @param action as ActionContainer
   */
  public void accept(ActionContainer action);
}
