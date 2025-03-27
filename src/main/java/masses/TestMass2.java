package masses;

import reaction.ActionContainer;

public class TestMass2 extends Mass {

  private String myString = "Just A demo";

  public TestMass2() {
    this.actions.put("WRITE",this::writeSomeText);
  }

  private void writeSomeText(ActionContainer args){
    String text = args.getStringInfo();
    System.out.println(text+ doSomeAction());
  }

  private String doSomeAction(){
    myString += " And doncha know!\n";
    return myString;
  }
}