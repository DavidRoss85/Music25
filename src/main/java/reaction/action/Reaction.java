package reaction.action;

import config.UConstants;
import graphics.elements.Box;
import masses.IMass;
import reaction.recognition.Gesture;

/**
 * Reactions know who they belong to and how much they're worth
 */
public abstract class Reaction implements React{

  public static final Reaction NO_REACTION = new Reaction(null,null) {
    public int makeBid(Gesture gesture) {
      return UConstants.noBid;
    }
  };

  private final IMass owner;
  private final String actionName;
  private ActionContainer actionDetails;
  protected int bid;


  public Reaction(IMass owner, String actionName) {
    this.owner = owner;
    this.actionName = actionName;
    this.bid = UConstants.noBid;
    this.actionDetails = ActionContainer.EMPTY_ACTION;
  }

  public IMass getOwner() {
    return owner;
  }
  public int getBid() {
    return bid;
  }

  public void setActionDetails(ActionContainer actionDetails) {
    this.actionDetails = actionDetails;
  }

  public String getActionName() {
    return actionName;
  }

  public ActionContainer getActionDetails() {
    return actionDetails;
  }

}
