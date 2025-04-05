package masses.accid;

import config.UConstants;
import java.awt.Graphics;
import masses.Mass;
import masses.glyph.Glyph;
import masses.head.Head;
import reaction.action.ActionContainer;

/**
 * Accidentals (Sharps/flats etc)
 */
public class Accid extends Mass {
  public static Glyph[] GLYPHS = {Glyph.DFLAT,Glyph.FLAT,Glyph.NATURAL,Glyph.SHARP,Glyph.DSHARP};
  public static final int FLAT = 1, NATURAL=2, SHARP=3;

  public int iGlyph;
  public Head head;
  public int left; //adjust location slightly left

  public Accid(Head head, int iGlyph){
    super("NOTE");
    this.head=head;
    this.iGlyph=iGlyph;

//    addReaction(new Reaction("DOT") {
//      public int bid(Gesture g) {
//        int x=g.vs.xM(), y=g.vs.yM();
//        int xA=Accid.this.x(), yA=head.y();
//        int dX=Math.abs(x-xA), dY=Math.abs(y-yA), dist=dX+dY;
//        return dist>50?UC.noBid:dist;
//      }
//      public void act(Gesture g) {
//        left+=10;
//        if(left>50){left=0;}
//      }
//    });
//
//    addReaction(new Reaction("S-N") { //delete
//      public int bid(Gesture g) {
//        int x = g.vs.xM(), y=g.vs.yL();
//        int aX = Accid.this.x()+head.w()/2,aY=Accid.this.head.y();
//        int dX=Math.abs(x-aX),dY=Math.abs(y-aY), dist=dX+dY;
//        System.out.println("Accid Delte: dx:" + dX + " dY: " +dY);
//        return dist>50?UC.noBid:dist;
//      }
//      public void act(Gesture g) {
//        Accid.this.deleteAccid();
//      }
//    });

  }

  public void deleteAccid(ActionContainer args) {
    head.accid=null;
    deleteMass(args); //FIX LATER
  }

  public void show(Graphics g){
    GLYPHS[iGlyph].showAt(g,head.staff.fmt.H,x(),head.y());
  }

  public int x(){return head.x()- UConstants.headAccidOffset-left;}
}
