// James Murphy
// CPSC 240 / Object-Oriented Analysis & Design
// Project 1 / Emergency Room Patient Manager

import java.util.Scanner;
import java.util.InputMismatchException;
import java.time.LocalDateTime;
import java.lang.NumberFormatException;
import java.lang.Integer;
import java.util.Arrays;

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
			// some joker forgets to put in a character
			try {
				selection = input.nextLine().charAt(0);
			} catch (StringIndexOutOfBoundsException e) {
				continue;
			}
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
			// some joker hits enter without entering a character
			try {
				selection = input.nextLine().charAt(0);
			} catch (StringIndexOutOfBoundsException e) {
				continue;
			}
			switch (selection) {
				// (e)nter new patient
				case 'E':
				case 'e':
					try {
						addPatient(input, pq);
					} catch (InputMismatchException e) {
						System.err.println(e.getMessage());
					} finally {
						waitForInput();
						break;
					}
				
				// (r)emove next patient in line
				case 'R':
				case 'r':
					removePatient(input, pq);
					waitForInput();
					break;
				
				// (f)ind position of patient in queue
				case 'F':
				case 'f':
					findPatient(input, pq);
					waitForInput();
					break;
				
				// (s)ort and display patients by priority
				case 'S':
				case 's':
					sortPatientsByPriority(input, pq);
					waitForInput();
					break;
				
				// (p)rint list of patients sorted by ID
				case 'P':
				case 'p':
					sortPatientsByID(input, pq);
					waitForInput();
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
	
	static void prompt() {
		_print(" > ");
	}
	
	static void addPatient(Scanner s, PatientQueue pq) {
		/* Initialize resources. */
		String first, last;
		int priority = -1;
		boolean failed = false;
		
		/* Obtain fields needed for Patient creation. */	
		_print("\nEnter the patient's last name: ");
		last = s.nextLine();
		
		_print("\nEnter the patient's first name: ");
		first = s.nextLine();
		
		_print("\nEnter the patient's priority (000-999): ");
		/* Prevent crashes caused by entering non-integer values. */
		try {
			priority = Integer.parseInt(s.nextLine(), 10);
		} catch (NumberFormatException e) {
			failed = true;
			throw new InputMismatchException("Value was not an integer!" +
				" Returning to menu...");
		}
		
		/* Enqueue the patient and print confirmation message to screen. */
		_println("");
		LocalDateTime enqueueTime = pq.enqueue(first, last, priority);
		_println("Added patient '" + first + " " + last + "' at " +
			enqueueTime.toString() + ".");
		_println(Integer.toString(pq.size()) +" now waiting.");
	}
	
	static void removePatient(Scanner s, PatientQueue pq) {
		if (pq.isEmpty()) {
			_println("No patients are waiting at this time.");
			return;
		} else {
			Patient nextInLine = pq.dequeue();
			String patientName = nextInLine.getFullName();
			String pid = String.format("%06d", nextInLine.getPatientID());
			_println("Patient " + patientName + " ID: " + pid +
				" will be seen now.");
		}
	}
	
	static void findPatient(Scanner s, PatientQueue pq) {
		/* Allocate resources. */
		Patient[] patients = pq.toArray();
		String first, last;
		boolean found = false;
		
		_print("\nEnter the patient's last name: ");
		last = s.nextLine();
		
		_print("\nEnter the patient's first name: ");
		first = s.nextLine();
		
		/* Find all patients with given name. */
		for (int i = 0; i < patients.length; i++) {
			if (patients[i].getFirstName().equals(first)) {
				if (patients[i].getLastName().equals(last)) {
					_println("There are " + Integer.toString(i) +
						" people ahead of " + patients[i].toString());
					found = true;
				}
			}
		}
		
		/* Alert user to spelling errors or unknown patient. */
		if (!found) {
			_println("Patient '" + first + " " + last + "' was not found.");
		}
	}
	
	static void sortPatientsByPriority(Scanner s, PatientQueue pq) {
		/* Initialize array */
		Patient[] patients = pq.toArray();
		
		// Patient::compareTo() naturally orders Patients by priority,
		// then arrival time
		Arrays.sort(patients);
		
		/* Format line */
		_println("ID\t Priority\tArrival Time\tLast Name, First Name");
		/* Queue is already sorted when converted to array. */
		for (int i = patients.length - 1; i >= 0; i--) {
			_println(Integer.toString(patients[i].getPatientID()) + "\t " +
				String.format("%06d", patients[i].getPriority()) + "\t" +
				patients[i].getArrivalTime().toString() + "\t  " +
				patients[i].getFullNameReversed());
		}
	}
	
	static void sortPatientsByID(Scanner s, PatientQueue pq) {
		;
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



