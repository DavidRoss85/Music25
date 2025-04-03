package reaction.action;

import masses.IMass;

/**
 * Reactions know who they belong to and how much they're worth
 */
public class Reaction {

  private IMass owner;
  private int bid;

  public Reaction(IMass owner, int bid) {
    this.owner = owner;
    this.bid = bid;
  }

  public IMass getOwner() {
    return owner;
  }
  public int getBid() {
    return bid;
  }
}
