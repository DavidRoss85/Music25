package masses.stem;

import java.util.ArrayList;
import java.util.Collections;
import masses.beam.Beam;

//----------------------------List---------------------------
public class StemList extends ArrayList<Stem> {
  public int yMin=1_000_000, yMax = -1_000_000;

  public void addStem(Stem s){
    add(s);
    if(s.yLo()<yMin){yMin=s.yLo();}
    if(s.yHi()>yMax){yMax=s.yHi();}
  }
  public void sort(){
    Collections.sort(this);}

  public boolean fastReject(int y){return y>yMax||y<yMin;}
  public ArrayList<Stem> allIntersectors(int x1, int y1, int x2, int y2){
    ArrayList<Stem> res = new ArrayList<>();
    for(Stem s: this){
      if(Beam.verticalLineCrossesSegment(s.x(),s.yLo(),s.yHi(),x1,y1,x2,y2)){res.add(s);}
    }
    return res;
  }
}
