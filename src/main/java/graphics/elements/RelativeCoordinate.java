package graphics.elements;

/**
 * --------------------Hierarchical Coordinate------------------
 * Used to calculate an object's coordinate in relation from its parent.
 * Every coordinate has a dad (possibly ZERO), and dv represents an offset from dad
 */
public class RelativeCoordinate {
  public static RelativeCoordinate ZERO = new RelativeCoordinate(null,0);
  public RelativeCoordinate dad;
  public int dv; //delta value from dad

  /**
   * Constructor for HC
   * @param dad parent to reference from
   * @param dv offset from parent
   */
  public RelativeCoordinate(RelativeCoordinate dad, int dv){this.dad=dad; this.dv=dv;}

  /**
   * Change the DV
   * @param dv
   */
  public void setDv(int dv){this.dv=dv;}

  /**
   * Returns the objects hierarchical value
   * @return dv if dad is ZERO, else adds dad's v to dv as {@code int}
   */
  public int v(){
    return dad==ZERO? dv : dad.v() + dv;  // the value of the coordinate
  }
}
