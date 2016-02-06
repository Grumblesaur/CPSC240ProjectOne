/** Queue for patients waiting for medical attention in emergency room.
 * @author James Murphy
 * @version 1.0 January 31st, 2016
 */

import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class PatientQueue {
	/** Patient ID tracker -- increments on patient enqueue.
	 * Counter runs from 1 to 999,999.
	 */
	private int patientIDCounter;
	
	/** Priority queue for determining which patient will next be helped.*/
	private PriorityQueue<Patient> priorityQueue;
	
	/** Default constructor for PatientQueue */	
	public PatientQueue() {
		patientIDCounter = 1;
		priorityQueue = new PriorityQueue<Patient>();
	}
	
	/** Parameterized constructor for resuming an ER session.
	 * @param pIDc patientIDCounter from saved session.
	 */
	public PatientQueue(int pIDc) {
		patientIDCounter = pIDc;
		priorityQueue = new PriorityQueue<Patient>();
	}
	
	/** Adds a patient to the queue.
	 * @param p A patient.
	 */
	public void enqueue(Patient p) {
		priorityQueue.add(p);
	}
	
	/** Removes the patient from the front of the list.
	 * @return The most urgent patient in the queue.
	 */
	public Patient dequeue() {
		return priorityQueue.poll();
	}
	
	/** Checks the patient at the front of the list.
	 * @return The most urgent patient in the queue.
	 */
	public Patient check() {
		return priorityQueue.peek();
	}
	
	/** Wraps the Patient() constructor within PatientQueue class.
	 * @param firstName The patient's first name.
	 * @param lastName The patient's last name.
	 * @param priority The patient's level of injury or illness.
	 * @return A Patient object.
	 */
	public Patient createPatient(String firstName, String lastName,
		int priority) {
		return new Patient(firstName, lastName, priority);
	}
	
	/** Retrieves the stored patientIDCounter value stored in a file.
	 * @param filename The name of the file containing patientIDCounter.
	 * @return Resumed patientIDCounter as an int.
	 */
	public static int retrieveIDCounter() {
		String filename = "idc.txt";
		int IDCounter = 1;
		try {
			Scanner in = new Scanner(new FileReader(filename));
			if (in.hasNextInt()) {
				IDCounter = in.nextInt();
			} else {
				IDCounter = 1;
			}
		} catch (FileNotFoundException e) {
			System.err.println("File '" + filename + "' not found.");
			System.out.println("Defaulting to patientIDCounter = 1.");
			IDCounter = 1;
		} finally {
			return IDCounter;
		}
	}
	
	/** Saves the PatientQueue's patientIDCounter value to file "idc.txt".
	 */
	public void saveIDCounter() {
		String filename = "idc.txt";
		PrintWriter w = null;
		try {
			w = new PrintWriter(filename, "ASCII");
		} catch (FileNotFoundException e) {
			System.err.println("File '" + filename + "' doesn't exist.");
		} catch (UnsupportedEncodingException e) {
			System.err.println("System does not support ASCII.");
			System.err.println("Please contact your sysadmin.");
		} finally {
			if (w != null) {
				w.println(this.patientIDCounter);
				w.close();
			}
		}
	}

}
