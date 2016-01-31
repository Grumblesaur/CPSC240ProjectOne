runpt: PatientTester.class
	java PatientTester

PatientQueue.class: PatientQueue.java
	javac PatientQueue.java

PatientTester.class: Patient.java PatientTester.java
	javac PatientTester.java

clean:
	rm -f *.class *.html

docs: Patient.java PatientTester.java
	javadoc -author -version Patient.java
