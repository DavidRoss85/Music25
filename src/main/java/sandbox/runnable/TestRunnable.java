package sandbox.runnable;

import java.util.HashMap;


// Define a custom functional interface for methods with three parameters
@FunctionalInterface
interface TriConsumer<T, U, V> {
  void accept(T t, U u, V v);
}

public class TestRunnable{
  private HashMap<String, DoIt<?,?,?>> actions = new HashMap<>();

  public TestRunnable() {
    // Store methods in the HashMap
    actions.put("doSomething", this::doSomething);
    actions.put("printInfo", this::printInfo);
  }

  private <T,U,V>  void doSomething(T str, U num, V value){
    System.out.println("doSomething -> String: " + str + ", Integer: " + num + ", Double: " + value);
  }

  private <T,U,V> void printInfo( T name, U  age, V height) {
    System.out.println("printInfo -> Name: " + name + ", Age: " + age + ", Height: " + height);
  }

  public <T, U, V> void performAction(String actionKey, T arg1, U arg2, V arg3) {
   DoIt<T , U, V> action = (DoIt<T, U, V>) actions.get(actionKey);
    if (action != null) {
      action.accept(arg1, arg2, arg3);
    } else {
      System.out.println("Action not found");
    }
  }

  public static void main(String[] args) {
    TestRunnable myClass = new TestRunnable();

    // Call actions with three arguments
    myClass.<String,Integer,Double>performAction("doSomething", "Hello", 10, 3.14);
    myClass.performAction("printInfo", "David", 25, 5.9);
  }
}
