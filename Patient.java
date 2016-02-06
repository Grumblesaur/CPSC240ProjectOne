/** Stores information about a patient awaiting assistance in ER.
 * @author James Murphy
 * @version 2.0 January 6th 2016
 */

import java.time.LocalDateTime;
import java.lang.Integer;
import java.util.Comparator;

class Patient implements Comparable<Patient> {
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
		arrivalTime = LocalDateTime.now();
		// to be set upon addition to PatientQueue
		ID = 0;
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
	
	/** Set the patient's ID number.
	 * To be done upon adding patient to the PatientQueue.
	 * @param ID a unique six digit int.
	 */
	public void setPatientID(int ID) {
		this.ID = ID;
		ID++;
	}
	
	/** Obtain the patient's ID number.
	 * @return An int, the patient's ID number.
	 */
	public int getPatientID() {
		return this.ID;
	}
	
	/* Borrowed code from the following site to figure out how to implement
		Comparable interface for Patient class:
		https://www.caveofprogramming.com/java-collections-framework/
		natural-order-comparable-collection-java-collections-framework-
		video-tutorial-part-8.html
	*/
	
	/** Generate a string representing Patient object for testing. */
	@Override
	public String toString() {
		return this.getFullName() + " (ID: " +
			String.format("%06d", ID) + ").";
	}
	
	/** Determine if one Patient is the same as another.
	 * @param obj An object.
	 * @return A boolean, true if equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Patient other = (Patient) obj;
		if (ID == 0) {
			if (other.ID != 0) {
				return false;
			}
		} else if (ID != other.ID) {
			return false;
		}
		return true;
	}
		
		
	/** Compares objects to allow for a natural ordering.
	 * @param p A patient object.
	 */
	@Override
	public int compareTo(Patient p) {
		/* Invert expected values due to Java min-heap nonsense. */
		if (this.triagePriority > p.triagePriority) {
			return -1;
		} else if (this.triagePriority < p.triagePriority) {
			return 1;
		} else {
			return this.arrivalTime.compareTo(p.arrivalTime);
		}
	}
	
}


