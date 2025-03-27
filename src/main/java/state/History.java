package state;

import java.util.ArrayList;
import masses.Mass;
import reaction.Action;
import reaction.ActionContainer;

public class History <T extends Mass>  {

  private ArrayList<ActionEntry<? extends T>> history = new ArrayList<>();

  public History() {
  }

  public void add(ActionEntry<? extends T> entry) {
    history.add(entry);
  }

  public void executeHistory() {
    for (ActionEntry<? extends T> entry : history) {
      entry.getTarget().doAction(entry.getAction());
    }
  }
}
