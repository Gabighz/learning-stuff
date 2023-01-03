import java.io.*;
import java.util.Scanner;
import java.util.PriorityQueue;

public class Main {

  public static void main (String[] args) {

    PriorityQueue<Patient> patients = readPatientFile("patients.txt");

    System.out.println("The queue elements:");

    while (!patients.isEmpty()) {
      System.out.println(patients.poll());

    }

  }

  public static Patient constructPatient (Scanner in) {

    String firstName = in.next();
    in.nextLine();
    String lastName = in.next();
    in.nextLine();
    String illness = in.next();
    in.nextLine();
    int illnessSeverity = in.nextInt();
    in.nextLine();

    return new Patient(firstName, lastName, illness, illnessSeverity);

  }

  public static PriorityQueue<Patient> readPatientFile (String filename) {

    File inputFile = new File (filename);
    Scanner in = null;

    try {
      in = new Scanner (inputFile);

    }
    catch (FileNotFoundException e) {
      System.out.println ("Cannot open " + filename);
      System.exit (0);

    }

    PriorityQueue<Patient> patients = new PriorityQueue<Patient>();

    while (in.hasNextLine()) {

      patients.add(constructPatient(in));

    }

		in.close();

    return patients;

  }

}
