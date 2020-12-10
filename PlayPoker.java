import java.util.ArrayList;
import java.util.Scanner;

/**
 * driver class for the Poker simulator
 * 
 * @author patdl
 *
 */
public class PlayPoker {
	private static PokerTable table = new PokerTable(3, new Deck(52, true), Blinds.ONETWO, Player.createPlayers());
	private static Player user;

	private static void printWelcome(Scanner scanner) {
		System.out.println("Welcome to Patrick's Poker Player!\n"
				+ "The name of the game is Texas Hold'em and we've got a table open for you\n"
				+ "let's start with a name for yourself, what do you go by?");
		String name = scanner.next();
		System.out.println("Nice to meet you " + name + " how much do you want to buy in with?\n"
				+ " enter a number between 100 - 300:");
		int buyIn = scanner.nextInt();
		System.out.println("Great, the game is about to begin, Here are the controls\n"
				+ "    bet <money amount> - only valid if you start the betting\n"
				+ "    check - stay in hand, no bet\n" + "    call - call a player's bet\n"
				+ "    raise <money amount> - raise a players bet/raise\n" + "    quit - quits application\n"
				+ "    Good Luck!\n\n");
		pause(3);
		user = new Player(name, buyIn);
		user.setPosition(Position.UTG);
		table.addPlayer(user);
		scanner.nextLine();
	}

	/**
	 * processes user commands if valid, if not return 0 to run again
	 * 
	 * @param command
	 * @return 1 = success, 0 = user made a mistake retry, -1 = quit
	 */
	private static int userCommand(String command) {
		command.trim();
		String[] commands = command.split(" ");
		if (commands[0].charAt(0) == 'q')
			return -1;
		if (commands[0].equalsIgnoreCase("bet")) {
			if (commands.length != 2) {
				System.out.println("bet must be followed by a single integer");
				return 0;
			}
			int betAmount;
			try {
				betAmount = Integer.parseInt(commands[1]);
			} catch (Exception e) {
				System.out.println("must input parasable integer after \"bet\"");
				return 0;
			}
			if (table.getAction() == Action.RAISE || table.getAction() == Action.BET || table.getAction() == Action.CALL
					|| table.isRaisePresent()) {
				System.out.println("Cannot make initial bet after a bet or raise or call");
				return 0;
			}
			if (betAmount > user.getMoneyAmount()) {
				System.out.println("Cannot bet more money then you have");
				return 0;
			}
			table.changePot(user.bet(betAmount));
			table.setAction(Action.BET);
			table.setBet(betAmount);
			if (user.isAllIn()) {
				System.out.println("You're All In!");
			}
			return 1;
		}

		if (commands[0].equalsIgnoreCase("check")) {
			if (user.getPosition() == Position._BB && table.getBet() == 2) {
				user.check();
				table.setAction(Action.CHECK);
				return 1;
			} else if (table.getAction() == Action.BET || table.getAction() == Action.RAISE
					|| table.getAction() == Action.CALL || table.isRaisePresent()) {
				System.out.println("Cannot check after when there is bet/raise on the table");
				return 0;
			}
			user.check();
			table.setAction(Action.CHECK);
			return 1;
		}
		if (commands[0].equalsIgnoreCase("call")) {
			if (table.getAction() == Action.CHECK) {
				System.out.println("Cannot call without a bet/raise on the table");
				return 0;
			}
			int betToCall = table.getBet();
			table.changePot(user.call(betToCall, user.getBet()));
			table.setAction(Action.CALL);
			return 1;
		}
		if (commands[0].equalsIgnoreCase("raise")) {
			if (commands.length != 2) {
				System.out.println("raise must be followed by a single integer");
				return 0;
			}
			int raiseAmount;
			try {
				raiseAmount = Integer.parseInt(commands[1]);
			} catch (Exception e) {
				System.out.println("must input parasable integer after \"raise\"");
				return 0;
			}
			if (table.getAction() == Action.NOACTION || table.getAction() == Action.CHECK) {
				System.out.println("Can only make a raise after an intial bet has been made");
				return 0;
			}
			if (raiseAmount > user.getMoneyAmount()) {
				System.out.println("Cannot raise more money then you have");
				return 0;
			}
			if (raiseAmount < table.getBet()) {
				System.out.println("must raise at least the size of the previous bet");
				return 0;
			}
			table.changePot(user.raise(raiseAmount, table));
			table.setAction(Action.RAISE);
			table.setBet(raiseAmount);
			if (user.isAllIn()) {
				System.out.println("You're All In!");
			}
			return 1;
		}
		if (commands[0].equalsIgnoreCase("fold")) {
			user.fold();
			table.setAction(Action.FOLD);
			return 1;
		}

		System.out.println("Error, not a valid command");
		System.out.println("bet <money amount> - only valid if you start the betting\n"
				+ "check - stay in hand, no bet\n" + "call - call a player's bet\n"
				+ "raise <money amount> - raise a players bet/raise\n" + "quit - quits application\n");
		return 0;
	}

