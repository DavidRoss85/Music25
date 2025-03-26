package reaction.capture;

import config.UConstants;
import graphics.elements.BBox;
import graphics.elements.PolyLine;
import graphics.interfaces.Area;
import graphics.interfaces.Show;
import java.awt.Graphics;

/** Class to act as buffer for Norms*/
public class Buffer extends PolyLine implements Show, Area {

  public static final int MAX = UConstants.inkBufferMax;
  public int n; //Counter for the number of points
  public BBox bBox = new BBox();

  /** Constructor */
  public Buffer(){
    super(MAX);
  }

  /**
   * Add a point to the array and increment n
   * Keep track of high and low bounds
   * @param x x coord
   * @param y y coord
   */
  public void add(int x, int y){
    if(n<MAX){points[n++].set(x,y);bBox.add(x,y);}
  }

  /**
   * Maps each point in {@code pl} to a point in the buffer. In other words,
   * maintains the general shape of the coordinates, with fewer points.
   * The more points in pl, the higher the sample.
   * @param pl the object to subsample to as {@code G.PL}
   */
  public void subSample(PolyLine pl){
    int k = pl.size();
    for(int i=0;i<k;i++){pl.points[i].set(this.points[i*(n-1)/(k-1)]);}
  }

  /**
   * Clears the buffer*/
  public void clear(){n=0;}

  /** Show */
  public void show(Graphics g){drawN(g,n);/*bBox.draw(g);*/}

  @Override
  public boolean hit(int x, int y) {return true;}

  /** Clears the buffer and initialize bBox on mouse down*/
  @Override
  public void cursorDown(int x, int y){clear();bBox.set(x,y);add(x,y);}
  /** Adds points to the buffer as mouse drags*/
  public void cursorDrag(int x, int y){add(x,y);}
  /** Adds the final point to buffer on mouse up*/
  public void cursorUp(int x, int y){
    add(x,y);
  }
}
