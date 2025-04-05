package masses.staff;

import config.UConstants;
import graphics.elements.RelativeCoordinate;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import masses.Mass;
import masses.MassList;
import masses.clef.Clef;
import masses.page.Margins;
import masses.page.Page;
import masses.sys.Sys;

public class Staff extends Mass {
  public Sys sys;
  public int iStaff;
  public RelativeCoordinate staffTop;
  public Fmt fmt;
  public MassList<Clef> clefs = null;

  public Staff(Sys sys, int iStaff, RelativeCoordinate staffTop, Fmt fmt){
    super("BACK");
    this.sys=sys;
    this.iStaff=iStaff;
    this.staffTop=staffTop;
    this.fmt=fmt;

//    addReaction(new Reaction("S-S"){
//      @Override
//      public int bid(Gesture g) {
//        Page PAGE = sys.page;
//        int x = g.vs.xM(), y1=g.vs.yL(), y2=g.vs.yH();
//        if(x<PAGE.margins.left || x>PAGE.margins.right+UC.barToMarginSnap){return UC.noBid;}
//        int d = Math.abs(y1-yTop())+Math.abs(y2-yBot());
//        //Bias lets cycleBar S-S outbid this
//        return d<30? d+UC.barToMarginSnap:UC.noBid;
//      }
//      @Override
//      public void act(Gesture g) {
//        new Bar(Staff.this.sys,g.vs.xM());
//      }
//    });
//
//    addReaction(new Reaction("S-S") { //toggle barContinues
//      @Override
//      public int bid(Gesture g) {
//        if(Staff.this.sys.iSys!=0){return UC.noBid;}
//        int y1 = g.vs.yL(), y2 = g.vs.yH();
//        if(iStaff==sys.staffs.size()-1){return UC.noBid;}
//        if(Math.abs(y1-yBot())>20){return UC.noBid;}
//        Staff nextStaff=sys.staffs.get(iStaff+1);
//        if(Math.abs(y2-nextStaff.yTop())>20){return UC.noBid;}
//        return 10;
//      }
//      @Override
//      public void act(Gesture g) {
//        fmt.toggleBarContinues();
//      }
//    });
//
//    addReaction(new Reaction("SW-SW") { //add notes to staff
//      @Override
//      public int bid(Gesture g) {
//        Page PAGE = sys.page;
//        int x = g.vs.xM(), y = g.vs.yM();
//        if(x<PAGE.margins.left || x>PAGE.margins.right){return UC.noBid;}
//        int H = Staff.this.fmt.H, top = Staff.this.yTop()-H, bot=Staff.this.yBot()+H;
//        if(y<top || y>bot){return UC.noBid;}
//        return 10;
//      }
//      @Override
//      public void act(Gesture g) {
//        new Head(Staff.this,g.vs.xM(),g.vs.yM());
//      }
//    });
//
//    addReaction(new Reaction("W-S") { // Add Q rest
//      public int bid(Gesture g) {
//        int x = g.vs.xL(), y=g.vs.yM();
//        if(x<sys.page.margins.left || x>sys.page.margins.right){return UC.noBid;}
//        int H = fmt.H, top=yTop()-H, bot=yBot()+H;
//        if(y<top || y>bot){return UC.noBid;}
//        return 10;
//      }
//      public void act(Gesture g) {
//        Time t = Staff.this.sys.getTime(g.vs.xL());
//        new Rest(Staff.this,t);
//      }
//    });
//
//    addReaction(new Reaction("E-S") { // Add eigth rest
//      public int bid(Gesture g) {
//        int x = g.vs.xL(), y=g.vs.yM();
//        if(x<sys.page.margins.left || x>sys.page.margins.right){return UC.noBid;}
//        int H = fmt.H, top=yTop()-H, bot=yBot()+H;
//        if(y<top || y>bot){return UC.noBid;}
//        return 10;
//      }
//      public void act(Gesture g) {
//        Time t = Staff.this.sys.getTime(g.vs.xL());
//        (new Rest(Staff.this,t)).nFlag=1;
//      }
//    });
//
//    addReaction(new Reaction("SW-SE") { //G clef
//      public int bid(Gesture g) {
//        int dTop = Math.abs(g.vs.yL()-yTop()), dBot = Math.abs(g.vs.yH()-yBot());
//        if(dTop+dBot>60){return UC.noBid;}
//        return dTop+dBot;
//      }
//      public void act(Gesture g) {
//        if(Staff.this.initialClef()==null){
//          setInitialClef(Glyph.CLEF_G);
//        }else{
//          addNewClef(Glyph.CLEF_G,g.vs.xM());
//        }
//      }
//    });
//
//    addReaction(new Reaction("SE-SW") { //G clef
//      public int bid(Gesture g) {
//        int dTop = Math.abs(g.vs.yL()-yTop()), dBot = Math.abs(g.vs.yH()-yBot());
//        if(dTop+dBot>60){return UC.noBid;}
//        return dTop+dBot;
//      }
//      public void act(Gesture g) {
//        if(Staff.this.initialClef()==null){
//          setInitialClef(Glyph.CLEF_F);
//        }else{
//          addNewClef(Glyph.CLEF_F,g.vs.xM());
//        }
//      }
//    });
  }

//  public void setInitialClef(Glyph glyph) {
//    Staff s = this, ps = prevStaff();
//    while(ps!=null){s= ps; ps=s.prevStaff();}
//    s.clefs = new Clef.List();
//    s.clefs.add(new Clef(s,-900,glyph));
//  }
//  public void addNewClef(Glyph glyph, int x){
//    if(clefs ==null){clefs=new Clef.List();}
//    clefs.add(new Clef(this, x,glyph));
//    Collections.sort(clefs);
//  }

