import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a poker player
 * 
 * @author patdl
 */
public class Player {
	private String name;
	private int moneyAmount;
	private Card[] holeCards; // the two cards that are dealt to each player
	private Card[] bestFive; // the best five cards the player has between their hole cards and the board
	private HandType handtype;
	private Position position;
	private int bet; // how much they have bet in the current street
	private Action action;
	private boolean isSittingDown; // whether they are participating in the hand
	private boolean isInHand; // whether they have folded yet
	private boolean isAllIn;
	private Random rand = new Random(System.currentTimeMillis());

	public void setAllIn(boolean isAllIn) {
		this.isAllIn = isAllIn;
	}

	/**
	 * Constructor for a Player
	 * 
	 * @param name          - name of player
	 * @param moneyAmount   - amount of money this player starts with
	 * @param isSittingDown - whether they are going to participate in upcoming hand
	 */
	public Player(String name, int moneyAmount) {
		this.name = name;
		this.moneyAmount = moneyAmount;
		this.isSittingDown = true;
		this.isAllIn = false;
		this.setHandType(HandType.HIGHCARD);
	}

	/**
	 * creates an ArrayList of players, must edit method to change the players
	 * 
	 * @return - the ArrayList of players
	 */
	public static ArrayList<Player> createPlayers() {
		Player p1 = new Player("Patrick", 200);
		Player p2 = new Player("Josh", 200);
		Player p3 = new Player("Grant", 200);
		ArrayList<Player> toBeReturned = new ArrayList<Player>(3);
		toBeReturned.add(p1);
		toBeReturned.add(p2);
		toBeReturned.add(p3);
		return toBeReturned;
	}

	/**
	 * player's name
	 */
	@Override
	public String toString() {
		return this.name.toString();
	}

	/**
	 * compares by name
	 */
	@Override
	public boolean equals(Object o) {
		Player other = (Player) o;
		return this.name.equals(other.name);
	}

	/**
	 * makes a bet for a player, returns the amount, reduces players money by bet
	 * size, sets players action type to bet.
	 * 
	 * @param amount - amount to be bet
	 * @return - amount bet
	 */
	public int bet(int amount) {
		this.changeMoney(amount);
		this.action = Action.BET;
		this.bet = amount;
		if (this.moneyAmount == 0) {
			this.isAllIn = true;
			System.out.println(this.name + "is All In");
		}
		System.out.println(this.name + " bet " + amount);
		return amount;
	}

	/**
	 * folds the players cards and takes them out of the hand
	 */
	public void fold() {
		this.action = Action.FOLD;
		this.isInHand = false;
		System.out.println(this.name + " folded");
	}

	/**
	 * checks for the player
	 */
	public void check() {
		this.action = Action.CHECK;
		System.out.println(this.name + " checks");
	}

	/**
	 * calls for the player, if the call puts the player all in, it puts in as many
	 * chips as they have left
	 * 
	 * @param tableBet  - the current bet to match
	 * @param playerBet - how much the player has put in to the street
	 * @return - the amount of money the player put in
	 */
	public int call(int tableBet, int playerBet) {
		int callAmount = tableBet - playerBet;
		if (callAmount > this.getMoneyAmount())
			callAmount = this.getMoneyAmount();
		this.changeMoney(callAmount);
		this.action = Action.CALL;
		this.bet = tableBet;
		if (this.moneyAmount == 0) {
			this.isAllIn = true;
			System.out.println(this.name + "is All In");
		}
		System.out.println(this.name + " calls the bet of " + tableBet + "chips");
		return callAmount;
	}

