package graphics.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * Vector point plus width & height.
 * loc is a vector containing the location.
 * size is a vector containing the width and height.
 */
public class Box implements Serializable {
  public Vector loc, size;

  /**
   * Constructor
   * @param x x coord
   * @param y y coord
   * @param w width
   * @param h height
   */
  public Box(int x, int y, int w, int h){
    loc = new Vector(x,y); size = new Vector(w,h);
  }

  /**
   * Draws a filled rectangle onto {@code g} in color {@code c}
   * @param g grahics object to draw onto as {@code Graphics}
   * @param c color to draw in as {@code Color}
   */
  public void fill(Graphics g, Color c){
    g.setColor(c); g.fillRect(loc.x,loc.y,size.x,size.y);
  }

  /**
   * Determines whether a point falls within the Box area
   * @param x x coord
   * @param y y coord
   * @return true if (x, y) falls between (loc, loc+size)
   */
  public boolean hit(int x, int y){
    return loc.x<=x && loc.y<=y && x<=(loc.x+size.x) && y<=(loc.y+size.y);
  }

  /**@return left (x) coord*/
  public int xL(){return loc.x;}
  /**@return right (x+width) coord*/
  public int xH(){return loc.x + size.x;}
  /**@return median x coord (x+width)/2*/
  public int xM(){return (loc.x + loc.x + size.x)/2;}
  /**@return top (y) coord*/
  public int yL(){return loc.y;}
  /**@return bottom (y+height) coord*/
  public int yH(){return loc.y + size.y;}
  /**@return median y coord (y+height)/2*/
  public int yM(){return (loc.y + loc.y + size.y)/2;}

}
