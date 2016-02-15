// James Murphy
// CPSC 240 / Object-Oriented Analysis & Design
// Project 1 / Emergency Room Patient Manager

/* Utilities and Libraries */
import java.util.Scanner;
import java.time.LocalDateTime;
import java.lang.Integer;
import java.util.Arrays;
import java.util.Comparator;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileOutputStream;

/* Exceptions */
import java.io.IOException;
import java.util.InputMismatchException;
import java.lang.NumberFormatException;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class EmergencyRoom {
	/* Program logic */
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
					int IDCounter = 1;
					try {
						Scanner in = new Scanner(new FileReader("idc.txt"));
						if (in.hasNextInt()) {
							IDCounter = in.nextInt();
						}
					} catch(FileNotFoundException e) {
						System.err.println("File 'idc.txt' not found.");
						System.err.println("Defaulting ID counter to 1.");
					} finally {
						pq = new PatientQueue(IDCounter);
						_println("Patient IDs starting from " +
						Integer.toString(pq.getPatientIDCounter()));
						validCommand = true;
						waitForInput();
						break;
					}
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
					removePatient(pq);
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
					sortPatientsByPriority(pq);
					waitForInput();
					break;
				
				// (p)rint list of patients sorted by ID
				case 'P':
				case 'p':
					sortPatientsByID(pq);
					waitForInput();
					break;
				
				// (q)uit
				case 'Q':
				case 'q':
					quitting = true;
					save(pq);
					_println("Session data saved in 'idc.txt'.");
					_print("You will be prompted to resume this session");
					_print(" upon next startup.\n");
					_println("Closing program...");
					break;
				
				default:
					break;
			}
		} while (!quitting);
			
	}
	
	/* Because I hate typing System.out.println() all the time. */
	static void _println(String message) {
		System.out.println(message);
	}
	static void _print(String message) {
		System.out.print(message);
	}
	
	/* What good program doesn't have one of these? */
	static void splashScreen() {
		_println("=======================================================");
		_println("   CPSC 240 EMERGENCY ROOM PATIENT QUEUE APPLICATION   ");
		_println("=======================================================");
		_print("");
	}
	
	/* Render menus for loops. */
	static void startMenu() {
		_println("\nSelect an option:");
		_println("\t(S)tart new session (Will overwrite saved session!)");
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
	
	/* Indicate to user that command is expected. */
	static void prompt() {
		_print(" > ");
	}
	
	/* Wrap PatientQueue::enqueue() operation inside helper function
		for I/O and error checking.
	*/
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
	
	/* Wrap PatientQueue::dequeue() operation in helper function
		for error checking and I/O.
	*/
	static void removePatient(PatientQueue pq) {
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
	
	/* Create a searchable array of patients and print all who match the
		user's search input.
	*/
	static void findPatient(Scanner s, PatientQueue pq) {
		if (pq.isEmpty()) {
			_println("No patients are waiting at this time.");
			return;
		}
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
	
	/* Sort and print patients as they are ordered by priority. */
	static void sortPatientsByPriority(PatientQueue pq) {
		if (pq.isEmpty()) {
			_println("No patients are waiting at this time.");
			return;
		}
		
		/* Initialize array */
		Patient[] patients = pq.toArray();
		
		/* Patient::compareTo() naturally orders Patients by priority,
			then arrival time. */
		Arrays.sort(patients);
		
		/* Format line */
		_println("ID\t Priority\tArrival Time\tLast Name, First Name");
		/* Queue is already sorted when converted to array. */
		for (int i = 0; i < patients.length; i++) {
			_println(String.format("%06d", patients[i].getPatientID()) +
				"\t " + Integer.toString(patients[i].getPriority()) +
				"\t" + patients[i].getArrivalTime().toString() +
				"\t  " + patients[i].getFullNameReversed());
		}
	}
	
	/* Sort and print patients as they are ordered by ID. */	
	static void sortPatientsByID(PatientQueue pq) {
		if (pq.isEmpty()) {
			_println("No patients are waiting at this time.");
			return;
		}
		
		/* No more bubble sort! */
		Patient[] patients = pq.toArray();
		Arrays.sort(patients, new PatientComparator());
		
		/* Format patient data. */
		for (int i = 0; i < patients.length; i++) {
			_println(String.format("%06d", patients[i].getPatientID()) +
			"\t " + Integer.toString(patients[i].getPriority()) +
			"\t" + patients[i].getArrivalTime().toString() +
			"\t  " + patients[i].getFullNameReversed());
		}
	}
	
	/* Ensure that user sees output of other functions before returning to
		main menu.
	*/
	static void waitForInput() {
		// Code snippet borrowed from @katamayros' post on this SO thread:
		//http://stackoverflow.com/questions/19870467/how-do-i-get-press-any
		//-key-to-continue-to-work-in-my-java-code
		_println("Press ENTER/RETURN to continue.");
		try {
			System.in.read();
		} catch (Exception e) {
			;
		}
	}
	
	/* Save IDCounter state to file. */
	static void save(PatientQueue pq) {
		PrintWriter w = null;
		try {
			w = new PrintWriter("idc.txt", "ASCII");
		} catch (FileNotFoundException e) {
			// Writing to the working directory, which is a hardcoded
			// condition in this case, will not cause this exception to
			// occur.
		} catch (UnsupportedEncodingException e) {
			System.err.println("System does not support ASCII.");
			System.err.println("Please contact your sysadmin.");
			System.exit(1);
		} finally {
			if (w != null) {
				w.println(pq.getPatientIDCounter());
				w.close();
			} else {
				save(pq);
			}
		}
	}
	
}

