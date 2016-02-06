/** Queue for patients waiting for medical attention in emergency room.
 * @author James Murphy
 * @version 2.0 February 6th, 2016
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
	
	/** Checks to see if the priority queue is empty.
	 * @return A boolean, true if the queue is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return priorityQueue.size() == 0;
	}
	
	/** Obtains number of patients in queue.
	 * @return An in, the number of patients waiting.
	 */
	public int size() {
		return priorityQueue.size();
	}
	
	/** Obtains number of patients since session start.
	 * @return An int, patientIDCounter.
	 */
	public int getPatientIDCounter() {
		return patientIDCounter;
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
		p.setPatientID(this.patientIDCounter++);
		LocalDateTime enqueueTime = p.getArrivalTime();
		priorityQueue.add(p);
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
	
	/** Returns an array of Patients in Queue order.
	 * @return An array of Patient objects.
	 */
	public Patient[] toArray() {
		Object[] objects = this.priorityQueue.toArray();
		Patient[] patients = new Patient[objects.length];
		for (int i = 0; i < objects.length; i++) {
			patients[i] = (Patient) objects[i];
		}
		return patients;
	}
	
}
