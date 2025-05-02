package chart;

import java.util.ArrayList;
import masses.head.Head;
import masses.staff.Staff;

public class ChartStaff extends ChartMass{

  //Chart Staff will need it's own index marking the "time" marker for each item on the staff.
  // i.e. masses belonging to the Staff will be auto adjusted according to the staff's preset
  // positions.
  // Should this index be an array that holds the item belonging there?




















  public ArrayList<ChartMeasure> measures = new ArrayList<>();
  private ArrayList<Head> headList = new ArrayList<>();
  public Staff staff;

  public ChartStaff(Staff staff) {
    this.staff = staff;
  }

  public ArrayList<Head> getHeadList() {
    return headList;
  }

  public void addHead(Head head) {
    headList.add(head);
  }

  public Head getHead(int index) {
    if(index > headList.size()-1) return null;
    return headList.get(index);
  }
}
