package reaction.recognition;

import config.UConstants;
import graphics.drawing.G;
import graphics.elements.Box;
import graphics.interfaces.Show;
import reaction.capture.Area;
import reaction.capture.Ink;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import reaction.capture.Norm;


/** Stores and retrieves gesture shapes*/
public class Shape implements Serializable{
  public static Shape.Database DB = Shape.Database.load();
  public static Shape DOT = DB.get("DOT");
  public static Trainer TRAINER = new Trainer();

  public static Collection<Shape> LIST = DB.values();
  public Prototype.List prototypes = new Prototype.List();
  private String name;

  public Shape(String name){this.name=name;}

  public static Shape recognize(Ink ink){  //can return null
    if(ink.getBox().getWidth() < UConstants.dotThreshold && ink.getBox().getHeight() < UConstants.dotThreshold){return DOT;}
    Shape bestMatch = null;
    int bestSoFar = UConstants.noMatchDist;
    for(Shape s: LIST){
      int d = s.prototypes.bestDist(ink.getNorm());
      if(d < bestSoFar){
        bestMatch = s;
        bestSoFar = d;
      }
    }
    return bestMatch;
  }

  public String getName(){return name;}

  //------------------Database--------------------------------
  public static class Database extends TreeMap<String, Shape> implements Serializable {
    private static String filename = UConstants.shapeDatabaseFileName;

    private Database(){
      super();
      put("DOT",new Shape("DOT"));
    }

    private Shape forcedGet(String name){
      if(!DB.containsKey(name)){
        DB.put(name, new Shape(name));
      }
      return DB.get(name);
    }

    public void train(String name, Norm norm){if(isLegal(name)){forcedGet(name).prototypes.train(norm);}}

    public static Database load(){
      Database res;

      try{
        System.out.println("Attempting to load " + filename);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        res = (Shape.Database) ois.readObject();
        System.out.println("Successful load. Found: " + res.keySet());
        ois.close();
      }catch(Exception e){
        System.out.println("Load fail: ");
        System.out.println(e);
        res = new Database();
      }
      return res;
    }

    public static void save(){
      try{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(DB);
        System.out.println("Saved " + filename);
        oos.close();
      }catch(Exception e){
        System.out.println("Failed saving " + filename);
        System.out.println(e);
      }
    }

    public boolean isKnown(String name){return containsKey(name);}

    public boolean isUnknown(String name){return !containsKey(name);}

    public boolean isLegal(String name){return !name.equals("") && !name.equals("DOT");}
  }

  //------------------Prototype-------------------------------
  public static class Prototype extends Norm implements Serializable{
    public int nBlend = 0;

    /** Prototype will blend the Norm with itself*/
    public void blend(Norm norm){
      blend(norm,nBlend);
      nBlend++;
    }

    //------------------List-----------------------------------
    public static class List extends ArrayList<Prototype> implements Show, Serializable{

      public static Prototype bestMatch; // set by side effect of bestDist()

      public int bestDist(Norm norm){
        bestMatch = null;
        int bestSofFar = UConstants.noMatchDist;
        for(Prototype p: this){
          int d=p.dist(norm);
          if(d<bestSofFar){
            bestMatch=p;
            bestSofFar=d;
          }
        }
        return bestSofFar;
      }

      public void train(Norm norm){
        if (bestDist(norm) < UConstants.noMatchDist){
          bestMatch.blend(norm);
        }else{
          add(new Shape.Prototype());
        }
      }

      private static int m=10, w=60, showBoxHeight=m+w;
      private Box showBox = new Box(m,m,w,w);

      public void show(Graphics g){
        g.setColor(Color.ORANGE);
        for(int i=0;i<size();i++){
          Prototype p = get(i);
          int x = m + i * (m + w);
          showBox.getLocation().set(x,m);
          p.drawAt(g,showBox);
          g.drawString("" + p.nBlend,x,20);
        }
      }
    }
  }

  //---------------------Trainer----------------------
  public static class Trainer implements Show, Area {
    public static final String UNKNOWN = " <- This name unknown";
    public static final String ILLEGAL = " <- This name NOT legal";
    public static final String KNOWN = " <- This name known";
    public static Shape.Prototype.List pList = new Shape.Prototype.List();

    public static String curName = "";
    public static String curState = ILLEGAL;


    private Trainer(){} //Singleton (private)

    public void setState(){
      curState = !Shape.DB.isLegal(curName)? ILLEGAL: UNKNOWN;
      if(curState==UNKNOWN){
        if(Shape.DB.isKnown(curName)){
          curState=KNOWN;
          pList = Shape.DB.get(curName).prototypes;
        }else{
          pList=null;
        }
      }
    }
    private boolean removePrototype(int x, int y){
      int H = Prototype.List.showBoxHeight;
      if(y<H){
        int iBox = x/H;
        Prototype.List pList = TRAINER.pList;
        if(pList!=null && iBox<pList.size()){pList.remove(iBox);}
        Ink.getBuffer().clear();
        return true;
      }
      return false;
    }

    public void show(Graphics g){
      G.fillBack(g);
      g.setColor(Color.BLACK);
      g.drawString(curName,600,30);
      g.drawString(curState,700,30);
      g.setColor(Color.RED);
      Ink.getBuffer().show(g);
      if(pList!=null){
        pList.show(g);
      }
    }

    public boolean hit(int x, int y){return true;}
    public void cursorDown(int x, int y){Ink.getBuffer().cursorDown(x,y);}
    public void cursorDrag(int x, int y){Ink.getBuffer().cursorDrag(x,y);}
    public void cursorUp(int x, int y){
      if(removePrototype(x,y)){return;}
      Ink.getBuffer().cursorUp(x,y);
      Ink ink = new Ink();
      Shape.DB.train(curName,ink.getNorm());
      setState();
    }
    public void keyTyped(KeyEvent ke){
      char c = ke.getKeyChar();
      System.out.println("Typed: "+ c);
      curName = (c==' ' || c == 0x0D || c == 0x0A)? "": curName + c;
      if(c==0x0D || c == 0x0A){
        Shape.Database.save();
      }
      setState();
    }
  }
}