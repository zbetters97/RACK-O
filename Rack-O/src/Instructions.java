import java.util.Scanner;

public class Instructions {
	
	static Scanner input = new Scanner (System.in);
	
	public static void instructions() {
		
		System.out.println("\nABOUT:\n\nThis game is called RACK-O. "
				
				+ "This is a computer only version of the game 'RACK-O.'\n\n"
				+ "Players are presented with a deck of cards. There are 50 cards in the deck " 
				+ "and each card has a number between 1 and 50.\n"
					+ "\tBoth players are dealt 10 cards from the main deck and those 10 cards are laid out in the order given.\n\n"
				+ "The objective of RACK-O is for one player to sort his/her given list of 10 cards in sequential order (Spot #1 = Smallest, Spot #10 = Largest).\n"				
					+ "\tFor example, the following is a winning card list: [5, 12, 14, 15, 20, 36, 38, 42, 43, 47].\n\n"
				+ "For each each turn, the player unveils the top card of the main deck.\n"
					+ "\tThe player then has the option to eaither SWAP or PASS.\n"
					+ "\tIf the player chooses SWAP, then they must pick a spot in their list of cards "
					+ "to replace with the chosen card.\n"
					+ "\tFor example, player one unveils the top card and it is #21. They choose to SWAP.\n"
					+ "\tPlayer one then picks spot #3 which holds the card #37.\n" 
					+ "\tCard #37 gets placed in the bottom of the main deck while card #21 gets placed in spot #3 of player one.\n\n"
				
					
				+ "All players participating in the game each rotate through these turns until one player in the group\n" 
					+ "\thas ordered all 10 of their cards -- RACK-O!!!\n\n"
				
				+ "HIT THE ENTER KEY TO EXIT INSTRUCTIONS");
		
		input.nextLine();
	}
}