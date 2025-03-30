package masses.testMass;

import masses.Mass;
import reaction.action.ActionContainer;

public class TestMass extends Mass {

  public TestMass() {
    super("FRONT");
    this.actions.put("WRITE",this::writeSomeText);
    this.gestureToActions.put("S-S","WRITE");
  }

  private void writeSomeText(ActionContainer args){
    String text = args.getStringInfo();
    System.out.println(text);
  }
}
