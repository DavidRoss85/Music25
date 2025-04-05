package masses.page;

import config.UConstants;
import graphics.elements.RelativeCoordinate;
import java.awt.Color;
import java.awt.Graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import masses.Mass;
import masses.MassList;
import masses.sys.Sys;

import reaction.action.ActionContainer;
import reaction.action.Reaction;
import reaction.recognition.Gesture;

public class Page extends Mass {

  private static final String ADD_STAFF_TAG = "ADD_NEW_STAFF";
  private static final String ADD_SYS_TAG = "ADD_NEW_SYS";

  private static HashMap<String, ArrayList<String>> globalShapeToActionsMap = new HashMap<>();
  static {
    //Static to be accessed by all Page objects
    //Add a function here that can load settings from configuration
    globalShapeToActionsMap.put("W-W",new ArrayList<>(Arrays.asList(ADD_STAFF_TAG)));
    globalShapeToActionsMap.put("W-E",new ArrayList<>(Arrays.asList(ADD_SYS_TAG)));

  }


  public Margins margins = new Margins();
  public int sysGap;
  public RelativeCoordinate pageTop;
  public MassList<Sys> sysList = new MassList<>();
  public int maxH=0;



  public Page(int y){
    super("BACK");
    margins.top = y;
    pageTop = new RelativeCoordinate(RelativeCoordinate.ZERO,y);
    RelativeCoordinate sysTop = new RelativeCoordinate(pageTop,0);
    sysList.add(new Sys(this,sysTop));
    updateMaxH();

    setUpActions();
    setUpReactions();
    //Copy global settings. This allows using one universal bid function in Map class:
    this.localShapeToActionsMap = globalShapeToActionsMap;

  }


  private void setUpActions(){
    this.actions.put("ADD_NEW_STAFF",this::addNewStaff);
    this.actions.put("ADD_NEW_SYS",this::callAddNewSys);
  }

  /**
   * Set up reactions for this object.
   * Creates anonymous Reactions and adds them to this item's list of reactions.
   * Gestures/Shapes can be mapped to these later
   */
  private void setUpReactions(){
    this.reactionMap.put(ADD_STAFF_TAG, new Reaction(this,ADD_STAFF_TAG){

      public int makeBid(Gesture gesture) {
        this.setActionDetails(new ActionContainer(this.getActionName(),gesture,null));
        if(sysList.size()!=1){
          bid = UConstants.noBid;
          return bid;
        }
        Sys sys = sysList.getFirst();
        int y = gesture.getBox().yM();
        if(y<sys.yBot()+UConstants.minStaffGap){
          bid = UConstants.noBid;
          return bid;
        }
        bid = 1000;
        return bid;
      }
    });
    this.reactionMap.put(ADD_SYS_TAG, new Reaction(this,ADD_SYS_TAG){
      public int makeBid(Gesture gesture) {
        this.setActionDetails(new ActionContainer(this.getActionName(),gesture,null));
        Sys lastSys = sysList.getLast();
        int y = gesture.getBox().yM();
        if(y<lastSys.yBot()+UConstants.minSysGap){
          bid = UConstants.noBid;
          return bid;
        }
        bid = 1000;
        return bid;
      }
    });
  }




  private void addNewStaff(ActionContainer args){
    int yLoc = args.getBox().yM();
    sysList.getFirst().addNewStaff(yLoc);
  }

  private void callAddNewSys(ActionContainer args){
    int yLoc = args.getBox().yM();
    this.addNewSys(yLoc);
  }

  public void updateMaxH(){
    Sys sys=sysList.getFirst();
    int newH = sys.staffs.getLast().fmt.H;
    if(maxH<newH){maxH=newH;}
  }

  private void addNewSys(int y){ // called from page reaction so one sys EXISTS!!!
    int nSys = sysList.size(), sysHeight = sysList.getFirst().height();
    if(nSys==1){sysGap=y-sysHeight-pageTop.v();}
    RelativeCoordinate sysTop = new RelativeCoordinate(pageTop,nSys*(sysHeight+sysGap));
    sysList.add(new Sys(this,sysTop));

  }
  public void show(Graphics g){g.setColor(Color.BLACK);}

}
