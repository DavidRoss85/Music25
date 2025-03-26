package graphics.elements;

//----------------Transform-----------------
/*** Used to transform points and coordinates*/
public class Transform{

  int dx, dy;
  int n, d; //numPoints and d represent a ratio old to new

  /**
   * Scales a rectangle while maintaining coords.
   * Sets dx and dy to new x,y coords
   * @param oldBox old rect
   * @param newBox new rect
   */
  public void set(Box oldBox, Box newBox){
    setScale(oldBox.size.x, oldBox.size.y, newBox.size.x, newBox.size.y);
    dx=setOff(oldBox.loc.x, newBox.loc.x);
    dy=setOff(oldBox.loc.y, newBox.loc.y);
  }
  public void set(BBox oB, Box nBox){
    setScale(oB.h.size(),oB.v.size(), nBox.size.x, nBox.size.y);
    dx=setOff(oB.h.lo, nBox.loc.x);
    dy=setOff(oB.v.lo, nBox.loc.y);
  }

  /**
   * Sets numPoints to height or width of old values, whichever is larger.
   * Sets d to the height or width of new values, whichever is larger.
   * Together numPoints/d can determine scale
   * @param oW old width
   * @param oH old height
   * @param nW new width
   * @param nH new height
   */
  public void setScale(int oW, int oH, int nW, int nH){
    n= Math.max(nW, nH);
    d= Math.max(oW, oH);
  }

  /**
   * When the object is scaled (Multiplied by numPoints/d) the coordinate will
   * increase or decrease accordingly. To keep the coordinate in the same place,
   * after scale, negate it and then add new coordinate
   * @param oX old coordinate
   * @param nX new coordinate
   * @return adjusted coordinate as {@code int}
   */
  public int setOff(int oX, int nX){
    return (-oX * n/d) + nX;
  }
}
