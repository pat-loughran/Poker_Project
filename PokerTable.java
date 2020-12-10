import java.util.ArrayList;

/**
 * Backend of poker simulator that runs the table
 * @author patdl
 */
public class PokerTable {
  private int numPeople; //total people at table
  private int numPeoplePlaying; //all people that are sitting down
  private Deck deck;
  private Blinds blind;
  private ArrayList<Card> board; // the community board of 3-5 cards that all the players share
  private int pot;
  private ArrayList<Player> players;
  private Player currentToAct;
  private Street street;
  private ArrayList<Position> positions;
  private Position currentPosition;
  private Action action; // most recent action of the table
  private int bet; // current bet to match on the table
  private boolean raisePresent;
/**
 * constructor for poker table
 */
  public PokerTable(int numPeople, Deck deck, Blinds blind, ArrayList<Player> players) {
    this.numPeople = numPeople;
    this.deck = deck;
    this.deck.CasinoWash();
    this.blind = blind;
    this.pot = 0;
    this.bet = 2;
    this.players = players;
    this.setStreet(Street.PREFLOP);
    this.setAction(Action.NOACTION);
    this.positions = new ArrayList<Position>();
    this.board = new ArrayList<Card>(5);
    this.raisePresent = false;
  }
/**
 * deals cards to the players, determines if they have a pair, and sets their positions
 */
public void dealCards() {
    for (int i = 0; i < players.size(); i++) {
      if (players.get(i).isSittingDown()) {
        Card card1 = this.deck.drawCard();
        Card card2 = this.deck.drawCard();
        Card[] cardArr = {card1, card2};
        players.get(i).setHoleCards(cardArr);
        numPeoplePlaying++;
        positions.add(Position.values()[i]);
        players.get(i).setIsInHand(true);
        players.get(i).setPosition(Position.values()[i]);
        players.get(i).setAction(Action.NOACTION);
        FindHand.findHoleCardsHand(players.get(i));
      }
    }
    this.currentToAct = this.findPlayerUsingPosition(Position.UTG);
    this.currentPosition = Position.UTG;
  }
  /**
   * bet the small and big blinds
   */
  public void betBlinds() {
    Player sb = findPlayerUsingPosition(Position._SB);
    Player bb = findPlayerUsingPosition(Position._BB);
    int sbAmount = this.blind.getBlindValues()[0];
    int bbAmount = this.blind.getBlindValues()[1];
    this.pot += sbAmount;
    this.pot += bbAmount;
    sb.changeMoney(sbAmount);
    sb.setBet(sbAmount);
    bb.changeMoney(bbAmount);
    bb.setBet(bbAmount);
  }
/**
 * deal the flop, 3 community cards
 */
  public void dealFlop() {
    for (int i = 0; i < 3; i++) {
      Card boardCard = deck.drawCard();
      board.add(boardCard);
    }
    this.setStreet(Street.FLOP);
  }
/**
 * deal the turn, one more community card
 */
  public void dealTurn() {
    Card boardCard = deck.drawCard();
    board.add(boardCard);
    this.setStreet(Street.TURN);
  }
/**
 * deal the river, last community card
 */
  public void dealRiver() {
    Card boardCard = deck.drawCard();
    board.add(boardCard);
    this.setStreet(Street.RIVER);
  }
  /**
   * sets the hand type of each player using FindHand class
   */
  public void setHands() {
    for(int i = 0; i < this.numPeople;i++) {
      if(players.get(i).isInHand()) {
        FindHand.findBestHand(players.get(i), this.board);
      }
    }
  }

