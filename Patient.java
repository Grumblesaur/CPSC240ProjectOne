/** Stores information about a patient awaiting assistance in ER.
 * @author James Murphy
 * @version 1.0 January 29th 2016
 */

import java.time.LocalDateTime; // for patient arrival time
import java.time.Clock;

class Patient {
	/** The patient's first name. */
	private String firstName;
	
	/** The patient's last name. */
	private String lastName;
	
	/** The patient's ID number. */
	private int ID;
	
	/** The patient's time of arrival. */
	private LocalDateTime arrivalTime;
	
	/** The patient's triage rating, from 000 to 999, lowest to highest.*/
	private int triagePriority;
	
	/** Constructs a Patient object.
	 * @param first The patient's first name.
	 * @param last The patient's last name.
	 * @param priority The patient's level of injury or illness.
	 */
	public Patient(String first, String last, int priority) {
		firstName = first;
		lastName = last;
		triagePriority = priority;
		// to be set upon addition to PatientQueue
		ID = 0;
		arrivalTime = null;
	}
	
	/** Obtain the patient's full name in first-last order.
	 * @return Patient's first and last name separated by a space.
	 */
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	/** Obtain the patient's full name in last-first order.
	 * @return Patient's last name and first name, separated by a comma
	   and a space.
	 */
	public String getFullNameReversed() {
		return lastName + ", " + firstName;
	}
	
	/** Obtain the patient's first name.
     * @return Patient's first name as a string.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/** Obtain the patient's last name.
	 * @return Patient's last name as a string.
	 */
	public String getLastName() {
		return lastName;
	}

	/** Obtain the patient's priority as an int.
	 * @return Patient's priority as an int.
	 */
	public int getPriority() {
		return triagePriority;
	}
	
	/** Obtain the patient's arrival time as a LocalDateTime object.
	 * @return Patient's arrival time as a LocalDateTime object.
	 */
	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}
	
	/** Set the patient's arrival time.
	 * To be done upon adding patient to the PatientQueue.
	 * @param systemClock a java.time.Clock object
	 */
	public void setArrivalTime(Clock systemClock) {
		arrivalTime = new LocalDateTime(systemClock);
	}
	
	/** Set the patient's ID number.
	 * To be done upon adding patient to the PatientQueue.
	 * @param ID a unique six digit int.
	 */
	public void setPatientID(int ID) {
		this.ID = ID;
	}
}
