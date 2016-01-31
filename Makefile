PatientTester.class: Patient.java PatientTester.java
	javac PatientTester.java

clean:
	rm -f *.class *.html

docs:
	javadoc --author --version Patient.java
