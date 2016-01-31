/** Queue for patients waiting for medical attention in emergency room.
 * @author James Murphy
 * @version 1.0 January 31st, 2016
 */

import java.util.PriorityQueue;

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
	
	

}
