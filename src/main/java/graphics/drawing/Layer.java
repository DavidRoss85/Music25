package graphics.drawing;

import graphics.interfaces.Show;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

public class Layer extends ArrayList<Show> implements Show {

  public static HashMap<String, Layer> byName = new HashMap<>();
  public static Layer ALL = new Layer("ALL");

  public String name;
  public Layer(String name){
    this.name=name;
    if(!name.equals("ALL")){ALL.add(this);}
    byName.put(name,this);
  }


  @Override
  public void show(Graphics g) {
    for(Show item: this){
      item.show(g);
    }
  }
  public static void nuke(){
    for(Show item: ALL){
      ((Layer)item).clear();
    }
  }
}
