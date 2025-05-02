package chart;

/**
 * Abstract representation of items in a measure like notes and rests
 * ChartMeasureItems have a duration >= 0.
 */
public abstract class ChartMeasureItem extends ChartMass{
  protected int duration = 0;
  protected boolean isAHeadNote = false;

  public ChartMeasureItem(int duration, boolean isAHeadNote) {
    this.duration = duration;
    this.isAHeadNote = isAHeadNote;
  }

  public boolean isHead() {
    return isAHeadNote;
  }
  public int getDuration() {
    return duration;
  }
  public void setDuration(int duration) {
    this.duration = duration;
  }
}
