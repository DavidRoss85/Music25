package masses.page;

import graphics.elements.RelativeCoordinate;
import java.awt.Color;
import java.awt.Graphics;
import masses.Mass;
import masses.MassList;
import masses.sys.Sys;

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

  public void updateMaxH(){
    Sys sys=sysList.get(0);
    int newH = sys.staffs.get(sys.staffs.size()-1).fmt.H;
    if(maxH<newH){maxH=newH;}
  }

  public void addNewSys(int y){ // called from page reaction so one sys EXISTS!!!
    int nSys = sysList.size(), sysHeight = sysList.get(0).height();
    if(nSys==1){sysGap=y-sysHeight-pageTop.v();}
    RelativeCoordinate sysTop = new RelativeCoordinate(pageTop,nSys*(sysHeight+sysGap));
    sysList.add(new Sys(this,sysTop));

  }
  public void show(Graphics g){g.setColor(Color.BLACK);}

}
