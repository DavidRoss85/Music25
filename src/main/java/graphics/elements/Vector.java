package graphics.elements;

public class Vector {

  public static Transform TRANSFORM = new Transform();
  public int x,y;

  /**
   * Constructor for Vector
   * @param x x coord
   * @param y y coord
   */
  public Vector(int x, int y){
    this.set(x,y);
  }

  /**Set x and y to provided values
   * @param x as int
   * @param y as int
   */
  public void set(int x, int y){
    this.x = x;
    this.y = y;
  }

  /**
   * Set x and y using a Vector object
   * @param v as {@code Vector}
   */
  public void set(Vector v){
    this.x = v.x;
    this.y = v.y;
  }

  /**
   * Add vectors to create new vector
   * @param v
   */
  public void add(Vector v){
    // vector addition
    this.x += v.x;
    this.y += v.y;
  }

  /**
   * Blends the points together with another using a blend factor
   * Uses a weighted average, with nBlend as the weight
   * @param v point to blend with this
   * @param nBlend weight given to the current object's coordinates {@code int}
   */
  public void blend(Vector v, int nBlend){
    set((nBlend*x + v.x)/(nBlend+1),(nBlend*y + v.y)/(nBlend+1));
  }

  //Transforms:
  /**
   * Set the x and y to transformed coordinates
   * @param v point vector as {@code Vector}
   */
  public void setT(Vector v){
    set(v.transformX(),v.transformY());
  }

  /**
   * Transform x by T's scale
   * @return transformed x coord
   */
  public int transformX(){
    return x* TRANSFORM.n/ TRANSFORM.d+ TRANSFORM.dx;
  }
  /**
   * Transform y by T's scale
   * @return transformed y coord
   */
  public int transformY(){
    return y* TRANSFORM.n/ TRANSFORM.d+ TRANSFORM.dy;
  }

}
