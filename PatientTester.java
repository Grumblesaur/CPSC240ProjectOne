/** Tests Patient.java.
 * @author James Murphy
 * @version 1.0 January 29th 2016
 */

public class PatientTester {
	public static void main(String[] args) {
		// initialize counter for unique IDs
		int IDCounter = 1;
		
		Patient patient1 = new Patient("David", "Jones", 540);
		patient1.setArrivalTime();
		patient1.setPatientID(IDCounter++);
		
		System.out.println(patient1.toString());
		System.out.println(IDCounter);
		
		
	}
}
