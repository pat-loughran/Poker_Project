import java.util.ArrayList;
import java.util.Collections;

/**
 * takes the hole cards and whatever the board is to determine best five cards
 * 
 * @author patdl
 */
public class FindHand {
	/**
	   * driver function to run all of the neccesary findHand functions to determine the players current best 5 cards
	   * 
	   * @param player - current player
	   * @param board - the current board
	   * 
	   * @return - Card[] of the best five cards the player has
	   */
		public static Card[] findBestHand(Player player, ArrayList<Card> board) {
			ArrayList<Card> combined = combineBoardAndHole(player.getHoleCards(), board);
			Card[] bestFive = new Card[5];
			if (hasStraightFlush(player, combined, bestFive))
				return player.getBestFive();
			combined = combineBoardAndHole(player.getHoleCards(), board);
			if (hasQuads(player, combined, bestFive))
				return player.getBestFive();
			combined = combineBoardAndHole(player.getHoleCards(), board);
			if (hasFullHouse(player, combined, bestFive))
				return player.getBestFive();
			combined = combineBoardAndHole(player.getHoleCards(), board);
			if (hasFlush(player, combined, bestFive, false))
				return player.getBestFive();
			combined = combineBoardAndHole(player.getHoleCards(), board);
			if (hasStraight(player, combined, bestFive))
				return player.getBestFive();
			combined = combineBoardAndHole(player.getHoleCards(), board);
			if (hasThreeOfKind(player, combined, bestFive, false))
				return player.getBestFive();
			combined = combineBoardAndHole(player.getHoleCards(), board);
			if (hasTwoPair(player, combined, bestFive))
				return player.getBestFive();
			combined = combineBoardAndHole(player.getHoleCards(), board);
			if (hasPair(player, combined, bestFive, false))
				return player.getBestFive();
			combined = combineBoardAndHole(player.getHoleCards(), board);
			findHighCard(player, combined, bestFive);
			return player.getBestFive();
		}

	/**
	 * called when its preflop and the players only have their hole cards
	 * 
	 * @param player - the player whose cards are being evaluated
	 */
	public static void findHoleCardsHand(Player player) {
		Card[] holeCards = player.getHoleCards();
		if (holeCards[0].equals(holeCards[1]))
			player.setHandType(HandType.PAIR);
		else {
			player.setHandType(HandType.HIGHCARD);
		}
	}
	/**
	 * called when all other findCard functions have returned false, finds best five cards
	 * @param player
	 * @param combined
	 * @param bestFive
	 */
	public static void findHighCard(Player player, ArrayList<Card> combined, Card[] bestFive) {
		Collections.sort(combined, Collections.reverseOrder());
		for(int i = 0; i < 5; i++)
			bestFive[i] = combined.get(i);
		player.setBestFive(bestFive);
		player.setHandType(HandType.HIGHCARD);
		
	}
  /**
   * checks to see if the player has a pair
   * 
   * @param player - the player to check
   * @param combined - the holecards of the player + the current board
   * @param bestFive - the possibly to-be-determined bestFive cards of the player
   * @param helperFunction - whether this method is being used as a helper method for another function
   * 
   * @return - whether the player has a pair
   */
  public static boolean hasPair(Player player, ArrayList<Card> combined, Card[] bestFive,  boolean helperFunction) {
		boolean hasPair = false;
		// sort cards so we can check for cards with same value  as they will be next to each other
		combined.sort(null);
		int combinedSize = combined.size();
		for (int i = 0; i <= combinedSize - 2; i++) {
			if (combined.get(i).equals(combined.get(i + 1))) {
				hasPair = true;
				bestFive[0] = combined.get(i);
				bestFive[1] = combined.get(i + 1);
				combined.remove(i);
				combined.remove(i);
				break;
			}
		}
		if (hasPair && !helperFunction) {
			int cardsNeeded = 3;
			findRestOfBestFive(combined, bestFive, cardsNeeded);
			player.setBestFive(bestFive);
			player.setHandType(HandType.PAIR);
		}
		return hasPair;
	}
  
