package graphics.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 *Poly Line Object is a collection of points
 * that we can use to draw various shapes or lines.
 * Each point is an object of type V which contains an x, and y coord.
 */
public class PolyLine implements Serializable {
  public Vector[] points;

  /**Constructor*/
  public PolyLine(int count){
    points = new Vector[count];
    for(int i=0;i<count;i++){
      points[i]=new Vector(0,0);
    }
  }

  /**
   * Returns number of points in collection
   * @return number of points as {@code int}
   */
  public int size(){
    return points.length;
  }

  /**
   * Connects points with lines to draw a shape/figure (up to n points).
   * @param g graphic target to draw on as {@code Graphics}
   * @param n number of points to connect as {@code int}
   */
  public void drawN(Graphics g, int n){
    for(int i=1;i<n;i++){
      g.drawLine(points[i-1].x,points[i-1].y,points[i].x,points[i].y);
    }
  }
  /**
   * Connects points with lines to draw a shape/figure (up to n points).
   * @param g graphic target to draw on as {@code Graphics}
   * @param n number of points to connect as {@code int}
   * @param color color to draw in as {@code Color}
   */
  public void drawN(Graphics g, int n, Color color){
    Color oldColor = g.getColor();
    g.setColor(color);
    for(int i=1;i<n;i++){
      g.drawLine(points[i-1].x,points[i-1].y,points[i].x,points[i].y);
    }
    g.setColor(oldColor);
  }



  /**
   * Draws points in the collection onto a target (up to n points)
   * @param g graphic target to draw on as {@code Graphics}
   * @param n number of points to draw as {@code int}
   */
  public void drawNDots(Graphics g, int n){
    for(int i=0;i<n;i++){g.drawOval(points[i].x-2,points[i].y-2,4,4);};
  }

  /**
   * Draws points in the collection onto a target (up to n points)
   * @param g graphic target to draw on as {@code Graphics}
   * @param n number of points to draw as {@code int}
   * @param color color to draw in as @{code Color}
   */
  public void drawNDots(Graphics g, int n , Color color){
    Color oldColor = g.getColor();
    g.setColor(color);
    for(int i=0;i<n;i++){
      g.drawOval(points[i].x-2,points[i].y-2,4,4);
    }
    g.setColor(oldColor);
  }

  /**
   * Connects points with lines to draw a shape/figure (All points).
   * @param g graphic target to draw on as {@code Graphics}
   */
  public void draw(Graphics g){drawN(g,points.length);}

  /** Transform all points coords by V's static T object*/
  public void transform(){for(int i=0;i<points.length;i++){points[i].setT(points[i]);}}
}