	/**
	 * executes either the command of a poker bot or the user and determines if the
	 * round is over
	 * 
	 * @param currentToAct
	 * @param scanner
	 * @return - whether the betting is done, 2 = entire round is over, 1 = current
	 *         street is over, 0 = street not over
	 */
	private static int playerAct(Player currentToAct, Scanner scanner) {
		Player lastToAct = lastToAct();
		int returnValue = -1;
		if (currentToAct.equals(user)) {
			while (true) {
				System.out.println("what is your move:");
				String command = scanner.nextLine();
				returnValue = userCommand(command);
				if (returnValue == 1)
					break;
				if (returnValue == -1)
					return returnValue;
			}

		} else {
			pause(6);
			currentToAct.act(table, lastToAct);
		}

		return bettingOver(currentToAct, lastToAct);

	}

	/**
	 * runs one street to completion,
	 * 
	 * @param scanner
	 * @return - is street done, 2 = entire round is over, 1 = current street is
	 *         over, 0 = street not over
	 */
	private static int runStreet(Scanner scanner) {
		int streetDone = 0;
		switch (table.getStreet()) { // sets up the street
		case PREFLOP:
			break;
		case FLOP:
			table.dealFlop();
			pause(3);
			System.out.println("The Flop Comes " + table.getBoard());
			pause(5);
			System.out.println(table);
			setHandTypes();
			if (user.isInHand()) {
				printCards();
				printHand();
			}
			break;
		case TURN:
			table.dealTurn();
			pause(3);
			System.out.println("The turn is a " + table.getBoard().get(3));
			pause(5);
			System.out.println(table);
			setHandTypes();
			if (user.isInHand()) {
				printCards();
				printHand();
			}
			break;
		case RIVER:
			table.dealRiver();
			pause(3);
			System.out.println("The river is a " + table.getBoard().get(4));
			pause(5);
			System.out.println(table);
			setHandTypes();
			if (user.isInHand()) {
				printCards();
				printHand();
			}
			break;
		}
		// runs the street
		Player currentToAct;
		while (streetDone == 0) {
			currentToAct = table.getCurrentToAct();
			streetDone = playerAct(currentToAct, scanner);
			System.out.println(table);
			if (user.isInHand()) {
				printCards();
				printHand();
			}
			table.moveToAct();
		}
		return streetDone;
	}

	/**
	 * runs rounds of poker
	 * 
	 * @param scanner
	 * @return - whether the user is going to continue to play
	 */
	private static boolean runRound(Scanner scanner) {
		int roundDone = 0;
		table.dealCards();
		printCards();
		pause(3);
		table.betBlinds();
		System.out.println(table.toString());
		pause(1);
		System.out.println(table.findPlayerUsingPosition(Position._SB).toString() + " enters his small blind of 1");
		pause(1);
		System.out.println(table.findPlayerUsingPosition(Position._BB).toString() + " enters his big blind of 2");

		while (roundDone == 0) {
			int runStreet = runStreet(scanner);
			if (runStreet == -1)
				roundDone = -1;
			else if (runStreet == 1)
				roundDone = 0;
			else if (runStreet == 2)
				roundDone = 1;
			resetPlayerAttributes();
			if (roundDone != 1) {
				table.setCurrentToAct(firstToAct());
				changeStreet();
			}
		}
		if (roundDone == -1)
			return true;
		Player winner = table.determineWinner();
		System.out.println(winner + " wins the pot of " + table.getPot() + " with a " + winner.getHandtype());
		pause(5);
		winner.changeMoney(-table.getPot());
		table.roundOver();
		table.changePositions();
		return false;
	}