  /**
   * checks to see if the player has a two-pair
   * 
   * @param player - the player to check
   * @param combined - the holecards of the player + the current board
   * @param bestFive - the possibly to-be-determined bestFive cards of the player
   * 
   * @return - whether the player has two-pair
   */
  public static boolean hasTwoPair(Player player, ArrayList<Card> combined, Card[] bestFive) {
		combined.sort(null);
		int combinedSize = combined.size();
		int pairsCounter = 0;
		// create a list to hold all twopairs present, max of 3 two pairs
		ArrayList<Card> twoPairs = new ArrayList<Card>(6);
		for (int i = 0; i <= combinedSize - 2; i++) {
			if (combined.get(i).equals(combined.get(i + 1))) {
				pairsCounter++;
				twoPairs.add(combined.get(i));
				twoPairs.add(combined.get(i + 1));
			}
		}
		if (pairsCounter < 2)
			return false;
		Collections.sort(twoPairs, Collections.reverseOrder());
		for (int i = 0; i < 4; i++) {
			bestFive[i] = twoPairs.get(i);
			combined.remove(new Card(bestFive[i].getValue(), bestFive[i].getSuit()));
		}
		int cardsNeeded = 1;
		findRestOfBestFive(combined, bestFive, cardsNeeded);
		player.setBestFive(bestFive);
		player.setHandType(HandType.TWOPAIR);
		return true;
	}
  /**
   * checks to see if the player has a three of a kind
   * 
   * @param player - the player to check
   * @param combined - the holecards of the player + the current board
   * @param bestFive - the possibly to-be-determined bestFive cards of the player
   * @param helperFunction - whether or not this function is being used as a helper function
   * @return - whether the player has a three of a kind
   */
  public static boolean hasThreeOfKind(Player player, ArrayList<Card> combined, Card[] bestFive, boolean helperFunction) {
		boolean hasThreeOfAKind = false;
		// sort cards so we can check for cards with same value as they will be next to each other
		combined.sort(null);
		int combinedSize = combined.size();
		for (int i = 0; i <= combinedSize - 3; i++) {
			if (combined.get(i).equals(combined.get(i + 1)) && combined.get(i + 1).equals(combined.get(i + 2))) {
				hasThreeOfAKind = true;
				bestFive[0] = combined.get(i);
				bestFive[1] = combined.get(i + 1);
				bestFive[2] = combined.get(i + 2);
				combined.remove(i);
				combined.remove(i);
				combined.remove(i);
				break;
			}
		}
		if (hasThreeOfAKind && !helperFunction) {
			int cardsNeeded = 2;
			findRestOfBestFive(combined, bestFive, cardsNeeded);
			player.setBestFive(bestFive);
			player.setHandType(HandType.THREEOFAKIND);
		}
		return hasThreeOfAKind;
	}

	/**
	 * checks to see if the player has a straight
	 * 
	 * @param player   - the player to check
	 * @param combined - the holecards of the player + the current board
	 * @param bestFive - the possibly to-be-determined bestFive cards of the player
	 * 
	 * @return - whether the player has a straight
	 */
  public static boolean hasStraight(Player player, ArrayList<Card> combined, Card[] bestFive) {
		boolean hasStraight = false;
		combined.sort(null);
		// create a copy to only hold unique cards
		ArrayList<Card> workingCopy = deepCopy(combined);
		// remove duplicate cards,
		// if we didn't remove duplicates, having a duplicate in a sorted list would
		// prevent a straight from being found
		for (Card card : combined) {
			if (workingCopy.indexOf(card) != workingCopy.lastIndexOf(card))
				workingCopy.remove(card);
		}
		if (workingCopy.size() < 5) // if unique cards left is less than 5, you cannot have a straight
			return false;
		// find if straight is present
		int straightPosition = 0;
		workingCopy.sort(null);
		for(int i = 0; i < workingCopy.size() - 4; i++) {
			int lowestValue = workingCopy.get(i).getValue().ordinal();
			if(lowestValue == workingCopy.get(i+1).getValue().ordinal() -1 && 
			   lowestValue == workingCopy.get(i+2).getValue().ordinal() -2 &&
			   lowestValue == workingCopy.get(i+3).getValue().ordinal() -3 &&
			  (lowestValue == workingCopy.get(i+4).getValue().ordinal() -4 || // this line and line below account for a low ace straight and high ace straight being found
			   ((lowestValue == 0) && workingCopy.get(workingCopy.size() -1).getValue().ordinal() - lowestValue == 12 - lowestValue))) {
			    straightPosition = i;
			    hasStraight = true;
			}
		}	
		if (hasStraight) 
			findBestStraight(straightPosition, workingCopy, bestFive );
	  player.setBestFive(bestFive);
	  player.setHandType(HandType.STRAIGHT);
	  return hasStraight;
  }
  
