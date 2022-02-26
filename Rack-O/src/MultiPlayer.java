import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MultiPlayer {
	
	static Scanner input = new Scanner(System.in);
	
	public static ArrayList<Integer> mainDeck;
	public static ArrayList<Integer> revDeck;
	public static ArrayList<ArrayList<Integer>> playerDecks;
	
	public static int numPlayers;
	public static int winner;
	public static int counter;
	public static int turn;
	
	
	
	/** MAIN METHOD THAT EXECUTES MULTI-PLAYER MODE **/
	public static void play() throws InterruptedException {
		
		clearContent();

		System.out.println("\nYou have chosen MULTI-PLAYER MODE\n");
		System.out.println("\nPlease enter the number of players to play with (2, 3, or 4):");
		numPlayers = input.nextInt();
		
		while (!isNumPlayersValid()) //LOOP UNTIL SELECTION IS WITHIN RANGE			
			numPlayers = input.nextInt();
		
		prepareGame(); //CALL METHOD TO SETUP THE GAME			
		decideTurns(); //CALL METHOD TO DECIDE HOW MANY TURNS UNTIL DECK GETS SHUFFLED
		
		winner = 0; //WINNER IS NO ONE AT START
		
		int currentPlayer = 0; //currentPlayer HELD TO KEEP TRACK OF WHOSE TURN IT IS
		
		while (true) { //LOOP UNTIL MANUAL BREAK (WHEN WINNER)
						
			if (counter == 0) { //DECK HAS REACHED END OF CARDS
				
				clearContent(); 
				
				System.out.println("\nShuffling main deck . . .");
				Collections.shuffle(mainDeck); //SHUFFLE CARDS...
				
				decideTurns(); //...AND RESET COUNTER
				
				Thread.sleep(3000);
			}
			else if (revDeck.size() == 0) {
				
				mainDeck.addAll(revDeck);
				Collections.shuffle(mainDeck);
				
				revDeck.clear();
				revDeck.add(mainDeck.get(0));
				mainDeck.remove(0);			
			}
			
			if (winner != 0) //STOP LOOP IF WINNER
				break;
			
			playerTurn(currentPlayer); //CALL METHOD FOR PLAYER TO PLAY AND SEND PLAYER #		
						
			currentPlayer++; //INCREMENT i TO KEEP TRACK OF PLAYER TURN
			counter--;
			
			if (currentPlayer > numPlayers - 1) //IF currentPlayer GOES ABOVE NUMBER OF PLAYERS...
				currentPlayer = 0; //...RESET currentPlayer
		}
		
		Thread.sleep(1000);
		
		clearContent();
		
		System.out.println("\nRACK-O!!!\n\nPlayer #" + winner + ", you are the winner!!!");
		
		Thread.sleep(2000);
		
		System.out.println("\nWinning deck:"); 
		
		int spotNum = 1; //spotNum VARIABLE HELD TO DISPLAY SPOT # OF EACH CARD
		
		for (int pCard : playerDecks.get(winner)) { //LOOP THROUGH ALL CARDS IN CURRENT PLAYER LIST
			
			System.out.println("\tSpot #" + spotNum + " - " + pCard);
			spotNum++;
		}
		
		Thread.sleep(2000);
		
		outPutScore();
		
		input.nextLine(); input.nextLine();
	}
	/** END MAIN METHOD **/
	
		
	
	/** METHOD TO SETUP GAME OF RACK-O **/
	public static void prepareGame() {
		
		mainDeck = new ArrayList<>(); //CREATE ARRAYLIST WHICH CARRIES MAIN CARDS
		revDeck = new ArrayList<>();
		
		playerDecks = new ArrayList<>(); //CREATE ARRAYLIST WHICH CARRIES PLAYERS ARRAYLISTS OF CARDS
		
		for (int i = 0; i < numPlayers; i++) { //NUMBER OF TIMES LOOPED IS NUMBER OF PLAYERS
			
			ArrayList<Integer> playerDeck = new ArrayList<>(); //CREATE NEW ARRAYLIST TO HOLD PLAYER CARDS...
			playerDecks.add(playerDeck); //...AND PLACE IN ARRAYLIST OF PLAYERS
		}
		
		for (int card = 1; card <= 50; card++) //FILL DECK WITH NUMBERS 1 TO 50
			mainDeck.add(card);
		
		Collections.shuffle(mainDeck); //SHUFFLE DECK

		for (int player = 0; player < numPlayers; player++) { //NUMBER OF TIMES LOOPED IS NUMBER OF PLAYERS
			
			for (int mCard = 0; mCard <= 4; mCard++) { //GIVE EACH PLAYER IN DECK TOP 10 CARDS (0-9)
										   				//ROTATION DOES NOT MATTER IF DECK IS SHUFFLED
				playerDecks.get(player).add(mainDeck.get(mCard)); //EACH PLAYER IN ARRAYLIST OF PLAYERS GETS 10 CARDS
				mainDeck.remove(mCard); //REMOVE GIVEN CARD OUT OF MAIN DECK 
			}
		}	
		
		revDeck.add(mainDeck.get(0));
		mainDeck.remove(0);
	}
	/** END SETUP GAME METHOD **/
	
	
	
	/** METHOD TO CHECK IF NUMBER OF PLAYERS ENTERED IS VALID **/
	public static boolean isNumPlayersValid() {
		
		if (1 > numPlayers || numPlayers > 4) { //CHECK IF NUMBER OF PLAYERS ENTERED IS OUTSIDE OF RANGE 1-4
			
			System.out.println("This is not a valid selection");
			return false;
		}
		else
			return true;	
	}
	/** END NUMBER PLAYERS VALID METHOD **/
	
	
	
	/** METHOD TO CHECK HOW MANY TURNS UNTIL DECK GETS SHUFFLED**/
	public static void decideTurns() {
		
		switch (numPlayers) { //FOR EVERY PLAYER ADDED TO GAME, 10 CARDS ARE REMOVED FROM MAIN DECK
		
			case 2:
				counter = 28;
				break;				
			case 3: 
				counter = 18;
				break;			
			case 4:
				counter = 8;
				break;
		}
	}
	/** END DECIDE TURNS METHOD **/
	
	
	
	/**METHOD TO EXECUTE PLAYER TURN **/
	public static void playerTurn(int playerNum) throws InterruptedException { //# OF PLAYER TURN IS PASSED
		
		clearContent();
		System.out.println("NEXT TURN:");
		Thread.sleep(1000);
		
		turn = playerNum + 1; //turn VARIABLE HELD TO SHOW WHAT PLAYER'S TURN IT IS
		
		System.out.println("\nTURN: Player #" + turn + "\n");
		
		System.out.println("Player #" + turn + " current list:"); 
		
		int spotNum = 1; //spotNum VARIABLE HELD TO DISPLAY SPOT # OF EACH CARD
		
		for (int pCard : playerDecks.get(playerNum)) { //LOOP THROUGH ALL CARDS IN CURRENT PLAYER LIST
			
			System.out.println("\tSpot #" + spotNum + " - " + pCard);
			spotNum++;
		}

		Thread.sleep(1000);
		
		System.out.println("\nThe current visible card is: " + revDeck.get(0));

		System.out.println("\nSWAP visible card or REVEAL hidden card (S/R)?");
		String SRchoice = input.next().toUpperCase();
		
		while (!isValidSR(SRchoice)) //LOOP UNTIL VALID SELECTION IS MADE			
			SRchoice = input.next().toUpperCase();
		
		if (SRchoice.equals("S"))
			swap(true, playerNum);
		else if (SRchoice.equals("R")) 
			reveal(playerNum);
		
		if (isOrdered(playerNum)) //CHECK IF CURRENT PLAYER IS WINNER AFTER EACH TURN
			winner = turn; //ASSIGN winner VARIABLE TO CURRENT PLAYER IN PLAY
	}
	/** END EXECUTE PLAYER TURN METHOD **/
	
		
	
	/** METHOD TO REVEAL CARD AT TOP OF DECK **/
	public static void reveal(int playerNum) throws InterruptedException {

		revDeck.add(mainDeck.get(0));
		mainDeck.remove(0);
		
		System.out.println("\nCard revealed: " + mainDeck.get(0)); //SHOW CURRENT CARD IN PLAY (TOP CARD)
	
		Thread.sleep(1000);
		
		System.out.println("\nSWAP or PASS revealed card " + mainDeck.get(0) + " (S/P)?");
		String SPchoice = input.next().toUpperCase();
		
		while (!isValidSP(SPchoice)) //LOOP UNTIL VALID SELECTION IS MADE			
			SPchoice = input.next().toUpperCase();
		
		if (SPchoice.equals("S")) //IF PLAYER CHOOSES SWAP OPTION...
			swap(false, playerNum); //CALL swap METHOD...
		else if (SPchoice.equals("P")) //IF PLAYER CHOOSES PASS OPTION...
			pass(playerNum); //CALL PASS METHOD
	}
	/** END REVEAL METHOD **/
	
	
	
	/** METHOD TO EXECUTE A CARD SWAP **/	
	public static void swap(boolean visCard, int playerNum) throws InterruptedException {
		
		if (visCard) {
			
			System.out.println("\nPlayer #" + turn + " chooses SWAP . . . "
				+ "\nPlease input the card spot to swap with card " + revDeck.get(0) + ":");
		}
		else {
			
			System.out.println("\nPlayer #" + turn + " chooses SWAP . . . "
				+ "\nPlease input the card spot to swap with card " + mainDeck.get(0) + ":");
		}
		
		int spot = input.nextInt(); //STORE SELECTED CARD TO SWAP
		
		while (!isValidInt(spot)) //LOOP UNTIL VALID SELECTION
			spot = input.nextInt();

		int oldCard = playerDecks.get(playerNum).get(spot - 1); //STORE CARD IN SELECTED SPOT # TO BE SWAPPED
		int newCard;
		
		if (visCard) {
			
			newCard = revDeck.get(0); //STORE VISIBLE CARD...
			playerDecks.get(playerNum).set(spot - 1, newCard); //REPLACE SELECTED CARD IN PLAYER DECK WITH CURRENT CARD IN PLAY
			revDeck.remove(0);
		}
		else {
			
			newCard = mainDeck.get(0); //...OR STORE REVEALED CARD
			playerDecks.get(playerNum).set(spot - 1, newCard); //REPLACE SELECTED CARD IN PLAYER DECK WITH CURRENT CARD IN PLAY
			mainDeck.remove(0);
		}
		
		System.out.println("\nPlayer #" + turn + " has SWAPPED " + oldCard + " with " + newCard);
		
		Thread.sleep(1500);
				
		revDeck.add(0, oldCard); //REPLACE VISIBLE CARD WITH SELECTED CARD FROM PLAYER HAND
	}
	/** END EXECUTE SWAP METHOD **/
	
	
	
	/** METHOD TO EXECUTE A CARD PASS**/	
	public static void pass(int playerNum) throws InterruptedException {
		
		System.out.println("\nPlayer #" + turn + " chooses PASS . . .");
		
		Thread.sleep(1000);
		
		mainDeck.add(mainDeck.remove(0)); //SEND TOP CARD TO BOTTOM OF ARRAYLIST (DON'T ASK ME HOW THIS WORKS)
		revDeck.add(revDeck.remove(0)); 
	}
	/** END EXECUTE PASS METHOD**/
	
	
	
	/** METHOD TO CHECK IF S/R CHOICE IS VALID **/
	public static boolean isValidSR(String SRchoice) {
		
		if (SRchoice.equals("S") || SRchoice.equals("R")) 
			return true; //IS VALID IF INPUT IS S OR R
		else { //ANYTHING ELSE IS INVALID
			
			System.out.println("This is not a valid selection!");
			return false;
		}		
	}
	/** END S/R IS VALID METHOD**/
	
	
	
	/** METHOD TO CHECK IF S/P CHOICE IS VALID **/
	public static boolean isValidSP(String SPchoice) {
		
		if (SPchoice.equals("S") || SPchoice.equals("P")) 
			return true; //IS VALID IF INPUT IS S OR P
		else { //ANYTHING ELSE IS INVALID
			
			System.out.println("This is not a valid selection!");
			return false;
		}		
	}
	/** END S/P IS VALID METHOD**/
	
	
	
	/** METHOD TO CHECK IF SPOT SELECTION IS VALID **/
	public static boolean isValidInt(int spot) {
		
		if (1 > spot || spot > 10) { //IF SELECTION IS OUTSIDE OF NUMBER OF SPOTS
			System.out.println("This is not a valid selection");
			return false;
		}
		else
			return true;			
	}
	/** END SPOT SELECTION IS VALID METHOD **/
	
	
	
	/** METHOD TO CHECK IF LIST OF CARDS IS IN SEQUENTIAL ORDER **/
	public static boolean isOrdered(int playerNum) {

		for (int pCard = 0; pCard < playerDecks.get(playerNum).size() - 1; pCard++) { //LOOP THROUGH LIST OF CARDS
			
			if (playerDecks.get(playerNum).get(pCard) > playerDecks.get(playerNum).get(pCard + 1)) //STOP THE METHOD IF ONE SPOT IS LARGER THEN THE FOLLOWING SPOT
				return false;
		}

		return true; //RETURN TRUE IF LOOP IS NEVER STOPPED			
	}
	/** END ASK IF THERE IS WINNER METHOD **/
	
	
	
	/** METHOD TO CHECK SCORE OF EACH PLAYER **/
	public static int calcScore(int playerNum) {

		int score = 0;
		
		for (int pCard = 0; pCard < playerDecks.get(playerNum).size() - 1; pCard++) { //LOOP THROUGH LIST OF CARDS
			
			score += 5; //INCREMENT SCORE BY 5 FOR EVERY SEQUENTIAL CARD
			
			if (playerNum == winner - 1) { //WINNER SCORE IS AUTOMATICALLY SET TO 75
				score = 75;
				break;
			}
			
			
			if (playerDecks.get(playerNum).get(pCard) > playerDecks.get(playerNum).get(pCard + 1)) //STOP THE METHOD IF ONE SPOT IS LARGER THEN THE FOLLOWING SPOT
				return score;
		}

		return score; //RETURN TO SATISFY METHOD		
	}
	/** END CHECK SCORE METHOD **/
	
	
	
	/** METHOD TO OUTPUT SCORES OF PLAYERS **/
	public static void outPutScore() throws InterruptedException {
		
		for (int player = 1; player < playerDecks.size() + 1; player++) {
			
			int score = calcScore(player - 1);
			
			System.out.println("\nPlayer #" + player + "'s score: " + score + "\t");
			
			Thread.sleep(1000);
		}
	}
	/** END OUTPUT SCORES METHOD **/
	
	
	
	/** METHOD TO CLEAR SCREEN **/
	public static void clearContent() {
		
		for (int clear = 0; clear < 200; clear++) 
			System.out.println("\n") ;
	}
	/** END CLEAR SCREEN METHOD **/
}