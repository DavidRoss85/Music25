package masses;

import reaction.action.ActionContainer;
import reaction.action.Reaction;
import reaction.recognition.Gesture;

public interface IMass {

    /**
     * Bid on a gesture (Lower is better)
     * @param gesture Gesture object received from window
     * @return winning reaction object {@code Reaction}
     */
    public Reaction bidOnGesture(Gesture gesture);

    /**
     *
     * @param args
     */
    public void doAction(ActionContainer args);

//    public void reactOnGesture(Gesture gesture);
}
