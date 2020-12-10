import java.util.ArrayList;
import java.util.Random;

/**
 * A Deck of cards of any size
 * 
 * @author patdl
 */
public class Deck {
  private ArrayList<Card> cards;
  private int numCards;
  private boolean isRegularOrder;
  
  /**
   * Constructor for a Deck
   * 
   * @param deckCards - the number of cards in the deck
   * @param regularOrder - true if the deck is built up from lower (2) to higher(Ace),
   * false if reversed
   */
	public Deck(int deckCards, boolean regularOrder) {
		this.numCards = deckCards;
		this.isRegularOrder = regularOrder;
		cards = new ArrayList<Card>(deckCards);

		Suit suitToBeSet;
		int valueToBeSet;
		Card cardToBeAdded;
		int counter = 0;

		for (int i = 0; i < 52; i++) {
			valueToBeSet = i % (numCards / 4);
			if (counter < (numCards / 4))
				suitToBeSet = Suit.CLUBS;
			else if (counter < numCards / 2)
				suitToBeSet = Suit.DIAMONDS;
			else if (counter < (numCards - (numCards / 4)))
				suitToBeSet = Suit.HEARTS;
			else
				suitToBeSet = Suit.SPADES;
			if (regularOrder)
				cardToBeAdded = new Card(Value.values()[valueToBeSet], suitToBeSet);
			else {
				valueToBeSet = Math.abs((numCards / 4) - valueToBeSet) + (12 - numCards / 4);
				cardToBeAdded = new Card(Value.values()[valueToBeSet], suitToBeSet);
			}
			cards.add(cardToBeAdded);
			counter++;
		}
	}
  
  /**
   * adds a Card to the deck its called on
   * 
   * @param toBeAdded - the card to be added
   */
  public void addCard(Card toBeAdded) {
    this.cards.add(toBeAdded);
  }
  
  /**
   * a Fisher-Yates unbiased shuffle 
   */
  public void CasinoWash() {
	int swapIndex;
	for(int i = this.numCards-1; i > 0; i--) {
		swapIndex = (int)Math.floor(Math.random() * (i +1));
		Card currentCard = cards.get(i);
		Card swapCard = cards.get(swapIndex);
		cards.set(i, swapCard);
		cards.set(swapIndex, currentCard);
	}
  }
  
  /**
   * draws one card from the top of the deck and returns it
   * 
   * @return the drawn card
   */
  public Card drawCard() {
    Card toBeRemoved = this.cards.get(0);
    this.cards.remove(0);
    return toBeRemoved;
  }
 
  /**
   * lists a card then a newline
   */
  @Override
  public String toString() {
    String s = "";
    for(int i = 0; i < this.numCards;i++) {
      s+= this.cards.get(i).getValue() + " of " + this.cards.get(i).getSuit().toString() + "\n";
    }
    return s;
  }
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // The rest of this class is just a few methods for fun, not related to Poker project
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * runs the consectuveTest but on a newly created random small deck every iteration
   * 
   * @param total - the amount times the test is run
   * @param deckSize -  the small deck size created each iteration
   * 
   * @return - the percentage the smaller deck has consecutive numbers
   */
  public double testConsecutiveSub(double total, int deckSize) {
    int consecutiveCounter = 0;
    for(int i = 0; i < total; i++) {
      Deck smallerDeck = this.createSmallerDeck(deckSize);
      smallerDeck.CasinoWash();
      if(smallerDeck.containsConsecutiveNum())
        consecutiveCounter++;
    }
    return (consecutiveCounter/(double)total) * 100;
  }
  /**
   * creates a smaller deck of a specified size from a larger deck by picking cards at random.
   * 
   * @param numCardsSelected - the size of the new smaller deck
   * 
   * @return the new smaller deck
   */
  public Deck createSmallerDeck(int numCardsSelected) {
	Random rand = new Random();
    Deck smallerDeck = new Deck(numCardsSelected, this.isRegularOrder);
    smallerDeck.cards.clear();
    for(int i = 0;i<smallerDeck.numCards;i++) {
      int indexOriginal = rand.nextInt((this.numCards));
      Card toBeAdded = this.cards.get(indexOriginal);
      smallerDeck.cards.add(toBeAdded);
    }
   return smallerDeck;
  }
  /**
   * runs containsConsecutiveNum() any amount of time to test via brute force
   * what percentage of shuffled decks on average have a pair of conseecutiveNum cards
   * 
   * @param total - the amount of times the test is performed
   * 
   * @return - the percentage that consecutive cards can be found in a deck
   */
  public double testConsecutiveFull(double total) {
    int consecutiveCounter = 0;
    for(int i = 0; i < total; i++) {
      this.CasinoWash();
      if(this.containsConsecutiveNum())
        consecutiveCounter++;
    }
    return (consecutiveCounter/(double)total) * 100;
  }
  
  /**
   * runs through the deck object its called on to see if there are two consecutive 
   * cards with the same number 
   * 
   * @return whether or not there were consecutive cards found
   */
  public boolean containsConsecutiveNum() {
    Card currentCard;
    Card nextCard;
    for(int i = 0; i < this.numCards- 1;i++) {
      currentCard = this.cards.get(i);
      nextCard = this.cards.get(i+1);
      if(currentCard.getValue().toString().equals(nextCard.getValue().toString()))
        return true;
    }
    return false;
  }
  
  public static void main(String[] args) {

  }
  
}
