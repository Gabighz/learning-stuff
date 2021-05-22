/**
 * This class can construct the profile of a person.
 * It also has get and set methods for their personal information.
 *
 * @author Gabriel Ghiuzan
 *
 */

import java.util.ArrayList;

public class Profile {

  // Stores the name of the person
  private String name;

  // Stores the date of birth of the person as three separate integers
  private int day, month, year;

  // Stores the town of residence of the person
  private String town;

  // Stores the country of residence of the person
  private String country;

  // Stores the nationality of the person
  private String nationality;

  // Stores the interests of the person as an array of strings
  private String[] interests;

  // Stores the friends of the person
  private ArrayList<Profile> friends;

  /**
   * Constructs a Profile
   *
   * @param name The name of the person.
   * @param day The day of birth of the person.
   * @param month The month of birth of the person.
   * @param year The year of birth of the person.
   * @param town The town of residence of the person.
   * @param country The country of residence of the person.
   * @param nationality The nationality of the person.
   * @param interests The interests of the person.
   */
  public Profile (String name, int day, int month, int year, String town, String country, String nationality, String[] interests) {
    this.name = name;
    this.day = day;
    this.month = month;
    this.year = year;
    this.town = town;
    this.country = country;
    this.nationality = nationality;
    this.interests = interests;

    friends = new ArrayList<Profile> ();

  }

  /**
   * Method to convert a profile to a string.
   */
  public String toString () {
    String result = "This is a profile. \n";
    result += "The name of the person is: " + name + "\n";
    result += "Their date of birth of is: " + day + "/" + month + "/" + year + "\n";
    result += "Their town of residence is: " + town + "\n";
    result += "Their country of residence is: " + country + "\n";
    result += "Their nationality is: " + nationality + "\n";

    String interestsConverted = "";

    // Concatenates each element of the array which represents interests to a string.
    for (String interest: interests) {
      interestsConverted += interest + ", ";

    }

    result += "Their interests are: " + interestsConverted + "\n";

    String friendsConverted = "";

    // Concatenates each element of the array which represents friends to a string.
    for (Profile friend: friends) {
      friendsConverted += friend.getName() + ", ";

    }

    result += "Their friends are: " + friendsConverted + "\n";
    result += "The size of their list of friends is: " + numOfFriends() + "\n";
    return result;
  }

  /**
   * @return The name of the person.
   */
  public String getName () {
    return name;

  }

  /**
   * @return The date of birth of the person.
   */
  public String getDateOfBirth () {
    return String.format("%d/%d/%d", day, month, year);

  }

  /**
   * @return The town of residence of the person.
   */
  public String getTown () {
    return town;

  }

  /**
   * @return The date of birth of the person.
   */
   public String getCountry () {
     return country;

   }

  /**
   * @return The nationality of the person.
   */
  public String getNationality () {
    return nationality;

  }

  /**
   * @return The interests of the person.
   */
  public String[] getInterests () {
    return interests;

  }

  /**
   * @return The friend which is stored at the provided index in the list of friends.
   */
  public Profile getFriend (int index) {
    return friends.get (index);

  }

  /**
   * @return The number of friends for this profile.
   */
  public int numOfFriends () {
    return friends.size ();

  }

  /**
   * @param town Resets the town of residence of the person.
   */
  public void setTown (String town) {
    this.town = town;

  }

  /**
   * @param country Resets the country of residence of the person.
   */
  public void setCountry (String country) {
    this.country = country;

  }

  /**
   * @param nationality Resets the nationality of the person.
   */
  public void setNationality (String nationality) {
    this.nationality = nationality;

  }

  /**
   * @param interests Resets the interests of the person.
   */
  public void setInterests (String[] interests) {
    this.interests = interests;

  }

  /**
   * @param profile Adds a friend to the friends list.
   */
  public void addFriend (Profile profile) {
    friends.add (profile);

  }

  /**
   * @param obj Object that is compared against.
   */
  public boolean equals ( Object obj ) {
    // If the references match then we then this and obj point to
    // the same object. Therefore, they are equal.
    if (this == obj) {
      return true;

    }
    // This object ( which exists ) can never be equal to a null
    // reference.
    if (obj == null) {
      return false;

    }
    // Now we know both objects exist. We need to check that their
    // classes match.
    if (obj.getClass() != this.getClass()) {
      return false;

    }
    // Both this and obj exist and are of the same class.
    // Now we compare the names and dates of birth.
    Profile other = (Profile) obj;

    if ((other.getName().equals(this.getName())) && (other.getDateOfBirth().equals(this.getDateOfBirth()))){
      return true;

    } else {
      return false;

    }

  }

  /**
   * @return The hash code of a Profile object.
   */
  public int hashCode () {
    int hash = 1;
    hash = hash * 17 + (getName() == null ? 0 : getName().hashCode());
    hash = hash * 31 + (getDateOfBirth() == null ? 0 : getDateOfBirth().hashCode());
    return hash;
  }


}