	/**
	 * raises for the player by specified amount
	 * 
	 * @param amount - the amount to raise with
	 * @param table  - the poker table being played on
	 * @return the amount raised
	 */
	public int raise(int amount, PokerTable table) {
		this.action = Action.RAISE;
		table.setRaisePresent(true);
		this.bet = amount;
		if (this.moneyAmount == 0) {
			this.isAllIn = true;
			System.out.println(this.name + "is All In");
		}
		System.out.println(this.name + " raises to " + amount);
		if (this.getPosition() == Position._BB && table.getStreet() == Street.PREFLOP) // special case if the player
																						// raising is the Big blind,
																						// they've already put their big
																						// blind in
			amount -= 2;
		this.changeMoney(amount);
		return amount;
	}

	/**
	 * reduce the money of a player by a specified amount
	 * 
	 * @param changeAmount - amount player's stack will be changed by
	 */
	public void changeMoney(int changeAmount) {
		this.moneyAmount -= changeAmount;
	}

/**
 *  this function serves as a simple poker bot, 
 *  depending on the current state of the game, the player randomly selects a valid move to execute
 *  the numbers selected to determine the behavior of the player are my best attempt to recreate a
 *  realistic game but this method is still very far from an AI poker bot
 *  
 * @param table - the poker ttable
 * @param lastToAct - the player who could potentially end betting for the round
 */
	public void act(PokerTable table, Player lastToAct) {
		int random = rand.nextInt(100);
		boolean isLastToAct = false;
		if (lastToAct.equals(this))
			isLastToAct = true;
		switch (table.getAction()) {
		case NOACTION:
			if (table.getStreet() == Street.PREFLOP) {
				if (random > 40) { // start the betting
					int bet = table.getBlind().getBigBlind() * 3;
					if (this.moneyAmount < bet)
						bet = this.moneyAmount;
					table.changePot(this.bet(bet));
					table.setAction(Action.BET);
					table.setBet(bet);
				} else { // call the big blind
					int call = table.getBet();
					table.changePot(this.call(call, 0));
					table.setAction(Action.CALL);
				}
			} else {
				if (random > 40) { //check
					this.check();
					table.setAction(Action.CHECK);
				} else { // start the betting
					int bet = table.getBlind().getBigBlind() * 3;
					if (this.moneyAmount < bet)
						bet = this.moneyAmount;
					table.changePot(this.bet(bet));
					table.setAction(Action.BET);
					table.setBet(bet);
				}
			}

			break;
		case CHECK:
			if (random > 35) { //check
				this.check();
				table.setAction(Action.CHECK);
			} else if (!isLastToAct) { //start the betting
				int bet = table.getPot() / 2;
				if (this.moneyAmount < bet)
					bet = this.moneyAmount;
				table.changePot(this.bet(bet));
				table.setAction(Action.BET);
				table.setBet(bet);
			} else { //check
				this.check();
				table.setAction(Action.CHECK);
			}
			break;
		case BET:
			if (random > 25) { //call
				int call = table.getBet();
				table.changePot(this.call(call, this.bet));
				table.setAction(Action.CALL);
			} else if (random < 15) { //raise
				int raise = table.getBet() * 2;
				if (raise > this.moneyAmount) {
					table.changePot(this.call(table.getBet(), this.bet));
					table.setAction(Action.CALL);
				} else
					table.changePot(this.raise(raise, table));
				table.setBet(raise);
				table.setAction(Action.RAISE);
			} else { //fold
				this.fold();
				table.setAction(Action.FOLD);
			}
			break;
		case CALL:
			if (isLastToAct && table.getBet() == 2 && table.getStreet() == Street.PREFLOP) { //forced check
				this.check();
				table.setAction(Action.CHECK);
			} else if (random > 40) { //call
				int call = table.getBet();
				table.changePot(this.call(call, this.getBet()));
			} else if (random < 25) { //raise
				int raise = table.getBet() * 2;
				if (raise > this.moneyAmount) {
					table.changePot(this.call(table.getBet(), this.bet));
					table.setAction(Action.CALL);
				} else {
					table.changePot(this.raise(raise, table));
					table.setAction(Action.RAISE);
					table.setBet(raise);
				}
			} else { //fold
				this.fold();
				table.setAction(Action.FOLD);
			}
			break;
		case RAISE:
			if (random > 38) { //call
				int call = table.getBet();
				table.changePot(this.call(call, this.bet));
				table.setAction(Action.CALL);
			} else {
				this.fold(); //fold
				table.setAction(Action.FOLD);
			}
			break;
		case FOLD:
			if (isLastToAct && table.getBet() == 2 && table.getStreet() == Street.PREFLOP) {
				this.check();
				table.setAction(Action.CHECK); // forced check
			}
			if (random > 38) { //call
				int call = table.getBet(); 
				table.changePot(this.call(call, this.bet));
				table.setAction(Action.CALL);
			} else { //fold
				this.fold();
				table.setAction(Action.FOLD);
			}
			break;
		}
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////// GETTER/SETTER FUNCTIONS BELOW /////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @return - the amount of money/chips the player has
	 */
	public int getMoneyAmount() {
		return moneyAmount;
	}

	/**
	 * @param moneyAmount - amount of money to set
	 */
	public void setMoneyAmount(int moneyAmount) {
		this.moneyAmount = moneyAmount;
	}

	/**
	 * getter for the hole cards of the player (these are the cards the players are
	 * dealt, 2 cards)
	 * 
	 * @return - Card[] of the hole cards
	 */
	public Card[] getHoleCards() {
		return holeCards;
	}

	/**
	 * setter for hole cards of the player (these are the cards the players are
	 * dealt, usually 2 cards)
	 * 
	 * @param holeCards - Card[] that holds holeCards to be set
	 */
	public void setHoleCards(Card[] holeCards) {
		this.holeCards = holeCards;
	}

	/**
	 * getter for isSittingDown, a boolean that signifies if the player will
	 * participate in the next hand
	 * 
	 * @return - whether the player is sitting down
	 */
	public boolean isSittingDown() {
		return isSittingDown;
	}

	/**
	 * setter for isSittingDown, a boolean that signifies if the player will
	 * participate in the next hand
	 * 
	 * @param isSittingDown - whether the player is sitting down
	 */
	public void setSittingDown(boolean isSittingDown) {
		this.isSittingDown = isSittingDown;
	}

	/**
	 * @return player's best five cards
	 */
	public Card[] getBestFive() {
		return bestFive;
	}

	/**
	 * set player's best 5 cards
	 * 
	 * @param bestFive
	 */
	public void setBestFive(Card[] bestFive) {
		this.bestFive = bestFive;
	}

	/**
	 * @return - whether the player is in the current hand
	 */
	public boolean isInHand() {
		return isInHand;
	}

	/**
	 * setter for isInHand, a boolean signifying if player is still in current hand
	 * 
	 * @param isInHand - whether the player is in the current hand
	 */
	public void setIsInHand(boolean isInHand) {
		this.isInHand = isInHand;
	}

	/**
	 * getter for position of player
	 * 
	 * @return - position of player
	 */
	public Position getPosition() {
		return this.position;
	}

	/**
	 * setter for Position of player
	 * 
	 * @param position - position of player
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * @return - the current Action (fold, bet, check, raise, call) of the player
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @param action - the action (fold, bet, call, check, raise) to set for the
	 *               player
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * @return - the handtype (pair, flush, ect) of the player
	 */
	public HandType getHandtype() {
		return handtype;
	}

	/**
	 * @param handtype - handtype (pair, flush, ect) of the player
	 */
	public void setHandType(HandType handtype) {
		this.handtype = handtype;
	}

	/**
	 * @return - how much money the player has put into the current street
	 */
	public int getBet() {
		return bet;
	}

	/**
	 * @param bet - how much money the player has put into the current street
	 */
	public void setBet(int bet) {
		this.bet = bet;
	}
	/**
	 * @return whether the player is all in
	 */
	public boolean isAllIn() {
		return isAllIn;
	}
	
}