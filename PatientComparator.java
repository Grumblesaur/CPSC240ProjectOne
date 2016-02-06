import java.util.Comparator;

/** Allow for comparison by ID. */
class PatientComparator implements Comparator<Patient> {
	public int compare(Patient p1, Patient p2) {
		return Integer.valueOf(p1.getPatientID()).compareTo(
			Integer.valueOf(p2.getPatientID()));
	}
}



