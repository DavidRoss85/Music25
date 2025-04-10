package chart;

import java.util.ArrayList;
import masses.head.Head;
import masses.staff.Staff;

public class ChartStaff extends ChartMass{

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
    return headList.get(index);
  }
}
