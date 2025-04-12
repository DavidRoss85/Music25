package masses.head;

import config.UConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import masses.Mass;
import masses.accid.Accid;
import masses.glyph.Glyph;
import masses.staff.Staff;
import masses.stem.Stem;
import masses.time.Time;
import reaction.action.ActionContainer;
import reaction.action.Reaction;
import reaction.recognition.Gesture;

public class Head extends Mass implements Comparable<Head> {

  private static final String ADD_STEM_TAG = "ADD_NEW_STEM";
//  private static final String ADD_SYS_TAG = "ADD_NEW_SYS";

  private static HashMap<String, ArrayList<String>> globalShapeToActionsMap = new HashMap<>();
  static {
    //Static to be accessed by all Page objects
    //Add a function here that can load settings from configuration
    globalShapeToActionsMap.put("S-S",new ArrayList<>(Arrays.asList(ADD_STEM_TAG)));
//    globalShapeToActionsMap.put("W-E",new ArrayList<>(Arrays.asList(ADD_SYS_TAG)));

  }

  public Staff staff;
  public int line;
  public Time time;
  public Glyph forcedGlyph = null;
  public Stem stem = null;
  public boolean wrongSide;
  public Accid accid = null;

  public Head(Staff staff, int x, int y) {
    super("NOTE");
    this.staff = staff;
    time = staff.sys.getTime(x);
    line = staff.lineOfY(y);
    time.heads.add(this);

    setUpActions();
    setUpReactions();
    //Copy global settings. This allows using one universal bid function in Map class:
    this.localShapeToActionsMap = globalShapeToActionsMap;

//    addReaction(new Reaction("DOT") {
//      @Override
//      public int bid(Gesture g) {
//        if (Head.this.stem == null) {
//          return UC.noBid;
//        }
//        int xH = x(), yH = y(), H = staff.fmt.H, W = w();
//        int x = g.vs.xM(), y = g.vs.yM();
//        if (x < xH || x > xH + 2 * W || y < yH - H || y > yH + H) {
//          return UC.noBid;
//        }
//        return Math.abs(xH + W - x) + Math.abs(yH - y);
//      }
//
//      public void act(Gesture g) {
//        Head.this.stem.cycleDot();
//      }
//    });
//
//    addReaction(new Reaction("NE-SE") { //up arrow raises sharp
//      public int bid(Gesture g) {
//        int x = g.vs.xM(), y = g.vs.yL();
//        int hX = Head.this.x() + Head.this.w() / 2, hY = Head.this.y();
//        int dX = Math.abs(x - hX), dY = Math.abs(y - hY), dist = dX + dY;
//        return dist > 50 ? UC.noBid : dist;
//      }
//
//      public void act(Gesture g) {
//        Head.this.accidUp();
//      }
//    });
//
//    addReaction(new Reaction("SE-NE") { //down arrow lowers sharp
//      public int bid(Gesture g) {
//        int x = g.vs.xM(), y = g.vs.yL();
//        int hX = Head.this.x() + Head.this.w() / 2, hY = Head.this.y();
//        int dX = Math.abs(x - hX), dY = Math.abs(y - hY), dist = dX + dY;
//        return dist > 50 ? UC.noBid : dist;
//      }
//
//      public void act(Gesture g) {
//        Head.this.accidDown();
//      }
//    });
//
//    addReaction(new Reaction("S-N") { //delete
//      public int bid(Gesture g) {
//        int x = g.vs.xM(), y = g.vs.yL();
//        int aX = Head.this.x() + Head.this.w() / 2, aY = Head.this.y();
//        int dX = Math.abs(x - aX), dY = Math.abs(y - aY), dist = dX + dY;
//        return dist > 50 ? UC.noBid : dist;
//      }
//
//      public void act(Gesture g) {
//        Head.this.deleteHead();
//      }
//    });
  }

  public void setUpActions() {
    this.actions.put(ADD_STEM_TAG,this::stemHead);
  }