  /*** Returns the hierarchical coordinate for the y top
   * @return coordinate as int*/
  public int yTop(){return staffTop.v();}

  public int yOfLine(int line){return yTop()+line* fmt.H;}
  public int lineOfY(int y){
    int H = fmt.H;
    int bias = 100;
    int top = yTop()-H*bias;
    return(y-top+H/2)/H-bias;
  }
  public int yBot(){return yOfLine(2*(fmt.nLines-1));}
  public Staff copy(Sys newSys){
    RelativeCoordinate hc = new RelativeCoordinate(newSys.staffs.sysTop,staffTop.dv);
    return new Staff(newSys,iStaff,hc,fmt);
  }

  public void show(Graphics g){
    Margins m = sys.page.margins;
    int x1 = m.left, x2=m.right, y=yTop(), h=fmt.H*2;
    for(int i=0;i<fmt.nLines;i++){
      g.drawLine(x1,y+i*h,x2,y+i*h);
    }
//    Clef clef = initialClef();
//    int x = sys.page.margins.left + UConstants.initialClefOffset;
//    if(clef!=null){clef.glyph.showAt(g,fmt.H,x,yOfLine(4));}
  }
  public Staff prevStaff(){return sys.iSys==0? null: sys.page.sysList.get(sys.iSys-1).staffs.get(iStaff);}

//  public Clef lastClef(){return clefs ==null? null:clefs.get(clefs.size()-1);}
//  public Clef firstClef(){return clefs ==null? null:clefs.get(0);}
//  public Clef initialClef(){
//    Staff s = this, ps = prevStaff();
//    while(ps!=null && ps.clefs==null){
//      s=ps;
//      ps=s.prevStaff();
//    }
//    return ps==null?s.firstClef():ps.lastClef();
//  }
//
//  public Clef clefAtX(int x) {
//    Clef iClef = initialClef();
//    if (iClef==null){return null;}
//    Clef ret = iClef;
//    for(Clef clef: clefs){
//      if(clef.x<x){ret=clef;}
//    }
//    return ret;
//  }

  //---------------Fmt------------------------
//  public static class Fmt{
//    public static Fmt DEFAULT = new Fmt(5,8);
//    public int nLines;
//    public int H; // this is half the line space on the staff
//    public boolean barContinues = false;
//
//    public Fmt(int nLines, int H){this.nLines=nLines;this.H=H;}
//
//    public void toggleBarContinues(){barContinues=!barContinues;}
//  }

  //-----------------List--------------------------
//  public static class List extends ArrayList<Staff> {
//    public RelativeCoordinate sysTop;
//    public List(RelativeCoordinate sysTop){this.sysTop=sysTop;}
//
//    public int sysTop(){return sysTop.v();}
//  }

}
