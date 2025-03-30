package masses;

import reaction.Action;
import reaction.ActionContainer;
import reaction.recognition.Gesture;

public interface IMass {

    /**
     * Bid on a gesture (Lower is better)
     * @param gesture Gesture object received from window
     * @return bid as {@code int}
     */
    public int bidOnGesture(Gesture gesture);

    /**
     *
     * @param args
     */
    public void doAction(ActionContainer args);

    public void reactOnGesture(Gesture gesture);
}