	/**
	 * runs the poker player
	 * 
	 * @param scanner
	 */
	private static void runGame(Scanner scanner) {
		boolean gameDone = false;
		while (!gameDone) {
			gameDone = runRound(scanner);
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////// HELPER FUNCTIONS BELOW ////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * helper method that finds the position of who will be last to act
	 */
	private static Position findLastToActPosition(Position positionOfHighestRaiser) {
		ArrayList<Position> positionsRearranged = deepCopy(table.getPositions());
		int originalSize = table.getPositions().size();
		int raiserPosition = positionOfHighestRaiser.ordinal();
		for (int i = 0; i < raiserPosition; i++) {
			positionsRearranged.add(table.getPositions().get(i));
			positionsRearranged.remove(0);
		}
		for (int i = positionsRearranged.size() - 1; i >= 0; i--) {
			if (table.findPlayerUsingPosition(positionsRearranged.get(i)).isInHand())
				return positionsRearranged.get(i);
		}
		System.out.println("Error in findLastToActPosition, code should never be reached");
		return null;
	}

	/**
	 * determines the current highest raiser for this street, last to act can then
	 * by found
	 * 
	 * @return - the highest raiser
	 */
	private static Player highestRaiser() {
		Player highestRaiser = null;
		Player lastToAct;
		for (Player player : table.getPlayers()) {
			if ((player.getAction() == Action.RAISE || player.getAction() == Action.BET) && player.isInHand()
					&& player.getBet() == table.getBet())
				highestRaiser = player;
		}
		return highestRaiser;
	}

	/**
	 * @return - the player who could potentially end the current round of betting
	 */
	private static Player lastToAct() {
		Player lastToAct;
		Player highestRaiser = highestRaiser();
		if (highestRaiser != null) {
			Position positionOfHighestRaiser = highestRaiser.getPosition();
			Position positionOfLastToAct = findLastToActPosition(positionOfHighestRaiser);
			lastToAct = table.findPlayerUsingPosition(positionOfLastToAct);
			return lastToAct;

		} else if (table.getStreet() == Street.PREFLOP) {
			return table.findPlayerUsingPosition(Position._BB);
		} else {
			lastToAct = table.findPlayerUsingPosition(Position._SB);
			for (Player player : table.getPlayers()) {
				if (player.isInHand() && (player.getPosition().ordinal() == 0))
					return player;
				if (player.getPosition().ordinal() > lastToAct.getPosition().ordinal())
					lastToAct = player;
			}
		}
		return lastToAct;
	}

	/**
	 * @return - the player who will be first to act
	 */
	private static Player firstToAct() {
		Player firstToAct = null;
		for (Player player : table.getPlayers()) {
			if (firstToAct == null && player.isInHand() && !player.getPosition().equals(Position.DLR)) {
				firstToAct = player;
				continue;
			}
			if (firstToAct != null && player.isInHand() && !player.getPosition().equals(Position.DLR)
					&& player.getPosition().ordinal() < firstToAct.getPosition().ordinal())
				firstToAct = player;
		}
		return firstToAct;
	}

	/**
	 * @param players - the players at the table
	 * @return if we are in a situation where the multiple players are all in and
	 *         its time for showdown.
	 */
	private static boolean allIn(ArrayList<Player> players) {
		ArrayList<Player> playersInHand = new ArrayList<Player>(players.size());
		int allInCounter = 0;
		for (Player player : players) {
			if (player.isInHand()) {
				playersInHand.add(player);
				if (player.isAllIn())
					allInCounter++;
			}
		}
		if (allInCounter == playersInHand.size() || allInCounter == playersInHand.size() - 1)
			return true;
		return false;
	}

	/**
	 * @param playerJustActed
	 * @param lastToAct
	 * @return - whether the current street is over, 2 = entire round is over, 1 =
	 *         current street is over, 0 = street not over
	 */
	private static int bettingOver(Player playerJustActed, Player lastToAct) {
		int numPlayersStillIn = 0;
		// easiest case, only one player still in hand, round over
		for (Player player : table.getPlayers()) {
			if (player.isInHand())
				numPlayersStillIn++;
		}
		if (numPlayersStillIn == 1)
			return 2;
		// if more than one player, check if lastToAct just acted
		switch (table.getAction()) {
		case CHECK:
			if (playerJustActed.equals(lastToAct) && (table.getStreet() == Street.RIVER || allIn(table.getPlayers())))
				return 2;
			else if (playerJustActed.equals(lastToAct))
				return 1;
			else
				return 0;
		case BET:
			return 0;
		case RAISE:
			return 0;
		case CALL:
			if (playerJustActed.equals(lastToAct)
					&& (table.getStreet() == Street.RIVER && playerJustActed.getBet() == table.getBet())
					|| allIn(table.getPlayers()))
				return 2;
			else if (playerJustActed.equals(lastToAct) && playerJustActed.getBet() == table.getBet())
				return 1;
			else
				return 0;
		case FOLD:
			if (playerJustActed.equals(lastToAct) && (table.getStreet() == Street.RIVER) || allIn(table.getPlayers()))
				return 2;
			else if (playerJustActed.equals(lastToAct))
				return 1;
			else
				return 0;
		}
		System.out.print("error in bettingOver, this code should never be reached");
		return -1;
	}

	/**
	 * set the handTypes for all the users
	 */
	private static void setHandTypes() {
		for (Player player : table.getPlayers()) {
			if (player.isInHand())
				FindHand.findBestHand(player, table.getBoard());
		}
	}

	/**
	 * print the hole cards of the user
	 */
	private static void printCards() {
		System.out.println("                                            Your Cards: " + user.getHoleCards()[0] + " "
				+ user.getHoleCards()[1]);
	}

	/**
	 * print the hand type of the user
	 */
	private static void printHand() {
		System.out.println("                                            your hand is a " + user.getHandtype());
	}

	/**
	 * resets all the neccesary fields
	 */
	private static void resetPlayerAttributes() {
		for (Player player : table.getPlayers()) {
			player.setBet(0);
			player.setAction(Action.NOACTION);
			table.setBet(2);
			table.setAction(Action.NOACTION);
			table.setRaisePresent(false);
		}
	}

	/**
	 * changes the street to the next street preflop -> flop -> turn -> river
	 */
	private static void changeStreet() {
		int streetNum = table.getStreet().ordinal();
		if (streetNum == 2)
			table.setStreet(Street.RIVER);
		else {
			if (table.getStreet() != Street.RIVER)
				table.setStreet(Street.values()[streetNum + 1]);
		}
	}

	/**
	 * @param list
	 * @return a deep copy of the positions
	 */
	private static ArrayList<Position> deepCopy(ArrayList<Position> list) {
		ArrayList<Position> deepCopy = new ArrayList<Position>(list.size());
		for (Position position : list) {
			deepCopy.add(position);
		}
		return deepCopy;
	}

	/**
	 * pauses the program to create a more realistic user experience, if you want to
	 * see just how fast this program can run, comment these out and give it a shot!
	 * 
	 * @param seconds - how many seconds to pause
	 */
	private static void pause(int seconds) {
		try {
			java.util.concurrent.TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			System.out.println("error in pause function");
			e.printStackTrace();
		}
	}

	public static void main(String[] Args) {
		Scanner scanner = new Scanner(System.in);
		printWelcome(scanner);
		runGame(scanner);
		System.out.println("thanks for playing!");

	}
}