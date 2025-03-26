package graphics.drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class G {

  public static Random RND = new Random();

  /**
   * Completely fill the background with white color
   * @param g graphics object
   */
  public static void fillBack(Graphics g){
    g.setColor(Color.WHITE); g.fillRect(0,0,3000,3000);
  }

  /**
   * Generate a random integer
   * @param max maximum range to generate
   * @return random integer
   */
  public static int rnd(int max){
    return RND.nextInt(max);
  }

  /**
   * Returns a random Color Object
   * @return color
   */
  public static Color rndColor(){
    return new Color(rnd(256),rnd(256),rnd(256));
  }

}
