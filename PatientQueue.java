/** Queue for patients waiting for medical attention in emergency room.
 * @author James Murphy
 * @version 1.0 January 31st, 2016
 */

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class PatientQueue {
	/** Patient ID tracker -- increments on patient enqueue.
	 * Counter runs from 1 to 999,999.
	 */
	private int patientIDCounter;
	
	/** Patient counter -- increments on enqueue, decrements on dequeue.
	 * When this value is zero, the queue is empty and the dequeue()
	 * method should NOT be called on it. This value can be checked with
	 * the method isEmpty().
	 */
	private int patientCounter;
	
	/** Priority queue for determining which patient will next be helped.*/
	private PriorityQueue<Patient> priorityQueue;
	
	/** Default constructor for PatientQueue */	
	public PatientQueue() {
		patientIDCounter = 1;
		patientCounter = 0;
		priorityQueue = new PriorityQueue<Patient>();
	}
	
	/** Parameterized constructor for resuming an ER session.
	 * @param pIDc patientIDCounter from saved session.
	 */
	public PatientQueue(int pIDc) {
		patientIDCounter = pIDc;
		patientCounter = 0;
		priorityQueue = new PriorityQueue<Patient>();
	}
	
	/** Removes the patient from the front of the list.
	 * @return The most urgent patient in the queue.
	 */
	public Patient dequeue() {
		patientCounter--;
		return priorityQueue.poll();
	}
	
	/** Checks the patient at the front of the list.
	 * @return The most urgent patient in the queue.
	 */
	public Patient check() {
		return priorityQueue.peek();
	}
	
	/** Checks to see if the priority queue is empty.
	 * @return A boolean, true if the queue is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return this.patientCounter == 0;
	}
	
	/** Constructs and enqueues a patient.
	 * @param firstName The patient's first name.
	 * @param lastName The patient's last name.
	 * @param priority The patient's level of injury or illness.
	 * @return A LocalDateTime object when the patient was enqueued.
	 */
	public LocalDateTime enqueue(String firstName, String lastName,
		int priority) {
		if (priority > 999) {
			priority = 999;
		} else if (priority < 0) {
			priority = 0;
		}
		
		Patient p = new Patient(firstName, lastName, priority);
		LocalDateTime enqueueTime = p.getArrivalTime();
		priorityQueue.add(p);
		patientCounter++;
		return enqueueTime;
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
	
	/** Returns an ArrayList of Patients in Queue order.
	 * @return An ArrayList of Patient objects.
	 */
	public ArrayList toArrayList() {
		ArrayList<Patient> patients =
		new ArrayList<Patient>(this.priorityQueue);
		return patients;
	}
	
}
