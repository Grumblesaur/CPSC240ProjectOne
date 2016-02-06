run: all
	java EmergencyRoom

all: EmergencyRoom.class PatientQueue.class Patient.class

EmergencyRoom.class: EmergencyRoom.java
	javac EmergencyRoom.java

PatientQueue.class: PatientQueue.java
	javac PatientQueue.java

Patient.class: Patient.java
	javac Patient.java

clean:
	rm -f *.class *.html

docs: Patient.java PatientTester.java
	javadoc -author -version PatientQueue.java