  /**
   * checks to see if the player has a flush
   * 
   * @param player - the player to check
   * @param combined - the holecards of the player + the current board
   * @param bestFive - the possibly to-be-determined bestFive cards of the player
   * @param helperFunction - whether or not this function is being used as a helper function
   * @return - whether the player has a flush
   */
  public static boolean hasFlush(Player player, ArrayList<Card> combined, Card[] bestFive, boolean helperFunction) {
		ArrayList<Card> flushCards = new ArrayList<Card>(7);
		int spadesCounter, heartsCounter, clubsCounter, diamondsCounter;
		spadesCounter = heartsCounter = clubsCounter = diamondsCounter = 0;
		boolean spadesFlush, heartsFlush, clubsFlush, diamondsFlush;
		spadesFlush = heartsFlush = clubsFlush = diamondsFlush = false;
		//count the num of each suit present
		for (Card card : combined) {
			if (card.getSuit() == Suit.SPADES)
				spadesCounter++;
			else if (card.getSuit() == Suit.HEARTS)
				heartsCounter++;
			else if (card.getSuit() == Suit.CLUBS)
				clubsCounter++;
			else
				diamondsCounter++;
		}
		boolean hasFlush = addFlushCards(spadesCounter, heartsCounter, clubsCounter, diamondsCounter, combined,
				flushCards);
		if (!hasFlush)
			return false;
		Collections.sort(flushCards, Collections.reverseOrder());
		// when this function is used as a helper to hasStraightFlush,
		// we want to be able to access all flush cards which will now be stored in
		// combined
		if (helperFunction) {
			combined.clear();
			for (int i = 0; i < flushCards.size(); i++) {
				combined.add(flushCards.get(i));
			}
		}
		// set best flush cards
		for (int i = 0; i < 5; i++)
			bestFive[i] = flushCards.get(i);
		player.setBestFive(bestFive);
		player.setHandType(HandType.FLUSH);
		return true;
	}

  /**
   * checks to see if the player has a full house
   * 
   * @param player - the player to check
   * @param combined - the holecards of the player + the current board
   * @param bestFive - the possibly to-be-determined bestFive cards of the player
   * @return - whether the player has a full house
   */
  public static boolean hasFullHouse(Player player, ArrayList<Card> combined, Card[] bestFive) {
		ArrayList<Card> combinedCopy = deepCopy(combined);
		ArrayList<Card> fullHouseCards = new ArrayList<Card>(7);
		// check if a three of a kind is present first
		boolean hasThreeOfKind = hasThreeOfKind(player, combined, bestFive, true);
		// if not, full house not possible
		if (!hasThreeOfKind)
			return false;
		fullHouseCards.add(bestFive[0]);
		fullHouseCards.add(bestFive[1]);
		fullHouseCards.add(bestFive[2]);
		// check if in special case of two three of kind present
		boolean hasThreeOfKindAgain = hasThreeOfKind(player, combined, bestFive, true);
		if (hasThreeOfKindAgain) {
			fullHouseCards.add(bestFive[0]);
			fullHouseCards.add(bestFive[1]);
			fullHouseCards.add(bestFive[2]);
			Collections.sort(fullHouseCards, Collections.reverseOrder());
			for (int i = 0; i < 5; i++)
				bestFive[i] = fullHouseCards.get(i);
			player.setBestFive(bestFive);
			return true;
		}
		// if there weren't two three of a kind, check for a pair
		boolean hasPair = hasPair(player, combined, bestFive, true);
		if (!hasPair)
			return false;
		fullHouseCards.add(bestFive[0]);
		fullHouseCards.add(bestFive[1]);
		for (int i = 0; i < 5; i++)
			bestFive[i] = fullHouseCards.get(i);
		player.setBestFive(bestFive);
		player.setHandType(HandType.FULLHOUSE);
		return true;
  }
  
