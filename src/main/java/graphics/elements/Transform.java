package graphics.elements;

//----------------Transform-----------------
/*** Used to transform points and coordinates*/
public class Transform{

  private int deltaX, deltaY;
  private int newMax, oldMax; //n and d represent a ratio old to new

  public Transform(){}

  /**Getters:*/
  public int getNewMax() {
    return newMax;
  }

  public int getOldMax() {
    return oldMax;
  }

  public int getDeltaX() {
    return deltaX;
  }

  public int getDeltaY() {
    return deltaY;
  }

  /**
   * Scales a rectangle while maintaining coords.
   * Sets dx and dy to new x,y coords
   * @param oldBox old rect
   * @param newBox new rect
   */
  public void set(Box oldBox, Box newBox){
    setScale(
        oldBox.getWidth(), oldBox.getHeight(),
        newBox.getWidth(), newBox.getHeight()
    );
    deltaX = offSet(oldBox.getX(), newBox.getX());
    deltaY = offSet(oldBox.getY(), newBox.getY());
  }
  /**
   * Scales a rectangle while maintaining coords.
   * Sets dx and dy to new x,y coords
   * @param oldBox old rect
   * @param newBox new rect
   */
  public void set(BBox oldBox, Box newBox){
    setScale(
        oldBox.getHorizontal().size(), oldBox.getVertical().size(),
        newBox.getWidth(), newBox.getHeight()
    );
    deltaX = offSet(oldBox.getHorizontal().lo, newBox.getWidth());
    deltaY = offSet(oldBox.getVertical().lo, newBox.getHeight());
  }

  /**
   * Sets n to old height or old width, whichever is larger.
   * Sets d to the new height or new width, whichever is larger.
   * Together n/d can determine scale
   * @param oW old width
   * @param oH old height
   * @param nW new width
   * @param nH new height
   */
  private void setScale(int oW, int oH, int nW, int nH){
    newMax = Math.max(nW, nH);
    oldMax = Math.max(oW, oH);
  }

  /**
   * When the object is scaled (Multiplied by n/d) the coordinate will
   * increase or decrease accordingly. To keep the coordinate in the same place,
   * after scale, negate it and then add new coordinate
   * @param oX old coordinate
   * @param nX new coordinate
   * @return adjusted coordinate as {@code int}
   */
  private int offSet(int oX, int nX){
    return (-oX * newMax / oldMax) + nX;
  }
}
