/**
 * This class can construct the medical data of a patient.
 * It also has get and set methods for their personal information.
 *
 * @author Gabriel Ghiuzan
 *
 */

public class Patient implements Comparable<Patient> {

  // Stores the first name of the patient
  String firstName;

  // Stores the last name of the patient
  String lastName;

  // Stores the illness of the patient
  String illness;

  // Stores the severity of the illness
  int illnessSeverity;

  /**
   * Constructs a Patient.
   *
   * @param firstName the first name of the patient
   * @param lastName the last name of the patient
   * @param illness the illness of the patient
   * @param illnessSeverity the severity of the illness
   */
  public Patient (String firstName, String lastName, String illness, int illnessSeverity) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.illness = illness;
    this.illnessSeverity = illnessSeverity;

  }

  /**
   * Method to convert a Patient to a string.
   */
  public String toString () {
    String result = "This is a patient. \n";
    result += "Their first name is: " + firstName + "\n";
    result += "Their last name is: " + lastName + "\n";
    result += "Their illness is: " + illness + "\n";
    result += "The severity of their illness is: " + illnessSeverity + "\n";
    return result;
  }

  /**
   * @return the first name of the patient
   */
  public String getFirstName () {
    return firstName;

  }

  /**
   * @return the last name of the patient
   */
  public String getLastName () {
    return lastName;

  }

  /**
   * @return the illness of the patient
   */
  public String getIllness () {
    return illness;

  }

  /**
   * @return the severity of the illness
   */
  public int getIllnessSeverity () {
    return illnessSeverity;

  }

  /**
   * @param town Resets the first name of the patient
   */
  public void setFirstName (String firstName) {
    this.firstName = firstName;

  }

  /**
   * @param town Resets the last name of the patient
   */
  public void setLastName (String lastName) {
    this.lastName = lastName;

  }

  /**
   * @param town Resets the illness of the patient
   */
  public void setIllness (String illness) {
    this.illness = illness;

  }

  /**
   * @param town Resets the severity of the illness
   */
  public void setIllnessSeverity (int illnessSeverity) {
    this.illnessSeverity = illnessSeverity;

  }

  /**
   * @param otherPatient another patient
   * @return relative priority
   */
  public int compareTo (Patient otherPatient) {

    if (this.getIllnessSeverity() < otherPatient.getIllnessSeverity()) {
      return +1;

    }

    if (this.getIllnessSeverity() > otherPatient.getIllnessSeverity()) {
      return -1;

    } else {
      return 0;

    }

  }


}
