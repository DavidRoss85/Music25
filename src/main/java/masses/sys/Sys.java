package masses.sys;

import graphics.elements.RelativeCoordinate;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import masses.Mass;
import masses.page.Page;

public class Sys extends Mass {
  public Page page;
  public int iSys;
//  public Staff.List staffs; //y coordinate hidden in this list
//  public Time.List times;
//  public Stem.List stems= new Stem.List();
//  public Key initialKey = new Key();

  public Sys(Page page, RelativeCoordinate sysTop){
    super("BACK");
    this.page=page;
    iSys=page.sysList.size();
//    staffs=new Staff.List(sysTop);
//    times=new Time.List(this);
//    if(iSys==0){
//      staffs.add(new Staff(this,0,new RelativeCoordinate(sysTop,0),new Staff.Fmt(5,8)));
//    }else{
//      Sys oldSys = page.sysList.get(0);
//      for(Staff oldStaff: oldSys.staffs){
//        Staff ns = oldStaff.copy(this);
//        this.staffs.add(ns);
//      }
//    }

//    addReaction(new Reaction("E-E") { // Beaming stems
//      public int bid(Gesture g) {
//        int x1 = g.vs.xL(), y1=g.vs.yL(), x2=g.vs.xH(), y2=g.vs.yH();
//        if(stems.fastReject((y1+y2)/2)){return UC.noBid;}
//        ArrayList<Stem> temp = stems.allIntersectors(x1,y1,x2,y2);
////                System.out.println("Sys E-E: " + temp.size());
//        if(temp.size()<2){return UC.noBid;}
//        Beam b=temp.get(0).beam;
////                System.out.println("Sys E-E: Testing All beams, same owner ");
//        for(Stem s: temp){if(s.beam!=b){return UC.noBid;}}
////                System.out.println("Sys E-E: All beams, same owner " + b + " " + b==null);
//        if(b==null && temp.size()!=2){return UC.noBid;}
//        if(b==null && ((temp.get(0).nFlag!=0) || temp.get(1).nFlag!=0)){return UC.noBid;}
//        return 50;
//      }
//      public void act(Gesture g) {
//        int x1 = g.vs.xL(), y1=g.vs.yL(), x2=g.vs.xH(), y2=g.vs.yH();
//        ArrayList<Stem> temp = stems.allIntersectors(x1,y1,x2,y2);
//        Beam b = temp.get(0).beam;
//        if(b==null){
//          new Beam(temp.get(0),temp.get(1));
//        }else{
//          for(Stem s: temp){s.incFlag();}
//        }
//      }
//    });
//
//    addReaction(new Reaction("E-E") { //increment initial key
//      public int bid(Gesture g) {
//        int x=page.margins.left;
//        int x1=g.vs.xL(), x2=g.vs.xH();
//        if(x1>x || x2<x){return UC.noBid;}
//        int y=g.vs.yM();
//        if(y<yTop() || y>yBot()){return UC.noBid;}
//        return Math.abs(x-(x1+x2)/2);
//      }
//      public void act(Gesture g) {
//        Sys.this.incKey();
//      }
//    });
//
//    addReaction(new Reaction("W-W") { //increment initial key
//      public int bid(Gesture g) {
//        int x=page.margins.left;
//        int x1=g.vs.xL(), x2=g.vs.xH();
//        if(x1>x || x2<x){return UC.noBid;}
//        int y=g.vs.yM();
//        if(y<yTop() || y>yBot()){return UC.noBid;}
//        return Math.abs(x-(x1+x2)/2);
//      }
//      public void act(Gesture g) {
//        Sys.this.decKey();
//      }
//    });
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
//
//  public Time getTime(int x){return times.getTime(x);}
//
//  public void show(Graphics g){
//    int x = page.margins.left;
//    g.drawLine(x,yTop(),x,yBot());
//    int xKey=x+UC.marginKeyOffset;
//    initialKey.drawOnSys(g,this,xKey);
//    showTimes(g);
//  }
//
//  private void showTimes(Graphics g) {
//    int n=1;
//    g.setColor(Color.LIGHT_GRAY);
//    for(Time t: times){
//      g.drawString("" + n++,t.x,yTop()-15);
//    }
//    g.setColor(Color.BLACK);
//  }
//
//  public int yTop(){return staffs.sysTop();}
//  public int yBot(){return staffs.get(staffs.size()-1).yBot();}
//  public int height(){return yBot()-yTop();}
//
//  public void addNewStaff(int y){
//    int off = y-staffs.sysTop();
//    RelativeCoordinate staffTop = new RelativeCoordinate(staffs.sysTop,off);
//    staffs.add(new Staff(this,staffs.size(),staffTop, new Staff.Fmt(5,8)));
//    page.updateMaxH();
//  }

  //----------------------List---------------------------------
//  public static class List extends ArrayList<Sys> {}

}
