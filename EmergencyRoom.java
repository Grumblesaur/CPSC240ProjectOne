// James Murphy
// CPSC 240 / Object-Oriented Analysis & Design
// Project 1 / Emergency Room Patient Manager

import java.util.Scanner;
import java.util.InputMismatchException;
import java.time.LocalDateTime;

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
		/* Initialize System Resources */
		PatientQueue pq = null;
		char selection = 0;
		Scanner input = new Scanner(System.in);
		
		/* Show off a little bit. */	
		splashScreen();
		
		/* Start Menu Loop */
		boolean validCommand = false;
		do {
			// prompt user input
			startMenu();
			prompt();
			selection = input.next().charAt(0);
			
			switch (selection) {
				// (S)tart new session
				case 'S':
				case 's':
					pq = new PatientQueue();
					validCommand = true;
					break;
				// (R)esume previous session
				case 'R':
				case 'r':
					pq = new PatientQueue(PatientQueue.retrieveIDCounter());
					validCommand = true;
					break;
				// (Q)uit from program
				case 'Q':
				case 'q':
					_println("Closing program...");
					System.exit(0);
					break;
				// invalid command
				default:
					_println("Please enter a valid command.\n");
			}
		} while (!validCommand);
		
		/* Main Menu Loop */
		boolean quitting = false;
		do {
			// prompt user input
			mainMenu();
			prompt();
			selection = input.next().charAt(0);
			
			switch (selection) {
				// (e)nter new patient
				case 'E':
				case 'e':
					try {
						addPatient(input, pq);
					} catch (InputMismatchException e) {
						System.err.println(e.getMessage());
					} finally {
						break;
					}
				
				// (r)emove next patient in line
				case 'R':
				case 'r':
					removePatient(input, pq);
					break;
			
				// (q)uit
				case 'Q':
				case 'q':
					quitting = true;
					// prompt user to save here
					break;
				
				default:
					break;
			}
		} while (!quitting);
			
	}
	
	public static void prompt() {
		_print(" > ");
	}
	
	public static void addPatient(Scanner s, PatientQueue pq) {
		String first, last;
		int priority = -1;
		boolean failed = false;
		
		_print("\nEnter the patient's last name: ");
		last = s.next();
		
		_print("\nEnter the patient's first name: ");
		first = s.next();
		
		_print("\nEnter the patient's priority (000-999): ");
		try {
			priority = s.nextInt();
		} catch (InputMismatchException e) {
			failed = true;
			throw new InputMismatchException("Value was not an integer!" +
				" Returning to menu...");
		}
		
		LocalDateTime enqueueTime = pq.enqueue(first, last, priority);
		_println("Added patient '" + first + " " + last + "' at " +
			enqueueTime.toString() + ".");
	}
	
	public static void removePatient(Scanner s, PatientQueue pq) {
		if (pq.isEmpty()) {
			_println("No patients are waiting at this time.");
			return;
		}
		Patient nextInLine = pq.dequeue();
		String patientName = nextInLine.getFullName();
		String pid = String.format("%06d", nextInLine.getPatientID());
		_println("Patient " + patientName + " ID: " + pid +
			" will be seen now.");
	}
	
	static void waitForInput() {
		// Code snippet borrowed from @katamayros' post on this SO thread:
		//http://stackoverflow.com/questions/19870467/how-do-i-get-press-any
		//-key-to-continue-to-work-in-my-java-code
		
		// ... I miss cin.get()
		_println("Press ENTER/RETURN to continue.");
		try {
			System.in.read();
		} catch (Exception e) {
			;
		}
	}
}



