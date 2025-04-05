package masses.sys;

import config.UConstants;
import graphics.elements.Box;
import graphics.elements.RelativeCoordinate;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import masses.Mass;
import masses.beam.Beam;
import masses.page.Page;
import masses.staff.Fmt;
import masses.staff.Staff;
import masses.staff.StaffList;
import masses.stem.Stem;
import masses.stem.StemList;
import masses.time.Time;
import masses.time.TimeList;
import reaction.action.ActionContainer;
import reaction.action.Reaction;
import reaction.recognition.Gesture;

public class Sys extends Mass {

  private static final String BEAM_STEMS_TAG = "BEAM_STEMS_TAG";
  private static final String INCREMENT_KEY_TAG = "INCREMENT_KEY";
  private static final String DECREMENT_KEY_TAG = "DECREMENT_KEY";

  private static HashMap<String, ArrayList<String>> globalShapeToActionsMap = new HashMap<>();
  static {
    //Static to be accessed by all Page objects
    //Add a function here that can load settings from configuration
    globalShapeToActionsMap.put("E-E",new ArrayList<>(Arrays.asList(BEAM_STEMS_TAG,INCREMENT_KEY_TAG)));
    globalShapeToActionsMap.put("W-W",new ArrayList<>(Arrays.asList(INCREMENT_KEY_TAG)));

  }

  public Page page;
  public int iSys;
  public StaffList staffs; //y coordinate hidden in this list
  public TimeList times;
  public StemList stems= new StemList();
//  public Key initialKey = new Key();

  public Sys(Page page, RelativeCoordinate sysTop){
    super("BACK");
    this.page=page;
    iSys=page.sysList.size();
    staffs=new StaffList(sysTop);
    times=new TimeList(this);
    if(iSys==0){
      staffs.add(new Staff(this,0,new RelativeCoordinate(sysTop,0),new Fmt(5,8)));
    }else{
      Sys oldSys = page.sysList.get(0);
      for(Staff oldStaff: oldSys.staffs){
        Staff ns = oldStaff.copy(this);
        this.staffs.add(ns);
      }
    }
    setUpActions();
    setUpReactions();
  }

  private void setUpActions(){
    this.actions.put(BEAM_STEMS_TAG,this::beamStems);
    this.actions.put(INCREMENT_KEY_TAG,this::incrementKey);
    this.actions.put(DECREMENT_KEY_TAG,this::decrementKey);
  }

  private void setUpReactions(){
    this.reactionMap.put(BEAM_STEMS_TAG, new Reaction(this,BEAM_STEMS_TAG){
      @Override
      public int makeBid(Gesture gesture) {
        this.setActionDetails(new ActionContainer(this.getActionName(),gesture,null));
        int x1 = gesture.getBox().xL(), y1=gesture.getBox().yL(), x2=gesture.getBox().xH(), y2=gesture.getBox().yH();
        if(stems.fastReject((y1+y2)/2)){
          bid = UConstants.noBid;
          return bid;
        }
        ArrayList<Stem> temp = stems.allIntersectors(x1,y1,x2,y2);
        if(temp.size()<2){
          bid = UConstants.noBid;
          return bid;
        }
        Beam b=temp.get(0).beam;
        for(Stem s: temp){
          if(s.beam!=b){
            bid = UConstants.noBid;
            return bid;
          }
        }
        if(b==null && temp.size()!=2){
          bid = UConstants.noBid;
          return bid;
        }
        if(b==null && ((temp.get(0).nFlag!=0) || temp.get(1).nFlag!=0)){
          bid = UConstants.noBid;
          return bid;
        }
        bid = 50;
        return bid;
      }
    });
    this.reactionMap.put(INCREMENT_KEY_TAG, new Reaction(this,INCREMENT_KEY_TAG){
      @Override
      public int makeBid(Gesture gesture) {
        this.setActionDetails(new ActionContainer(this.getActionName(),gesture,null));
        int x = page.margins.left;
        int x1=gesture.getBox().xL(), x2=gesture.getBox().xH();
        if(x1>x || x2<x){
          bid = UConstants.noBid;
          return bid;
        }
        int y=gesture.getBox().yM();
        if(y<yTop() || y>yBot()){
          bid = UConstants.noBid;
          return bid;
        }
        bid = Math.abs(x-(x1+x2)/2);
        return bid;
      }
    });
    this.reactionMap.put(DECREMENT_KEY_TAG, new Reaction(this,DECREMENT_KEY_TAG){
      @Override
      public int makeBid(Gesture gesture) {
        int x = page.margins.left;
        int x1=gesture.getBox().xL(), x2=gesture.getBox().xH();
        if(x1>x || x2<x){
          bid = UConstants.noBid;
          return bid;
        }
        int y=gesture.getBox().yM();
        if(y<yTop() || y>yBot()){
          bid = UConstants.noBid;
          return bid;
        }
        bid = Math.abs(x-(x1+x2)/2);
        return bid;
      }
    });
  }

  private void beamStems(ActionContainer args){
    Box g = args.getBox();
    int x1 = g.xL(), y1=g.yL(), x2=g.xH(), y2=g.yH();
//    ArrayList<Stem> temp = stems.allIntersectors(x1,y1,x2,y2);
//    Beam b = temp.get(0).beam;
//    if(b==null){
//      new Beam(temp.get(0),temp.get(1));
//    }else{
//      for(Stem s: temp){s.incFlag();}
//  }
}

  private void incrementKey(ActionContainer args){
//    Sys.this.incKey();
  }

  private void decrementKey(ActionContainer args){
    //Syst.this.decKey();
  }


//  private void decKey() {
//    if(initialKey.n>-7){initialKey.n--;}
//    initialKey.glyph=initialKey.n>0?Glyph.SHARP:Glyph.FLAT;
//  }
//
//  private void incKey() {
//    if(initialKey.n<7){initialKey.n++;}
//    initialKey.glyph=initialKey.n>0?Glyph.SHARP:Glyph.FLAT;
//  }

  public Time getTime(int x){return times.getTime(x);}

  public void show(Graphics g){
    int x = page.margins.left;
    g.drawLine(x,yTop(),x,yBot());
    int xKey=x+ UConstants.marginKeyOffset;
//    initialKey.drawOnSys(g,this,xKey);
    showTimes(g);
  }

  private void showTimes(Graphics g) {
    int n=1;
    g.setColor(Color.LIGHT_GRAY);
    for(Time t: times){
      g.drawString("" + n++,t.x,yTop()-15);
    }
    g.setColor(Color.BLACK);
  }
//
  public int yTop(){return staffs.sysTop();}
  public int yBot(){return staffs.get(staffs.size()-1).yBot();}
  public int height(){return yBot()-yTop();}

  public void addNewStaff(int y){
    int off = y-staffs.sysTop();
    RelativeCoordinate staffTop = new RelativeCoordinate(staffs.sysTop,off);
    staffs.add(new Staff(this,staffs.size(),staffTop, new Fmt(5,8)));
    page.updateMaxH();
  }


}
