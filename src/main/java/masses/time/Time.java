package masses.time;

import config.UConstants;
import graphics.elements.Box;
import java.util.ArrayList;
import java.util.Collections;
import masses.MassList;
import masses.head.Head;
import masses.rest.Rest;
import masses.sys.Sys;
import reaction.action.ActionContainer;

public class Time implements Comparable<Time> {

  public int x;
  public MassList<Head> heads = new MassList<>();
  public MassList<Rest> rests = new MassList<>(); //Added to be included in playback

  public Time(Sys sys, int x){
    this.x=x;
    sys.times.add(this);
  }

  public void unStemHeads(int y1, int y2){
    for(Head h: heads){
      int y=h.y();
      if(y>y1&&y<y2){
        h.unStem(
            new ActionContainer("DELETE_MASS", Box.EMPTY_BOX,null)
        );
      }
    }
  }

  @Override
  public int compareTo(Time t) {
    return x-t.x;
  }


}
