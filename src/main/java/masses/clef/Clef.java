package masses.clef;

import config.UConstants;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import masses.Mass;
import masses.glyph.Glyph;
import masses.staff.Staff;
import reaction.action.ActionContainer;
import reaction.action.Reaction;
import reaction.recognition.Gesture;

public class Clef extends Mass implements Comparable<Clef> {

  private static HashMap<String, ArrayList<String>> globalShapeToActionsMap = new HashMap<>();

  static {
    //Static to be accessed by all Page objects
    //Add a function here that can load settings from configuration
    globalShapeToActionsMap.put("S-N", new ArrayList<>(Arrays.asList(DELETE_TAG)));

  }

  public Glyph glyph;
  public int x;
  public Staff staff;

  public Clef(Staff staff, int x, Glyph glyph) {
    super("NOTE");
    this.staff = staff;
    this.x = x;
    this.glyph = glyph;

    setUpActions();
    setUpReactions();
    this.localShapeToActionsMap = globalShapeToActionsMap;

  }

  private void setUpActions(){
    this.actions.put(DELETE_TAG,this::deleteClef);
  }

  /**
   * Set up reactions for this object.
   * Creates anonymous Reactions and adds them to this item's list of reactions.
   * Gestures/Shapes can be mapped to these later
   */
  private void setUpReactions(){
    this.reactionMap.put(DELETE_TAG,new Reaction(this,DELETE_TAG) { //delete

      public int makeBid(Gesture gesture) {
        int x = gesture.getBox().xM(), y = gesture.getBox().yL();
        int aX = Clef.this.x + staff.fmt.H * 2, aY = staff.yOfLine(4);
        int dX = Math.abs(x - aX), dY = Math.abs(y - aY), dist = dX + dY;
        bid = dist > 50 ? UConstants.noBid : dist;
        return bid;
      }

    });
  }


  private void deleteClef(ActionContainer args) {
    staff.clefs.remove(this);
    if (staff.clefs.size() == 0) {
      staff.clefs = null;
    } else {
      Collections.sort(staff.clefs);
    }
    deleteMass(args);
  }

  public void show(Graphics g) {
    glyph.showAt(g, staff.fmt.H, x, staff.yOfLine(4));
  }

  @Override
  public int compareTo(Clef c) {
    return x - c.x;
  }
}
