package masses;

import reaction.Action;
import reaction.ActionContainer;
import reaction.recognition.Gesture;

public interface IMass {
    public int bidOnGesture(Gesture gesture);

    public Action getAction(String action);

    public void doAction(ActionContainer args);

    public String getActionFromGesture(Gesture gesture);
}
