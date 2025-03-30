package sandbox.runnable;

import graphics.elements.Box;
import masses.testMass.TestMass;
import masses.testMass.TestMass2;
import reaction.action.ActionContainer;
import state.ActionEntry;
import state.States;

public class ExternalRunTest {

  public static void main(String[] args) {
    TestMass mass = new TestMass();
    TestMass2 mass2 = new TestMass2();

    ActionContainer details = new ActionContainer("WRITE", new Box(1,1,1,1),"Just something.");
    ActionContainer details2 = new ActionContainer("WRITE", new Box(1,1,1,1),"Second something.");

    ActionEntry<TestMass> entry1 = new ActionEntry<>(details, mass);
    ActionEntry<TestMass2> entry2 = new ActionEntry<>(details2, mass2);

    States.actionHistory.add(entry1);
    States.actionHistory.add(entry2);


    System.out.println("\n\n\n\n***************************************\n\n\n");
    mass.doAction(details);
    mass2.doAction(details2);

    System.out.println("\n\n\n\n***************************************\n\n\n");
    States.actionHistory.executeHistory();
    System.out.println("\n\n\n\n***************************************\n\n\n");
  }
}
