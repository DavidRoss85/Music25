package graphics.elements;

import java.awt.Graphics;
import java.io.Serializable;

/**Bounding box (Keeps track of the maximum bounds ie high and low coords)*/
public class BBox implements Serializable {
  public LoHi h, v; //horizontal and vertical
  public BBox(){h=new LoHi(0,0);v=new LoHi(0,0);}

  /**
   * Sets the initial high and low values for x and y
   * @param x as {@code int}
   * @param y as {@code int}
   */
  public void set(int x, int y){h.set(x);v.set(y);}

  public void add(int x, int y){h.add(x);v.add(y);}
  public void add(Vector v){add(v.x,v.y);}
  public Box getNewVS(){return new Box(h.lo, v.lo,h.hi-h.lo,v.hi-v.lo);}
  public void draw(Graphics g){g.drawRect(h.lo,v.lo,h.hi-h.lo,v.hi-v.lo);}
}
