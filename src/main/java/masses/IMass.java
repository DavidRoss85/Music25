package masses;

import reaction.Action;
import reaction.ActionContainer;
import reaction.recognition.Gesture;

public interface IMass {
    public int bidOnGesture(Gesture gesture);

    public void doAction(ActionContainer args);

    public void reactOnGesture(Gesture gesture);
}
