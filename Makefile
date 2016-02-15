run: all
	java EmergencyRoom

all: EmergencyRoom.class PatientQueue.class Patient.class PatientComparator.java

EmergencyRoom.class: EmergencyRoom.java
	javac EmergencyRoom.java

PatientQueue.class: PatientQueue.java
	javac PatientQueue.java

PatientComparator.class: PatientComparator.java
	javac PatientComparator.java

Patient.class: Patient.java
	javac Patient.java

clean:
	rm -f *.class *.html package-list *.js *.css *.zip

docs: Patient.java PatientTester.java
	javadoc -author -version PatientQueue.java Patient.java

zip: Patient.java PatientQueue.java PatientComparator.java EmergencyRoom.java ProjectOneTestMethodSummary.pdf Makefile
	zip CPSC240ProjectOne.zip *.java *.pdf Makefile
