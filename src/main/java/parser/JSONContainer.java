package parser;


import java.util.HashMap;

public class JSONContainer extends HashMap<String,String>{

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();

    for(String key : this.keySet()){
      sb.append(key+"(KEY): "+this.get(key) +"(VALUE)\n");
    }
    return sb.toString();
  }
}