  public Player findPlayerUsingPosition(Position pos) {
    for(int i = 0; i < this.players.size();i++) {
      if(this.players.get(i).getPosition() != null)
        if(this.players.get(i).getPosition().equals(pos))
          return this.players.get(i);
    }
    throw new IllegalStateException("Could not find Player with that position");
  }
  /**
   * moves who is up to act based on who is in the hand and the positions of the players
   */
  public void moveToAct() {
    Position currentPosition = this.currentToAct.getPosition();
    int positionIndex = this.positions.indexOf(currentPosition);
    Position nextPosition;
    boolean nextFound = false;
    while(!nextFound) {
      if(positionIndex != this.positions.size()-1)
        nextPosition = this.positions.get(positionIndex +1);
      else {
        nextPosition = this.positions.get(0);
        positionIndex = -1;
      }
      //find the player with next postion, check if they're in the hand
      Player possibleNext = findPlayerUsingPosition(nextPosition);
      if(possibleNext.isInHand()) {
         this.currentToAct = possibleNext;
         this.currentPosition = nextPosition;
         nextFound = true;
      }
      else {
        positionIndex++;
      }
    }
  }
  
/**
 * helper method to determine if the player's hand is better than the current winner's
 * @return 1 if true, -1 if false
 */
private int betterThanCurrentWinner(Player currentWinner, Player other) {
	  int compareTo = currentWinner.getHandtype().compareTo(other.getHandtype());
	  if(compareTo > 0)
		  return -1;
	  if(compareTo < 0) 
		  return 1;
	  else {
		  for(int i = 0; i < 5; i++) {
			  if(currentWinner.getBestFive()[i].compareTo(other.getBestFive()[i]) < 0)
				  return 1;
			  else if(currentWinner.getBestFive()[i].compareTo(other.getBestFive()[i]) > 0)
				  return -1;
		  }
	  }
	  return 0;
		  
  }
/**
 * determine winner of the round
 * @return
 */
  public Player determineWinner() {
	  if(this.street != Street.PREFLOP) { 
	  Player winner = this.currentToAct;
	  for(Player player : players) {
		  if(player.isInHand() && betterThanCurrentWinner(winner, player) > 0)
			  winner = player;
	  }
	  return winner;
	  }
	  else { //if pre flop, compare hole cards
		  
		  Player winner = this.currentToAct;
		  for(Player player : players) {
			  if(player.getHandtype() == HandType.PAIR && winner.getHandtype() == HandType.HIGHCARD) {
				  winner = player;
				  continue;
			  }
			  else if (player.getHandtype() == HandType.HIGHCARD && winner.getHandtype() == HandType.PAIR) {
				  continue;
			  }
			  else if (player.getHandtype() == HandType.PAIR && winner.getHandtype() == HandType.PAIR) {
				  if(player.getHoleCards()[0].getValue().ordinal() > winner.getHoleCards()[0].getValue().ordinal())
					  winner = player;
				  continue;
			  }
			  else {
			  Card[] playerCards = player.getHoleCards();
			  Card[] winnerCards = winner.getHoleCards();
			  int playerBestCard = (playerCards[0].getValue().ordinal() > playerCards[1].getValue().ordinal()) ? playerCards[0].getValue().ordinal() : playerCards[1].getValue().ordinal();
			  int winnerBestCard = (winnerCards[0].getValue().ordinal() > winnerCards[1].getValue().ordinal()) ? winnerCards[0].getValue().ordinal() : winnerCards[1].getValue().ordinal();
			  int playerWorstCard = (playerCards[0].getValue().ordinal() < playerCards[1].getValue().ordinal()) ? playerCards[0].getValue().ordinal() : playerCards[1].getValue().ordinal();
			  int winnerWorstCard = (winnerCards[0].getValue().ordinal() < winnerCards[1].getValue().ordinal()) ? winnerCards[0].getValue().ordinal() : winnerCards[1].getValue().ordinal();
			  if(playerBestCard > winnerBestCard)
				  winner = player;
			  else if (playerBestCard == winnerBestCard) {
				  if(playerWorstCard > winnerWorstCard)
					  winner = player;
				  
			  }
			  }
		  }
		  return winner;
	  }
  }
  /**
   * 
   * @param amountToAdd - how much to increase the pot by
   */
  public void changePot(int amountToAdd) {
    this.pot+= amountToAdd;
  }
/**
 * rotate positions clockwise
 */
  public void changePositions() {
	  players.add(players.get(0));
	  players.remove(0);
  }
  /**
   * put cards back in deck, clear the board, reset all fields, shuffle the deck
   */
  public void roundOver() {

    for (int i = 0; i < this.board.size(); i++) {
      this.deck.addCard(this.board.get(i));
    }
    int boardSize = this.board.size();
    for (int i = 0; i < boardSize; i++) {
      this.board.remove(0);
    }
    for (int i = 0; i < this.numPeople; i++) {
      for (int j = 0; j < 2; j++) {
        if (this.players.get(i).isSittingDown()) {
          this.deck.addCard(this.players.get(i).getHoleCards()[j]);
        }
      }
      this.players.get(i).setHoleCards(null);
    }
    this.numPeoplePlaying = 0;
    this.setStreet(Street.PREFLOP);
    this.positions.clear();
    this.pot = 0;
    this.deck.CasinoWash();
    
  }
  /**
   * run a new round
   */
public void newRound() {
    this.dealCards();
  }
  /**
   * @param player - is added to the poker table
   */
  public void addPlayer(Player player) {
	  this.players.add(player);
	  this.numPeople++;
  }
/**
 * helper method to try and maintain the toStriing of this class
 * @return
 */
private int numCharactersInBoard() {
	int numChar = 0;
	for(Card card: this.getBoard()) {
		if(card != null)
			numChar += card.toString().length();
	}
	return numChar;
}
/**
 * my best attempt at represting a poker table with just text
 */
@Override
public String toString() {
	int numChar = numCharactersInBoard();
	String boardString = this.board.toString();
	for(int i = 0; i < 77 -numChar; i++)
		boardString+=" ";
	String str =

	"                                //===========================================================================\\\\\n" +	
	"                               //                                   The Board                                 \\\\\n"+
	"                              //" + boardString + "\\\\\n" +
	"                             //                                  Pot: " + this.pot + "                                         \\\\\n" +
	"                            ||                        Current Street: " + this.getStreet() +"                                    ||\n" +
	"                            ||                             Player Up: " + this.getCurrentToAct().toString() + "                                       ||\n" +
	"                            ||                 Players:  " + players.get(0).toString() +"     "+ players.get(1).toString() +"      "+ players.get(2).toString() +"      "+ players.get(3).toString() +"                    ||\n" +                                                                          
	"                            ||                 Chips:      " + players.get(0).getMoneyAmount() +"        "+ players.get(1).getMoneyAmount() +"       "+ players.get(2).getMoneyAmount() +"       "+ players.get(3).getMoneyAmount() +"                    ||\n"+
	"                             \\\\                Position:   " + players.get(0).getPosition()+"        "+ players.get(1).getPosition()+"       "+ players.get(2).getPosition()+"       "+ players.get(3).getPosition()+ "                   //\n" +
	"                              \\\\               Action:     " + players.get(0).getAction()+ "       "+players.get(1).getAction()+ "      "+players.get(2).getAction()+ "      "+players.get(3).getAction() +"  //\n" +
	"                               \\\\              In Hand:    " + players.get(0).isInHand() + "       " + players.get(1).isInHand()+ "      " + players.get(2).isInHand()+"      " + players.get(3).isInHand()+"                //\n" +
	"                                \\\\===========================================================================// ";
return str;
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////// GETTER/SETTER FUNCTIONS BELOW /////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public Action getAction() {
	return action;
}

public void setAction(Action action) {
	this.action = action;
}

public int getBet() {
	return bet;
}

public void setBet(int bet) {
	this.bet = bet;
}

public Street getStreet() {
	return street;
}

public void setStreet(Street street) {
	this.street = street;
}
public Player getCurrentToAct() {
	return currentToAct;
}

public void setCurrentToAct(Player currentToAct) {
	this.currentToAct = currentToAct;
}
public ArrayList<Position> getPositions() {
	return positions;
}

public ArrayList<Card> getBoard() {
	return board;
}
public int getPot() {
	return pot;
}

public void setPot(int pot) {
	this.pot = pot;
}

public Position getCurrentPosition() {
	return currentPosition;
}

public void setCurrentPosition(Position currentPosition) {
	this.currentPosition = currentPosition;
}

public ArrayList<Player> getPlayers() {
	return players;
}

public boolean isRaisePresent() {
	return raisePresent;
}

public void setRaisePresent(boolean raisePresent) {
	this.raisePresent = raisePresent;
}

public Blinds getBlind() {
	return blind;
}

public void setBlind(Blinds blind) {
	this.blind = blind;
}
}
