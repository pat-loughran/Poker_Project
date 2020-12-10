import java.util.ArrayList;

public class FindHandsTester {

	/**
	 * tests if a pair is correctly detected
	 * 
	 * @return - whether or not HasPair functions correctly
	 */
	public static boolean testHasPair() {
		// Case 1: pair between board and hole cards
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.TWO, Suit.SPADES) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.ACE, Suit.CLUBS);
			Card c2 = new Card(Value.THREE, Suit.CLUBS);
			Card c3 = new Card(Value.FOUR, Suit.CLUBS);
			Card c4 = new Card(Value.FIVE, Suit.CLUBS);
			Card c5 = new Card(Value.SIX, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasPair = FindHand.hasPair(p1, combined, bestFive, false);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { new Card(Value.ACE, Suit.SPADES), c1, c5, c4, c3 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, inbetween pair not found");
					return false;
				}
			}
		}

		// Case 2: pair in hole cards
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.ACE, Suit.SPADES) };
			Card[] bestFive = new Card[5];
			p1.setHoleCards(testCards);
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.TWO, Suit.CLUBS);
			Card c2 = new Card(Value.THREE, Suit.CLUBS);
			Card c3 = new Card(Value.FOUR, Suit.CLUBS);
			Card c4 = new Card(Value.FIVE, Suit.CLUBS);
			Card c5 = new Card(Value.SIX, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasPair = FindHand.hasPair(p1, combined, bestFive, false);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.ACE, Suit.SPADES), c5, c4, c3 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, holeCards pair not found");
					return false;
				}
			}
		}

		// Case 3: pair within the board
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.TWO, Suit.SPADES) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.THREE, Suit.CLUBS);
			Card c2 = new Card(Value.THREE, Suit.CLUBS);
			Card c3 = new Card(Value.FOUR, Suit.CLUBS);
			Card c4 = new Card(Value.FIVE, Suit.CLUBS);
			Card c5 = new Card(Value.SIX, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasPair = FindHand.hasPair(p1, combined, bestFive, false);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { c1, c2, new Card(Value.ACE, Suit.SPADES), c5, c4 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, pair withing board not found");
					return false;
				}
			}
		}
		// Case 4: no pair present
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.TWO, Suit.SPADES) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.THREE, Suit.CLUBS);
			Card c2 = new Card(Value.FOUR, Suit.CLUBS);
			Card c3 = new Card(Value.FIVE, Suit.CLUBS);
			Card c4 = new Card(Value.SIX, Suit.CLUBS);
			Card c5 = new Card(Value.SEVEN, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasPair = FindHand.hasPair(p1, combined, bestFive, false);
			Card[] actualBestFive = p1.getBestFive();
			if (actualBestFive != null) {
				System.out.println("Error, holeCards pair not found");
				return false;
			}
		}
		System.out.println("Success! hasPair test passed");
		return true;
	}

	public static boolean testHasTwoPair() {
		// Case 1: pair with hole cards pair and pair on board
		{
			Player p1 = new Player("Patrick", 200);
			Card[] bestFive = new Card[5];
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.ACE, Suit.CLUBS) };
			p1.setHoleCards(testCards);
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.TWO, Suit.CLUBS);
			Card c2 = new Card(Value.TWO, Suit.CLUBS);
			Card c3 = new Card(Value.FOUR, Suit.CLUBS);
			Card c4 = new Card(Value.FIVE, Suit.CLUBS);
			Card c5 = new Card(Value.SIX, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			FindHand.hasTwoPair(p1, combined, bestFive);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.ACE, Suit.CLUBS), c1, c2, c5 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, two pair with pocket pair not found");
					return false;
				}
			}
		}
		// Case 2: pair with 1 hole car and pair on board
		{
			Player p1 = new Player("Patrick", 200);
			Card[] bestFive = new Card[5];
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.TWO, Suit.CLUBS) };
			p1.setHoleCards(testCards);
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.ACE, Suit.CLUBS);
			Card c2 = new Card(Value.THREE, Suit.CLUBS);
			Card c3 = new Card(Value.THREE, Suit.CLUBS);
			Card c4 = new Card(Value.FOUR, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			FindHand.hasTwoPair(p1, combined, bestFive);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.ACE, Suit.CLUBS), c2, c3, c5 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, two pair with 1 board pair not found");
					return false;
				}
			}
		}
		// Case 3: two pair on board
		{
			Player p1 = new Player("Patrick", 200);
			Card[] bestFive = new Card[5];
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.TWO, Suit.CLUBS) };
			p1.setHoleCards(testCards);
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.THREE, Suit.CLUBS);
			Card c2 = new Card(Value.THREE, Suit.CLUBS);
			Card c3 = new Card(Value.FOUR, Suit.CLUBS);
			Card c4 = new Card(Value.FOUR, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			FindHand.hasTwoPair(p1, combined, bestFive);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { c3, c4, c2, c1, new Card(Value.ACE, Suit.SPADES) };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, two pair on board not found");
					return false;
				}
			}
		}
		// Case 4: three pair on board
		{
			Player p1 = new Player("Patrick", 200);
			Card[] bestFive = new Card[5];
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.FIVE, Suit.CLUBS) };
			p1.setHoleCards(testCards);
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.THREE, Suit.CLUBS);
			Card c2 = new Card(Value.THREE, Suit.CLUBS);
			Card c3 = new Card(Value.FOUR, Suit.CLUBS);
			Card c4 = new Card(Value.FOUR, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			FindHand.hasTwoPair(p1, combined, bestFive);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { c5, c5, c4, c4, new Card(Value.ACE, Suit.SPADES) };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, two pair with three pair not found");
					return false;
				}
			}
		}
		// Case 5: no Two - Pair
		{
			Player p1 = new Player("Patrick", 200);
			Card[] bestFive = new Card[5];
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.FIVE, Suit.CLUBS) };
			p1.setHoleCards(testCards);
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.TEN, Suit.CLUBS);
			Card c2 = new Card(Value.THREE, Suit.CLUBS);
			Card c3 = new Card(Value.KING, Suit.CLUBS);
			Card c4 = new Card(Value.FOUR, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasTwoPair = FindHand.hasTwoPair(p1, combined, bestFive);
			if (hasTwoPair) {
				System.out.println("Error, two pair found when absent");
				return false;
			}
		}
		System.out.println("Success! hasTwoPair test passed");
		return true;
	}

	public static boolean testHasThreeOfAKind() {
		// Case 1: three of a kind between board and hole pocket pair (Set)
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.ACE, Suit.SPADES) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.ACE, Suit.CLUBS);
			Card c2 = new Card(Value.TWO, Suit.CLUBS);
			Card c3 = new Card(Value.THREE, Suit.CLUBS);
			Card c4 = new Card(Value.FOUR, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasThreeOfAKind = FindHand.hasThreeOfKind(p1, combined, bestFive, false);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { c1, c1, c1, c5, c4 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, three of a kind with pocket pair (set) not found");
					return false;
				}
			}
		}
		// Case 2: three of a kind with board and one hole card (Trips)
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.TWO, Suit.SPADES) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.TWO, Suit.CLUBS);
			Card c2 = new Card(Value.TWO, Suit.CLUBS);
			Card c3 = new Card(Value.THREE, Suit.CLUBS);
			Card c4 = new Card(Value.FOUR, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasThreeOfAKind = FindHand.hasThreeOfKind(p1, combined, bestFive, false);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { c1, c1, c1, new Card(Value.ACE, Suit.SPADES), c5 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, three of a kind with board and one hole card (trips) not found");
					return false;
				}
			}
		}
		// Case 3: three of a kind with board and one hole card (Trips)
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.TWO, Suit.SPADES) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.THREE, Suit.CLUBS);
			Card c2 = new Card(Value.THREE, Suit.CLUBS);
			Card c3 = new Card(Value.THREE, Suit.CLUBS);
			Card c4 = new Card(Value.FOUR, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasThreeOfAKind = FindHand.hasThreeOfKind(p1, combined, bestFive, false);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { c1, c1, c1, new Card(Value.ACE, Suit.SPADES), c5 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, three of a kind with board not found");
					return false;
				}
			}
		}
		// Case 4: no three of a kind
		{
			Player p1 = new Player("Patrick", 200);
			Card[] bestFive = new Card[5];
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.FIVE, Suit.CLUBS) };
			p1.setHoleCards(testCards);
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.TEN, Suit.CLUBS);
			Card c2 = new Card(Value.THREE, Suit.CLUBS);
			Card c3 = new Card(Value.FOUR, Suit.CLUBS);
			Card c4 = new Card(Value.FOUR, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasThreeOfKind = FindHand.hasThreeOfKind(p1, combined, bestFive, false);
			if (hasThreeOfKind) {
				System.out.println("Error, three of a kind found when absent");
				return false;
			}
		}
		System.out.println("Success! hasThreeOfAKind test passed");
		return true;
	}

	public static boolean testHasStraight() {
		// Case 1: 5 card straight
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.TWO, Suit.SPADES),
					new Card(Value.THREE, Suit.SPADES) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.FOUR, Suit.CLUBS);
			Card c2 = new Card(Value.FIVE, Suit.CLUBS);
			Card c3 = new Card(Value.SIX, Suit.CLUBS);
			Card c4 = new Card(Value.TEN, Suit.CLUBS);
			Card c5 = new Card(Value.TEN, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasStraight = FindHand.hasStraight(p1, combined, bestFive);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { c3, c2, c1, new Card(Value.THREE, Suit.SPADES),
					new Card(Value.TWO, Suit.SPADES) };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, failed to find 5 card straight");
					return false;
				}
			}
		}
		// Case 2: 5 card straight, with high ace
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.TWO, Suit.SPADES) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.FOUR, Suit.CLUBS);
			Card c2 = new Card(Value.TEN, Suit.CLUBS);
			Card c3 = new Card(Value.JACK, Suit.CLUBS);
			Card c4 = new Card(Value.QUEEN, Suit.CLUBS);
			Card c5 = new Card(Value.KING, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasStraight = FindHand.hasStraight(p1, combined, bestFive);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { new Card(Value.ACE, Suit.SPADES), c5, c4, c3, c2, };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, failed to find 5 card straight high ace");
					return false;
				}
			}
		}
		// Case 3: 5 card straight low ace
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.TWO, Suit.SPADES) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.THREE, Suit.CLUBS);
			Card c2 = new Card(Value.FOUR, Suit.CLUBS);
			Card c3 = new Card(Value.FIVE, Suit.CLUBS);
			Card c4 = new Card(Value.TEN, Suit.CLUBS);
			Card c5 = new Card(Value.QUEEN, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasStraight = FindHand.hasStraight(p1, combined, bestFive);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { c3, c2, c1, new Card(Value.TWO, Suit.SPADES),
					new Card(Value.ACE, Suit.SPADES) };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, failed to find 5 card straight low ace");
					return false;
				}
			}
		}
		// Case 4: 6 card straight
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.TWO, Suit.SPADES) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.THREE, Suit.CLUBS);
			Card c2 = new Card(Value.FOUR, Suit.CLUBS);
			Card c3 = new Card(Value.FIVE, Suit.CLUBS);
			Card c4 = new Card(Value.SIX, Suit.CLUBS);
			Card c5 = new Card(Value.QUEEN, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasStraight = FindHand.hasStraight(p1, combined, bestFive);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { c4, c3, c2, c1, new Card(Value.TWO, Suit.SPADES) };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, failed to find correct f card striaght in 6 card straight");
					return false;
				}
			}
		}
		// Case 5: 7 card straight
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.KING, Suit.SPADES) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.QUEEN, Suit.CLUBS);
			Card c2 = new Card(Value.JACK, Suit.CLUBS);
			Card c3 = new Card(Value.TEN, Suit.CLUBS);
			Card c4 = new Card(Value.NINE, Suit.CLUBS);
			Card c5 = new Card(Value.EIGHT, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasStraight = FindHand.hasStraight(p1, combined, bestFive);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.KING, Suit.SPADES), c1, c2, c3 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, failed to find correct 5 card straight in 7 card straight");
					return false;
				}
			}
		}
		// Case 6: no straight
		{
			Player p1 = new Player("Patrick", 200);
			Card[] bestFive = new Card[5];
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.JACK, Suit.CLUBS) };
			p1.setHoleCards(testCards);
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.TEN, Suit.CLUBS);
			Card c2 = new Card(Value.THREE, Suit.CLUBS);
			Card c3 = new Card(Value.FOUR, Suit.CLUBS);
			Card c4 = new Card(Value.SIX, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasStraight = FindHand.hasStraight(p1, combined, bestFive);
			if (hasStraight) {
				System.out.println("Error, straight found when absent");
				return false;
			}
		}
		System.out.println("Success! hasStraight test passed");
		return true;
	}

	public static boolean testHasFlush() {
		// case 1: 5 card flush
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.KING, Suit.SPADES) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.FOUR, Suit.CLUBS);
			Card c2 = new Card(Value.JACK, Suit.CLUBS);
			Card c3 = new Card(Value.SEVEN, Suit.CLUBS);
			Card c4 = new Card(Value.NINE, Suit.CLUBS);
			Card c5 = new Card(Value.TWO, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasFlush = FindHand.hasFlush(p1, combined, bestFive, false);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { c2, c4, c3, c1, c5 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, failed to find 5 card flush");
					return false;
				}
			}
		}
		// case 2: 5 card flush in 6 card flush
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.CLUBS),
					new Card(Value.KING, Suit.SPADES) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.FOUR, Suit.CLUBS);
			Card c2 = new Card(Value.JACK, Suit.CLUBS);
			Card c3 = new Card(Value.SEVEN, Suit.CLUBS);
			Card c4 = new Card(Value.NINE, Suit.CLUBS);
			Card c5 = new Card(Value.TWO, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasFlush = FindHand.hasFlush(p1, combined, bestFive, false);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { new Card(Value.ACE, Suit.CLUBS), c2, c4, c3, c1 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, failed to find 5 card flush in 6 card flush");
					return false;
				}
			}
		}
		// case 3: 5 card flush in 7 card flush
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.CLUBS),
					new Card(Value.KING, Suit.CLUBS) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.FOUR, Suit.CLUBS);
			Card c2 = new Card(Value.JACK, Suit.CLUBS);
			Card c3 = new Card(Value.SEVEN, Suit.CLUBS);
			Card c4 = new Card(Value.NINE, Suit.CLUBS);
			Card c5 = new Card(Value.TWO, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasFlush = FindHand.hasFlush(p1, combined, bestFive, false);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { new Card(Value.ACE, Suit.CLUBS),
					new Card(Value.KING, Suit.CLUBS), c2, c4, c3 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, failed to find 5 card flush in 7 card flush");
					return false;
				}
			}
		}
		// Case 4: no flush
		{
			Player p1 = new Player("Patrick", 200);
			Card[] bestFive = new Card[5];
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.FIVE, Suit.HEARTS) };
			p1.setHoleCards(testCards);
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.TEN, Suit.CLUBS);
			Card c2 = new Card(Value.THREE, Suit.CLUBS);
			Card c3 = new Card(Value.FOUR, Suit.DIAMONDS);
			Card c4 = new Card(Value.FOUR, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasFlush = FindHand.hasFlush(p1, combined, bestFive, false);
			if (hasFlush) {
				System.out.println("Error, flush found when absent");
				return false;
			}
		}
		System.out.println("Success! hasFlush test passed");
		return true;
	}

	public static boolean testHasFullHouse() {
		// case 1: regular full house
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.CLUBS),
					new Card(Value.ACE, Suit.CLUBS) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.FOUR, Suit.CLUBS);
			Card c2 = new Card(Value.FOUR, Suit.CLUBS);
			Card c3 = new Card(Value.FOUR, Suit.CLUBS);
			Card c4 = new Card(Value.NINE, Suit.CLUBS);
			Card c5 = new Card(Value.TWO, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasFlush = FindHand.hasFullHouse(p1, combined, bestFive);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { c1, c2, c3, new Card(Value.ACE, Suit.CLUBS),
					new Card(Value.ACE, Suit.CLUBS) };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, failed to find full house");
					return false;
				}
			}
		}
		// Case 2: 6 card full house (2 three of a kind)
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.NINE, Suit.CLUBS),
					new Card(Value.NINE, Suit.CLUBS) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.FOUR, Suit.CLUBS);
			Card c2 = new Card(Value.FOUR, Suit.CLUBS);
			Card c3 = new Card(Value.FOUR, Suit.CLUBS);
			Card c4 = new Card(Value.NINE, Suit.CLUBS);
			Card c5 = new Card(Value.ACE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasFlush = FindHand.hasFullHouse(p1, combined, bestFive);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { c4, c4, c4, c1, c1 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, failed to find full house with 2 three pair");
					return false;
				}
			}
		}
		// Case 3: no full house
		{
			Player p1 = new Player("Patrick", 200);
			Card[] bestFive = new Card[5];
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.FIVE, Suit.HEARTS) };
			p1.setHoleCards(testCards);
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.TEN, Suit.CLUBS);
			Card c2 = new Card(Value.THREE, Suit.CLUBS);
			Card c3 = new Card(Value.FOUR, Suit.DIAMONDS);
			Card c4 = new Card(Value.TEN, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasFullHouse = FindHand.hasFullHouse(p1, combined, bestFive);
			if (hasFullHouse) {
				System.out.println("Error, full house found when absent");
				return false;
			}
		}
		System.out.println("Success! hasFullHouse test passed");
		return true;
	}

	public static boolean testHasQuads() {
		// Case 1: regular quads
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.TWO, Suit.SPADES),
					new Card(Value.TWO, Suit.SPADES) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.TWO, Suit.CLUBS);
			Card c2 = new Card(Value.TWO, Suit.CLUBS);
			Card c3 = new Card(Value.THREE, Suit.CLUBS);
			Card c4 = new Card(Value.FOUR, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasQuads = FindHand.hasQuads(p1, combined, bestFive);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { c1, c1, c1, c1, c5 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, quads not found");
					return false;
				}
			}
		}
		// Case 2: no quads
		{
			Player p1 = new Player("Patrick", 200);
			Card[] bestFive = new Card[5];
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.FIVE, Suit.HEARTS) };
			p1.setHoleCards(testCards);
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.TEN, Suit.CLUBS);
			Card c2 = new Card(Value.THREE, Suit.CLUBS);
			Card c3 = new Card(Value.FOUR, Suit.DIAMONDS);
			Card c4 = new Card(Value.FOUR, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasQuads = FindHand.hasQuads(p1, combined, bestFive);
			if (hasQuads) {
				System.out.println("Error, Quads found when absent");
				return false;
			}
		}
		System.out.println("Success! hasQuads test passed");
		return true;
	}

	public static boolean testHasStraightFlush() {
		// Case 1: 5 card Straight-FLush low ace
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.JACK, Suit.SPADES),
					new Card(Value.JACK, Suit.SPADES) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.ACE, Suit.CLUBS);
			Card c2 = new Card(Value.TWO, Suit.CLUBS);
			Card c3 = new Card(Value.THREE, Suit.CLUBS);
			Card c4 = new Card(Value.FOUR, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasQuads = FindHand.hasStraightFlush(p1, combined, bestFive);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { c5, c4, c3, c2, c1 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, straightFLush not found");
					return false;
				}
			}
		}
		// Case 2: 6 card Straight-FLush low ace
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.JACK, Suit.SPADES),
					new Card(Value.SIX, Suit.CLUBS) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.ACE, Suit.CLUBS);
			Card c2 = new Card(Value.TWO, Suit.CLUBS);
			Card c3 = new Card(Value.THREE, Suit.CLUBS);
			Card c4 = new Card(Value.FOUR, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasQuads = FindHand.hasStraightFlush(p1, combined, bestFive);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { new Card(Value.SIX, Suit.CLUBS), c5, c4, c3, c2 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, correct straightFLush not found in 6");
					return false;
				}
			}
		}
		// Case 3: 7 card Straight-FLush high ace
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.NINE, Suit.CLUBS),
					new Card(Value.EIGHT, Suit.CLUBS) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.ACE, Suit.CLUBS);
			Card c2 = new Card(Value.KING, Suit.CLUBS);
			Card c3 = new Card(Value.QUEEN, Suit.CLUBS);
			Card c4 = new Card(Value.JACK, Suit.CLUBS);
			Card c5 = new Card(Value.TEN, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasQuads = FindHand.hasStraightFlush(p1, combined, bestFive);
			Card[] actualBestFive = p1.getBestFive();
			Card[] correctBestFive = { c1, c2, c3, c4, c5 };
			for (int i = 0; i < 5; i++) {
				if (!actualBestFive[i].equals(correctBestFive[i])) {
					System.out.println("Error, correct straightFLush not found in 7");
					return false;
				}
			}
		}
		// Case 4: straight but no flush
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.JACK, Suit.SPADES),
					new Card(Value.SIX, Suit.DIAMONDS) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.ACE, Suit.CLUBS);
			Card c2 = new Card(Value.TWO, Suit.DIAMONDS);
			Card c3 = new Card(Value.THREE, Suit.CLUBS);
			Card c4 = new Card(Value.FOUR, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasStraightFlush = FindHand.hasStraightFlush(p1, combined, bestFive);
			if (hasStraightFlush) {
				System.out.println("Error, detected a straight flush when only a straight was present");
				return false;
			}
		}
		// Case 5: Flush but no straight
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.JACK, Suit.SPADES),
					new Card(Value.SIX, Suit.DIAMONDS) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.TEN, Suit.CLUBS);
			Card c2 = new Card(Value.TWO, Suit.CLUBS);
			Card c3 = new Card(Value.THREE, Suit.CLUBS);
			Card c4 = new Card(Value.EIGHT, Suit.CLUBS);
			Card c5 = new Card(Value.FIVE, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasStraightFlush = FindHand.hasStraightFlush(p1, combined, bestFive);
			if (hasStraightFlush) {
				System.out.println("Error, detected a straight flush when only a flush was present");
				return false;
			}
		}
		// Case 6: Flush and straight but no straight flush
		{
			Player p1 = new Player("Patrick", 200);
			Card[] testCards = new Card[] { new Card(Value.ACE, Suit.SPADES),
					new Card(Value.TWO, Suit.DIAMONDS) };
			p1.setHoleCards(testCards);
			Card[] bestFive = new Card[5];
			ArrayList<Card> testBoard = new ArrayList<Card>(5);
			Card c1 = new Card(Value.THREE, Suit.CLUBS);
			Card c2 = new Card(Value.FOUR, Suit.CLUBS);
			Card c3 = new Card(Value.FIVE, Suit.CLUBS);
			Card c4 = new Card(Value.TEN, Suit.CLUBS);
			Card c5 = new Card(Value.TEN, Suit.CLUBS);
			testBoard.add(c1); testBoard.add(c2); testBoard.add(c3); testBoard.add(c4); testBoard.add(c5);
			ArrayList<Card> combined = FindHand.combineBoardAndHole(testCards, testBoard);
			boolean hasStraightFlush = FindHand.hasStraightFlush(p1, combined, bestFive);
			if (hasStraightFlush) {
				System.out.println(
						"Error, detected a straight flush when a straight and flush present but not overlapping");
				return false;
			}
		}
		System.out.println("Success! hasStraightFlush test passed");
		return true;
	}

	public static void main(String[] args) {
		testHasPair();
		testHasTwoPair();
		testHasThreeOfAKind();
		testHasStraight();
		testHasFlush();
		testHasFullHouse();
		testHasQuads();
		testHasStraightFlush();

	}

}
