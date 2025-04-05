package masses.duration;

import java.awt.Graphics;
import masses.Mass;

public abstract class Duration extends Mass {
  public abstract void show(Graphics g);

  public int nFlag = 0; // range -2 to 4
  public int nDot = 0; // range 0 to 3

  public Duration(){
    super("NOTE");
  }

  public void incFlag(){if(nFlag<4){nFlag++;}}
  public void decFlag(){if(nFlag>-2){nFlag--;}}
  public void cycleDot(){nDot++;if(nDot>3){nDot=0;}}
}
