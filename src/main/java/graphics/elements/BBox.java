package graphics.elements;

import java.awt.Graphics;
import java.io.Serializable;

/**Bounding box (Keeps track of the maximum bounds ie high and low coords)*/
public class BBox implements Serializable {

  private LoHi horizontal, vertical; //horizontal and vertical

  public BBox(){
    horizontal =new LoHi(0,0);
    vertical =new LoHi(0,0);
  }

  public LoHi getHorizontal() {
    return horizontal;
  }

  public LoHi getVertical() {
    return vertical;
  }

  /**
   * Sets the initial high and low values for x and y
   * @param x as {@code int}
   * @param y as {@code int}
   */
  public void setBounds(int x, int y){
    horizontal.setBounds(x);
    vertical.setBounds(y);}

  /**
   *Adapts bounds of bbox to include coordinate (highest/lowest values)
   * @param x
   * @param y
   */
  public void adaptBounds(int x, int y){
    horizontal.adaptBounds(x);
    vertical.adaptBounds(y);
  }
  public void adaptBounds(Vector v){
    adaptBounds(v.x,v.y);
  }

  /**
   * Generates a box based on the highest and lowest vertical and horizontal values
   * @return new rectangular box as {@code Box}
   */
  public Box getNewBox(){
    return new Box(
        horizontal.lo,
        vertical.lo,
        horizontal.hi- horizontal.lo,
        vertical.hi- vertical.lo
    );
  }

  /**
   * Draw box borders based on the highest and lowest vertical and horizontal values
   * @param g graphics target
   */
  public void drawBorders(Graphics g){
    g.drawRect(
        horizontal.lo,
        vertical.lo,
        horizontal.hi- horizontal.lo,
        vertical.hi- vertical.lo
    );
  }
}
