import java.util.Scanner;

public class MainMenu {
	
	static Scanner input = new Scanner(System.in);
	
	public void mainMenu() throws InterruptedException {
				
		clearContent();
		
		System.out.println("Welcome to RACK-O!");	
		
		boolean valid = true;
		
		while (valid) { //valid will always be true, so loop goes until user enters 4
			
			System.out.println("\nPlease type on your keyboard an option and then hit enter:");
			
			System.out.println("(1): ONE PLAYER MODE\n(2): MULTI-PLAYER MODE\n(3): INSTRUCTIONS\n(4): QUIT");
			int choice = 0;
			
			try { choice = input.nextInt(); } //store user input 
			catch (Exception e) {
				System.out.println("You have entered an invalid selection.\n"
						+ "This program will now end.");
				break;
			}
			
			switch (choice) { //call actions based on user input 
			
				case 1: 
					clearContent();
					OnePlayer.play(); //call class to simulate 1 player mode 
					clearContent();
					break;
					
				case 2: 
					clearContent();
					MultiPlayer.play(); //call class to simulate multi-player mode 
					clearContent();
					break;
				
				case 3: 
					clearContent();
					Instructions.instructions(); //call class to print instructions
					clearContent();
					break;
					
				case 4:
					System.out.println("So long and thanks for all the fish!");
					System.exit(1); //close entire program
					break;
					
				default: //error if user enters number other than given
					System.out.println("You have entered an unrecognized choice. "
							+ "Please be sure to enter a valid option");
					break;
			}
		}
	}
	
	public static void clearContent() {
		
		for(int clear = 0; clear < 200; clear++) 
			System.out.println("\n") ;
	}
}