  /**
   * checks to see if the player has quads (four of a kind)
   * 
   * @param player - the player to check
   * @param combined - the holecards of the player + the current board
   * @param bestFive - the possibly to-be-determined bestFive cards of the player
   * @return - whether the player has quads
   */
  public static boolean hasQuads(Player player, ArrayList<Card> combined, Card[] bestFive) {
		boolean hasQuads = false;
		// sort cards so we can check for cards with same value  as they will be next to each other
		combined.sort(null);
		int combinedSize = combined.size();
		for (int i = 0; i <= combinedSize - 4; i++) {
			if (combined.get(i).equals(combined.get(i + 1)) && combined.get(i).equals(combined.get(i + 2))
					&& combined.get(i).equals(combined.get(i + 3))) {
				hasQuads = true;
				bestFive[0] = combined.get(i);
				bestFive[1] = combined.get(i + 1);
				bestFive[2] = combined.get(i + 2);
				bestFive[3] = combined.get(i + 3);
				combined.remove(i);
				combined.remove(i);
				combined.remove(i);
				combined.remove(i);
				break;
			}
		}
		if (hasQuads) {
			int cardsNeeded = 1;
			findRestOfBestFive(combined, bestFive, cardsNeeded);
			player.setBestFive(bestFive);
			player.setHandType(HandType.FOUROFAKIND);
			return true;
		}
		return false;
	}
  /**
   * checks to see if the player has a straight or royal flush
   * 
   * @param player - the player to check
   * @param combined - the holecards of the player + the current board
   * @param bestFive - the possibly to-be-determined bestFive cards of the player
   * @return - whether the player has a straight or royal flush
   */
	public static boolean hasStraightFlush(Player player, ArrayList<Card> combined, Card[] bestFive) {
		ArrayList<Card> deepCopy = deepCopy(combined);
		// check if flush is present first
		boolean hasFlush = hasFlush(player, deepCopy, bestFive, true);
		if (!hasFlush)
			return false;
		// if flush was present, check if straight is present with only the flush cards
		// (deep copy was set to all the flush cards in hasFlush as helperFunction
		// parameter was set to true)
		
		boolean hasStraight = hasStraight(player, deepCopy, bestFive);
		if(hasStraight && player.getBestFive()[0].getValue().equals(Value.ACE))
			player.setHandType(HandType.ROYALFLUSH);
		else if(hasStraight)
			player.setHandType(HandType.STRAIGHTFLUSH);
		return hasStraight;

	}
  
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////// HELPER FUNCTIONS BELOW ////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 /**
	   * combines the hole cards and the board cards into one ArrayList
	   * 
	   * @param holeCard - the holeCard[]
	   * @param board - the board ArrayList
	   * 
	   * @throws - IllegalStateException if the combined board is not a valid size (<2 || >7)
	   * 
	   * @return - the combined ArrayList of board and hole cards
	   */
		public static ArrayList<Card> combineBoardAndHole(Card[] holeCard, ArrayList<Card> board) {
			ArrayList<Card> totalCards = new ArrayList<>(holeCard.length + board.size());
			totalCards.add(holeCard[0]);
			totalCards.add(holeCard[1]);
			for (Card card : board) {
				totalCards.add(card);
			}
			if (totalCards.size() < 2 || totalCards.size() > 7)
				throw new IllegalStateException("Error, combined board and hole cards invalid size");
			return totalCards;
		}
	  /**
	   * determines the rest of the bestFive cards using the values of the remaining cards
	   * 
	   * @param remianingCards - the cards left
	   * @param cardsNeeded- how many more cards needed to get to 5
	   * 
	   * @return - an ArrayList of the rest of the bestFive cards
	   */
		private static void findRestOfBestFive(ArrayList<Card> remainingCards, Card[] bestFive, int cardsNeeded) {
			Collections.sort(remainingCards, Collections.reverseOrder());
			for (int i = 0; i < cardsNeeded; i++) {
				bestFive[i + (5 - cardsNeeded)] = remainingCards.get(i);

			}
		}
	  /**
	   * creates and returns a deep copy of an ArrayList
	   * 
	   * @param list - the list to be copied
	   * @return - deep copy of list
	   */
		private static ArrayList<Card> deepCopy(ArrayList<Card> list) {
			ArrayList<Card> deepCopy = new ArrayList<Card>(list.size());
			for (Card card : list) {
				deepCopy.add(new Card(card.getValue(), card.getSuit()));
			}
			return deepCopy;
		}
	  /**
	   * helper method to determine if an ace (usually high value) needs to be represented as a one
	   * e.g. Ace, 2, 3, 4, 5 is a straight but so is 10, jack, queen, king, ace
	   * 
	   * @param i - the current iteration in calling loop
	   * @param workingCopy - cards to check
	   * @return - whether we are dealing with a lowAceStraight
	   */
	  private static boolean isLowAceStraight(int i, ArrayList<Card> workingCopy) {
		  if(i == 4 && workingCopy.get(0).getValue().ordinal() == 0 
				  && workingCopy.get(1).getValue().ordinal() == 1
				  && workingCopy.get(2).getValue().ordinal() == 2
				  && workingCopy.get(3).getValue().ordinal() == 3
				  && workingCopy.get(4).getValue().ordinal() != 4
				  && workingCopy.get(workingCopy.size()-1).getValue().ordinal() == 12 ) {
			  return true;
		  }
		  return false;
	  }
	  /**
	   * helper function to find highest straight present
	   * @param straightPosition - what position the straight is in in the list
	   * @param workingCopy - list of combined cards
	   * @param bestFive - best five cards of the player
	   */
	  private static void findBestStraight(int straightPosition, ArrayList<Card> workingCopy, Card[]bestFive ) {
			// straight position refers where the consecutive five cards lie in a maximum of
			// seven cards
			// 1 2 3 4 5 x x == case 0
			// x 1 2 3 4 5 x == case 1
			// x x 1 2 3 4 5 == case 2
			switch (straightPosition) {
			case 0:
				for (int i = 4; i >= 0; i--) {
					if (isLowAceStraight(i, workingCopy)) {
						workingCopy.add(0, workingCopy.get(workingCopy.size() - 1));
						workingCopy.remove(workingCopy.size() - 1);
					}
					bestFive[4 - i] = workingCopy.get(i);
				}
				break;
			case 1:
				for (int i = 4; i >= 0; i--) {
					if (isLowAceStraight(i, workingCopy)) {
						workingCopy.add(0, workingCopy.get(workingCopy.size() - 1));
						workingCopy.remove(workingCopy.size() - 1);
					}
					bestFive[4 - i] = workingCopy.get(i + 1);
				}
				break;
			case 2:
				for (int i = 4; i >= 0; i--) {
					if (isLowAceStraight(i, workingCopy)) {
						workingCopy.add(0, workingCopy.get(workingCopy.size() - 1));
						workingCopy.remove(workingCopy.size() - 1);
					}
					bestFive[4 - i] = workingCopy.get(i + 2);
				}
				break;
			}
		}
	    /**
	     * helper function for adding same suit cards to flushCards
	     * @param spadesCounter - numSpades
	     * @param heartsCounter - numHearts
	     * @param clubsCounter - numClubs
	     * @param diamondsCounter - numDiamonds
	     * @param combined - combined cards
	     * @param flushCards - possible flush cards
	     * @return - whether a flush is present
	     */
		private static boolean addFlushCards(int spadesCounter, int heartsCounter, int clubsCounter, int diamondsCounter,
				ArrayList<Card> combined, ArrayList<Card> flushCards) {
			if (spadesCounter > 4) {
				for (Card card : combined) {
					if (card.getSuit() == Suit.SPADES)
						flushCards.add(card);
				}
				return true;
			} else if (heartsCounter > 4) {
				for (Card card : combined) {
					if (card.getSuit() == Suit.HEARTS)
						flushCards.add(card);
				}
				return true;
			} else if (clubsCounter > 4) {
				for (Card card : combined) {
					if (card.getSuit() == Suit.CLUBS)
						flushCards.add(card);
				}
				return true;
			} else if (diamondsCounter > 4) {
				for (Card card : combined) {
					if (card.getSuit() == Suit.DIAMONDS)
						flushCards.add(card);
				}
				return true;
			} else
				return false;
		}
}
