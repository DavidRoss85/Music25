package masses.page;

import graphics.elements.RelativeCoordinate;
import java.awt.Color;
import java.awt.Graphics;
import masses.Mass;
import masses.MassList;
import masses.sys.Sys;
import reaction.action.ActionContainer;
import reaction.recognition.Gesture;

public class Page extends Mass {
  public Margins margins = new Margins();
  public int sysGap;
  public RelativeCoordinate pageTop;
  public MassList<Sys> sysList = new MassList<>();
  public int maxH=0;

  public Page(int y){
    super("BACK");
    margins.top=y;
    pageTop=new RelativeCoordinate(RelativeCoordinate.ZERO,y);
    RelativeCoordinate sysTop = new RelativeCoordinate(pageTop,0);
    sysList.add(new Sys(this,sysTop));
    updateMaxH();

    this.actions.put("ADD_NEW_STAFF",this::addNewStaff);
    this.actions.put("ADD_NEW_SYS",this::callAddNewSys);

    this.gestureToActions.put("W-W","ADD_NEW_STAFF");
    this.gestureToActions.put("W-E","ADD_NEW_SYS");

//    addReaction(new Reaction("W-W"){ //This is adding a new staff to first system
//      public int bid(Gesture g){
//        if(sysList.size()!=1){return UC.noBid;}
//        Sys sys = sysList.get(0);
//        int y = g.vs.yM();
//        if(y<sys.yBot()+UC.minStaffGap){return UC.noBid;}
//        return 1000;
//      }
//      public void act(Gesture g){sysList.get(0).addNewStaff(g.vs.yM());}
//    });
//
//    addReaction(new Reaction("W-E") {
//      public int bid(Gesture g) {
//        Sys lastSys = sysList.get(sysList.size()-1);
//        int y = g.vs.yM();
//        if(y<lastSys.yBot()+UC.minSysGap){return UC.noBid;}
//        return 1000;
//      }
//      public void act(Gesture g) {addNewSys(g.vs.yM());}
//    });
  }

  private void addNewStaff(ActionContainer args){
    int yLoc = args.getGesture().getBox().yM();
    sysList.get(0).addNewStaff(yLoc);
  }

  private void callAddNewSys(ActionContainer args){
    int yLoc = args.getGesture().getBox().yM();
    this.addNewSys(yLoc);
  }

  public void updateMaxH(){
    Sys sys=sysList.get(0);
    int newH = sys.staffs.get(sys.staffs.size()-1).fmt.H;
    if(maxH<newH){maxH=newH;}
  }

  private void addNewSys(int y){ // called from page reaction so one sys EXISTS!!!
    int nSys = sysList.size(), sysHeight = sysList.get(0).height();
    if(nSys==1){sysGap=y-sysHeight-pageTop.v();}
    RelativeCoordinate sysTop = new RelativeCoordinate(pageTop,nSys*(sysHeight+sysGap));
    sysList.add(new Sys(this,sysTop));

  }
  public void show(Graphics g){g.setColor(Color.BLACK);}

}
