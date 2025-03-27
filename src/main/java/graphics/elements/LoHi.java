package graphics.elements;

import java.io.Serializable;

/**Keeps a record of the lowest and highest values*/
public class LoHi implements Serializable {

  public int lo,hi;

  /**Constructor*/
  public LoHi(int min, int max){
    lo=min;
    hi=max;
  }

  /**
   * Set high and low to vertical
   * @param v as {@code int}
   */
  public void setBounds(int v){
    lo = v;
    hi = v;
  }

  /**
   * Keeps track of high and low
   * Compares if value is higher than hi or lower than lo and stores if it is
   * @param value new number as {@code int}
   */
  public void adaptBounds(int value){
    if (value < lo) lo= value;
    if(value >hi) hi= value;
  }

  /**@return difference of hi and lo*/
  public int size(){
    return hi-lo>0? hi-lo : 1;
  }
}
