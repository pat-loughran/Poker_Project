/**
 * Playing Card Class
 * @author patdl
 *
 */
public class Card implements Comparable<Card> {
  private Value value;
  private Suit suit;
  private Player player;
  
  /**
   * constructor for a card object
   * @param value - value of card (2 - Ace)
   * @param suit - suit of card (clubs, diamonds, spades, Hearts)
   */
  public Card(Value value, Suit suit) {
    this.value = value;
    this.suit = suit;
  }
  /**
   * @return - value from 2 - Ace
   */
  public Value getValue() {
    return this.value;
  }
  /**
   * @return - suit of card (clubs, diamonds, spades, Hearts)
   */
  public Suit getSuit() {
    return this.suit;
  }
  /**
   * @param player - the player that holds the card
   */
  public void setPlayer(Player player) {
    this.player = player;
  }
  /**
   * e.g. ACE of SPADES
   */
  @Override
  public String toString() {
    String s = this.getValue() + " of " + this.getSuit().toString();
    return s;
  }
  /**
   * compares two cards via their value
   */
  @Override
  public boolean equals(Object o) {
    Card comp = (Card)o;
    if (this.value == comp.getValue())
      return true;
    return false;
    
  }
  /**
   * compareTo uses card value to compare cards
   */
  @Override
  public int compareTo(Card o) {
    if(this.getValue().ordinal()> o.getValue().ordinal())
      return 1;
    else if(this.getValue().ordinal()< o.getValue().ordinal())
      return -1;
    else {
      return 0;
    }
  }
}
