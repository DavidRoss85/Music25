package masses.time;

import config.UConstants;
import java.util.ArrayList;
import java.util.Collections;
import masses.sys.Sys;

public class TimeList extends ArrayList<Time> {

  public Sys sys;

  public TimeList(Sys sys){this.sys=sys;}

  public Time getTime(int x){
    if(size()==0){return new Time(sys,x);}
    Time t = getClosestTime(x);
    Time res=(Math.abs(x-t.x)< UConstants.snapTime? t:new Time(sys,x));
    this.sort();
    return res;
  }

  public Time getClosestTime(int x){
    Time res = get(0);
    int bestSoFar = Math.abs(x-res.x);
    for(Time t: this){
      int dist = Math.abs(x-t.x);
      if(dist<bestSoFar){res=t;bestSoFar=dist;}
    }
    return res;
  }

  public void sort(){
    Collections.sort(this);
  }
}
