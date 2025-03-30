package masses.staff;

public class Fmt{

  public static Fmt DEFAULT = new Fmt(5,8);
  public int nLines;
  public int H; // this is half the line space on the staff
  public boolean barContinues = false;

  public Fmt(int nLines, int H){this.nLines=nLines;this.H=H;}

  public void toggleBarContinues(){barContinues=!barContinues;}
}
