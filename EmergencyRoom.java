// James Murphy
// CPSC 240 / Object-Oriented Analysis & Design
// Project 1 / Emergency Room Patient Manager

import java.util.Scanner;

public class EmergencyRoom {
	static void _println(String message) {
		System.out.println(message);
	}
	
	static void _print(String message) {
		System.out.print(message);
	}

	static void splashScreen() {
		_println("=======================================================");
		_println("   CPSC 240 EMERGENCY ROOM PATIENT QUEUE APPLICATION   ");
		_println("=======================================================");
		_print("");
	}
	
	static void startMenu() {
		_println("\nSelect an option:");
		_println("\t(S)tart new session");
		_println("\t(R)esume previous session");
		_println("\t(Q)uit");
	}
	
	static void mainMenu() {
		_println("\nSelect an option:");
		_println("\t(E)nter a new patient.");
		_println("\t(R)emove next patient.");
		_println("\t(F)ind position of patient in queue.");
		_println("\t(S)ort and display patients by priority.");
		_println("\t(P)rint list of patients sorted by ID.");
		_println("\t(Q)uit");
	}
	
	public static void main(String[] args) {
		/* initialize system resources */
		PatientQueue pq = null;
		char selection = 0;
		Scanner input = new Scanner(System.in);
				
		splashScreen();
		startMenu();
		_print(" > ");
		
		// Start menu code, runs once
		boolean validCommand = false;
		do {
			selection = input.next().charAt(0);
			switch (selection) {
				case 'S':
				case 's':
					pq = new PatientQueue();
					validCommand = true;
					break;
				case 'R':
				case 'r':
					pq = new PatientQueue(PatientQueue.retrieveIDCounter());					validCommand = true;
					break;
				case 'Q':
				case 'q':
					_println("Closing program...");
					System.exit(0);
					break;
				default:
					_println("Please enter a valid command.\n");
					startMenu();
			}
		} while (!validCommand);
		
		
	}
}