  public void setUpReactions(){
    this.reactionMap.put(ADD_STEM_TAG, new Reaction(this,ADD_STEM_TAG) {
      public int makeBid(Gesture gesture) { //stem or unstem heads
        this.setActionDetails(new ActionContainer(this.getActionName(),gesture,null));
        int x = gesture.getBox().xM(), y1 = gesture.getBox().yL(), y2 = gesture.getBox().yH();
        int W = Head.this.w(), hY = Head.this.y();
        int y = staff.yOfLine(Head.this.line);
        if (y1 > y || y2 < y) {
          return UConstants.noBid;
        }
        int hL = Head.this.time.x, hR = hL + W;
        if (x < hL - W || x > hR + W) {
          bid = UConstants.noBid;
          return bid;
        }
        if (x < hL + W / 2) {
          bid = hL - x;
          return bid;
        }
        if (x > hR - W / 2) {
          bid = x - hR;
          return bid;
        }
        bid = UConstants.noBid;
        return bid;
      }
    });
  }

  private void stemHead(ActionContainer args) {

    int x = args.getBox().xM(),
        y1 = args.getBox().yL(),
        y2 = args.getBox().yH();

    Staff staff = Head.this.staff;
    Time t = Head.this.time;
    int W = Head.this.w();
    boolean up = x > t.x + W / 2;
    if (Head.this.stem == null) {
      //t.stemHeads(staff,up,y1,y2);
      Stem.getStem(staff, t, y1, y2, up);
    } else {
      t.unStemHeads(y1, y2);
    }

  }

  public void deleteHead(ActionContainer args) {
    if (accid != null) {
      accid.deleteAccid(args);
    }
    unStem(args);
    time.heads.remove(this);
    deleteMass(args);
  }

  private void accidUp() {
    if (accid == null) {
      accid = new Accid(this, Accid.SHARP);
      return;
    }
    if (accid.iGlyph < 4) {
      accid.iGlyph++;
    }
  }

  private void accidDown() {
    if (accid == null) {
      accid = new Accid(this, Accid.FLAT);
      return;
    }
    if (accid.iGlyph > 0) {
      accid.iGlyph--;
    }
  }

  public int w() {
    return 24 * staff.fmt.H / 10;
  } // width of note head

  public int y() {
    return staff.yOfLine(line);
  }

  public int x() {
    int res = time.x;
    if (wrongSide) {
      res += (stem != null && stem.isUp) ? w() : -w();
    }
    return res;
  }

  public Glyph normalGlyph() {
    if (stem == null) {
      return Glyph.HEAD_Q;
    }
    if (stem.nFlag == -1) {
      return Glyph.HEAD_HALF;
    }
    if (stem.nFlag == -2) {
      return Glyph.HEAD_W;
    }
    return Glyph.HEAD_Q;
  }

  public void delete() {
    time.heads.remove(this);
  } //STUB

  public void show(Graphics g) {
    g.setColor(stem == null ? Color.BLUE : Color.BLACK);

    int H = staff.fmt.H;
    (forcedGlyph != null ? forcedGlyph : normalGlyph()).showAt(g, H, x(), y());
    if (stem != null) {
      int off = UConstants.AugDotOffset, sp = UConstants.AugDotSpacing;
      for (int i = 0; i < stem.nDot; i++) {
        g.fillOval(x() + off + i * sp, y() - 3 * H / 2, H * 2 / 3, H * 2 / 3);
      }
    }
    g.setColor(Color.BLACK);
  }

  public void unStem(ActionContainer args) {
    if (stem != null) {
      stem.heads.remove(this);
      if (stem.heads.size() == 0) {
        stem.deleteStem(args);
      }
      stem = null;
      wrongSide = false;
    }
  }

  public void joinStem(Stem s) {
    if (stem != null) {
      unStem(ActionContainer.EMPTY_ACTION); //FIX LATER
    }
    s.heads.add(this);
    stem = s;
  }

  @Override
  public int compareTo(Head h) {
    return (staff.iStaff != h.staff.iStaff ? staff.iStaff - h.staff.iStaff : line - h.line);
  }
}