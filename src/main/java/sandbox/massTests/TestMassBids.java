package sandbox.massTests;

import graphics.elements.Box;
import masses.IMass;
import masses.Mass;
import masses.TestMass;
import reaction.recognition.Gesture;
import reaction.recognition.Shape;
import state.States;

public class TestMassBids {

    public static void main(String[] args) {

        TestMass mass = new TestMass();

        Mass bestBidder = States.massList.returnBestBidder(
                new Gesture(
                        new Shape("A-A"),
                        new Box(1,1,1,1)
                        )
        );

        System.out.println("Best bidder: " + bestBidder);
    }
}
