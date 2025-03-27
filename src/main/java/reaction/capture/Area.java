package reaction.capture;

public interface Area {

  /**
   * Detect a click on object
   * @param x x coord
   * @param y y coord
   * @return true if in bounds false otherwise
   */
  public boolean hit(int x, int y);

  /**
   * Called when cursor is pressed
   * @param x
   * @param y
   */
  public void cursorDown(int x, int y);

  /**
   * Called when cursor dragged
   * @param x
   * @param y
   */
  public void cursorDrag(int x, int y);

  /**
   * Called when cursor is lifted
   * @param x
   * @param y
   */
  public void cursorUp(int x, int y);
}
