package masses;

import reaction.action.ActionContainer;

public class TestMass extends Mass {

  public TestMass() {
    this.actions.put("WRITE",this::writeSomeText);
    this.gestureToActions.put("S-S","WRITE");
  }

  private void writeSomeText(ActionContainer args){
    String text = args.getStringInfo();
    System.out.println(text);
  }
}
