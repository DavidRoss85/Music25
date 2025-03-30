package masses.staff;

import graphics.elements.RelativeCoordinate;
import masses.MassList;

public class StaffList extends MassList<Staff> {

  public RelativeCoordinate sysTop;

  public StaffList(RelativeCoordinate sysTop){
    this.sysTop=sysTop;
  }

  public int sysTop(){
    return sysTop.v();
  }
}