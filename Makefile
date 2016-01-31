runpt: PatientTester.class
	java PatientTester

PatientTester.class: Patient.java PatientTester.java
	javac PatientTester.java

clean:
	rm -f *.class *.html

docs: Patient.java PatientTester.java
	javadoc -author -version Patient.java
