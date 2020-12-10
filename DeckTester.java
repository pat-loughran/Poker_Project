import java.util.ArrayList;

public class DeckTester {
	
	public static int[] runSimulation() {
		int[] handTotals = new int[10];
		Player patrick = new Player("Patrick", 200);
		Deck deck = new Deck(52, true);
		ArrayList<Card> board = new ArrayList<Card>(5);
		for(int i = 0; i < 1000000 ; i++) {
			deck.CasinoWash();
			board.add(deck.drawCard());
			board.add(deck.drawCard());
			board.add(deck.drawCard());
			board.add(deck.drawCard());
			board.add(deck.drawCard());
			patrick.setHoleCards(new Card[] {deck.drawCard(), deck.drawCard()});
			FindHand.findBestHand(patrick, board);
			handTotals[patrick.getHandtype().ordinal()]++;
			deck.addCard(board.remove(0));
			deck.addCard(board.remove(0));
			deck.addCard(board.remove(0));
			deck.addCard(board.remove(0));
			deck.addCard(board.remove(0));
			deck.addCard(patrick.getHoleCards()[0]);
			deck.addCard(patrick.getHoleCards()[1]);
		}
		return handTotals;
	}
	
	public static void analyzeData(int[] handTotals, int numRuns) {
		/*
		final double rfExpected = ((double)4324/133784560);
		final double sfExpected = ((double)37260/133784560);
		final double quadsExpected = ((double)224848/133784560);
		final double fhExpected = ((double)3473184/133784560);
		final double flushExpected = ((double)4047644/133784560);
		final double straightExpected = ((double)6180020/133784560);
		final double threeExpected = ((double)6461620/133784560);
		final double twoExpected = ((double)31433400/133784560);
		final double pairExpected = ((double)58627800/133784560);
		final double noneExpected = ((double)23294460/133784560);
		*/
		final int rfExpected = 4324;
		final int sfExpected = 37260;
		final int quadsExpected = 224848;
		final int fhExpected = 3473184;
		final int flushExpected = 4047644;
		final int straightExpected = 618002;
		final int threeExpected = 6461620;
		final int twoExpected = 31433400;
		final int pairExpected = 58627800;
		final int noneExpected = 23294460;
		int[] expected = {noneExpected, pairExpected, twoExpected, threeExpected, straightExpected, flushExpected, fhExpected, quadsExpected, sfExpected, rfExpected};
		
		/*
		double rfActual = ((double)handTotals[9]/numRuns);
		double sfActual = ((double)handTotals[8]/numRuns);
		double quadsActual = ((double)handTotals[7]/numRuns);
		double fhActual = ((double)handTotals[6]/numRuns);
		double flushActual = ((double)handTotals[5]/numRuns);
		double straightActual = ((double)handTotals[4]/numRuns);
		double threeActual = ((double)handTotals[3]/numRuns);
		double twoActual = ((double)handTotals[2]/numRuns);
		double pairActual= ((double)handTotals[1]/numRuns);
		double noneActual = ((double)handTotals[0]/numRuns);
		double[] actual = {noneActual, pairActual, twoActual, threeActual, straightActual, flushActual, fhActual, quadsActual, sfActual, rfActual};
	*/
		int rfActual = (handTotals[9]);
		int sfActual = (handTotals[8]);
		int quadsActual = (handTotals[7]);
		int fhActual = (handTotals[6]);
		int flushActual = (handTotals[5]);
		int straightActual = (handTotals[4]);
		int threeActual = (handTotals[3]);
		int twoActual = (handTotals[2]);
		int pairActual= (handTotals[1]);
		int noneActual = (handTotals[0]);
		int[] actual = {noneActual, pairActual, twoActual, threeActual, straightActual, flushActual, fhActual, quadsActual, sfActual, rfActual};
	for(int i = 0; i < 10; i++) {
		System.out.println("Expected: "+ expected[i] +"    Actual: " + actual[i]);
	}
	}
	public static void main(String[] args) {
		analyzeData(runSimulation(), 100);
	}
}
