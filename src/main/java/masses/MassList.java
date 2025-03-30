package masses;

import reaction.recognition.Gesture;

import java.util.ArrayList;

/**
 * Class representing a List of Masses
 *
 * @param <T>
 */
public class MassList <T extends Mass> extends ArrayList<T> {

    public MassList() {}

    public T returnBestBidder(Gesture gesture) {
        int bestBid = 999999;
        T bestBidder = null;
        for(T item: this){
            int bid = item.bidOnGesture(gesture);
            if(bid < bestBid){
                bestBid = bid;
                bestBidder = item;
            }
        }
        return bestBidder;
    }
}
