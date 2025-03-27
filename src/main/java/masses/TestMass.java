package masses;

import reaction.ActionContainer;

public class TestMass extends Mass {

  public TestMass() {
    this.actions.put("WRITE",this::writeSomeText);
  }

  private void writeSomeText(ActionContainer args){
    String text = args.getStringInfo();
    System.out.println(text);
  }
}
