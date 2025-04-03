package masses;

import config.UConstants;
import reaction.action.Reaction;
import reaction.recognition.Gesture;

import java.util.ArrayList;

/**
 * Class representing a List of Masses
 *
 * @param <T>
 */
public class MassList <T extends Mass> extends ArrayList<T> {

    public MassList() {}

    public Reaction returnBestBidder(Gesture gesture) {
        Reaction bestBidder = Reaction.NO_REACTION;
        for(T item: this){
            Reaction bidder = item.bidOnGesture(gesture);
            if(bidder.getBid() < bestBidder.getBid()){
                bestBidder = bidder;
            }
        }
        return bestBidder;
    }
}
