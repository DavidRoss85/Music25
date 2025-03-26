package sandbox.runnable;

import masses.Mass;
import reaction.ActionContainer;
import reaction.Gesture;

public class ExternalRunTest {

  public static void main(String[] args) {
//    Mass mass = new Mass();
    ActionContainer actionContainer = new ActionContainer(new Gesture(),"This is a gesture");

//    mass.doAction("demo", actionContainer);
  }
}
