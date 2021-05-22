/**
 * This class is meant to test the Profile class.
 *
 * @author Gabriel Ghiuzan
 *
 */

public class ProfileMain {

  public static void main(String[] args){

    Profile firstProfile = new Profile("Mark", 20, 10, 1990, "Brighton", "United Kingdom", "British", new String[] {"rugby", "beer", "television"});
    Profile secondProfile = new Profile("Steve", 15, 8, 1989, "Milan", "Italy", "Italian", new String[] {"books", "gardening", "crafting"});

    firstProfile.addFriend(secondProfile);
    secondProfile.addFriend(firstProfile);

    System.out.println(firstProfile.toString());
    System.out.println(secondProfile.toString());

  }

}
