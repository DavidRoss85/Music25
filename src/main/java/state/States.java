package state;

import java.util.ArrayList;
import masses.Mass;
import masses.TestMass;
import reaction.Action;

public class States {

  public static History<Mass> actionHistory = new History<>();

  public static MassList<Mass> massList = new MassList<>();
}
