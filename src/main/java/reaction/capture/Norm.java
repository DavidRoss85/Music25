package reaction.capture;


import config.UConstants;
import graphics.elements.Box;
import graphics.elements.PolyLine;
import graphics.elements.Vector;
import java.awt.Graphics;
import java.io.Serializable;

/** Norm extends a Poly Line.
 * Stores the drawn shape in a sub-sampled version.
 * N is the sample size, i.e. the number of points in the point line array.
 * NCS is the drawing area*/
public class Norm extends PolyLine implements Serializable {

  public static final int SAMPLE_SIZE = UConstants.normSampleSize, MAX = UConstants.normCoordMax;
  public static final Box NCS = new Box(0,0,MAX,MAX);

  /**
   * Constructor for Norm
   */
  public Norm(){
    super(SAMPLE_SIZE);
    Buffer buffer = Ink.getBuffer();
    buffer.subSample(this); //subsample the buffer and store in this
    Vector.TRANSFORMER.set(buffer.bBox, NCS);
    this.transform();
  }

  /** Draws the norm at a target box location
   * @param g drawing surface
   * @param vs box dimensions to drawBorders at
   */
  public void drawAt(Graphics g, Box vs){
    Vector.TRANSFORMER.set(NCS, vs);
    for(int i=1;i< SAMPLE_SIZE;i++){
      g.drawLine(
          points[i-1].transformX(),
          points[i-1].transformY(),
          points[i].transformX(),
          points[i].transformY()
      );
    }
  }

  /** Distribution (dist)
   * Takes the square of the distance of each point to the same index point
   * in the array and adds them together. The result is the distribution.
   * @param n a {@code Norm} to compare this one to
   * @return distribution as int
   */
  public int dist(Norm n){
    int res = 0;
    for(int i=0;i< SAMPLE_SIZE;i++){
      int dx = points[i].x - n.points[i].x;
      int dy = points[i].y - n.points[i].y;
      res+=dx*dx+dy*dy;
    }
    return res;
  }

  /**Calls to G.V's blend method for each point
   * Blends this object's points with norm
   * @param norm
   * @param nBlend
   */
  public void blend(Norm norm, int nBlend){
    for(int i=0;i< SAMPLE_SIZE;i++){
      points[i].blend(norm.points[i],nBlend);
    }
  }
}
