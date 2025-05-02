package chart;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ChartMeasure {

  private int maxDuration = 1;
  private int totalDuration = 0;
  private int headCount = 0;
  private LinkedList<ChartMeasureItem> masses = new LinkedList<>(); //Holds items with duration

  /** Constructor */

  public ChartMeasure() {}

  /**
   * Helper method.
   * Calculates the total duration of the items in the measure.
   * O(n) time complexity
   *
   * @return the total duration as {@code int}
   */
  private int calculateTotalDuration() {
    int totalDuration = 0;
    for (ChartMeasureItem mass : masses) {
      totalDuration += mass.getDuration();
    }
    return totalDuration;
  }

  /**
   * Helper method.
   * Calculates the number of heads in the measure
   * O(n) time complexity
   *
   * @return number of heads in measure as {@code int}
   */
  private int calculateHeadCount() {
    int headCount = 0;
    for (ChartMeasureItem mass : masses) {
      headCount+= mass.isHead() ? 1 : 0;
    }
    return headCount;
  }

  /**
   * Updates the total duration of the measure
   */
  private void updateTotalDuration() {
    totalDuration = calculateTotalDuration();
  }

  /**
   * Updates the count of heads
   */
  private void updateHeadCount() {
    headCount = calculateHeadCount();
  }

  /**
   * Add an item to the measure at the designated position.
   * An invalid position will default to the end of the list
   *
   * @param item item to add which extends a {@link ChartMeasureItem}
   * @param insertionPoint index to insert at as {@code int}
   */
  public void addItem(ChartMeasureItem item, int insertionPoint) {
    if (insertionPoint >= masses.size() || insertionPoint < 0) {
      masses.add(item);
    }else{
      masses.add(insertionPoint, item);
    }
    if(item.isHead()) {headCount+=1;}
  }

  /**
   * Remove the nth item from the measure.
   * Note: uses 0 indexing
   * If n is out of bounds, method will fail
   *
   * @param n index of item to remove as {@code int}
   */
  public void removeNthItem(int n) {
    if (n < masses.size() && n >= 0) {
      masses.remove(n);
    }
  }

  /**
   * Remove the nth head (note) from the measure.
   * Note: uses 0 indexing
   * If n is out of bounds, method will fail
   *
   * @param n index of head to remove as {@code int}
   */
  public void removeNthHead(int n) {
    if (n < masses.size() && n >= 0) {
      int count = -1;
      for (ChartMeasureItem mass : masses) {
        if (mass.isHead()) {count++;}
        if (count==n) {
          masses.remove(mass);
          break;
        };
      }
    }
  }




  public ChartMeasureItem getItem(int n) {
    return null;
  }

  public int getTotalDuration() {
    return totalDuration;
  }
  public int getHeadCount() {
    return headCount;
  }

  public void setMaxDuration(int maxDuration) {
    this.maxDuration = maxDuration;
  }
  public int getMaxDuration() {
    return maxDuration;
  }
}
