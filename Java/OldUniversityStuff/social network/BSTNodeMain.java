/**
 * This class is meant to test the BSTNode class.
 *
 * @author Gabriel Ghiuzan
 *
 */

public class BSTNodeMain {

  public static void main (String[] args) {

    Profile firstProfile = new Profile("Mark", 20, 10, 1990, "Brighton", "United Kingdom", "British", new String[] {"rugby", "beer", "television"});
    Profile secondProfile = new Profile("Steve", 15, 8, 1989, "Milan", "Italy", "Italian", new String[] {"books", "gardening", "crafting"});
    Profile thirdProfile = new Profile("Stephen", 10, 2, 1970, "London", "United Kingdom", "British", new String[] {"football", "fishing", "philosophy"});

    BSTNode firstNode = new BSTNode(firstProfile);
    BSTNode secondNode = new BSTNode(secondProfile);
    BSTNode thirdNode = new BSTNode(thirdProfile);

    firstNode.setLeft(secondNode);
    firstNode.setRight(thirdNode);
    firstNode.getRight().setProfile(secondProfile);
    System.out.println(thirdNode.getProfile());

    firstNode.getLeft().setProfile(firstProfile);
    firstNode.setProfile(thirdProfile);
    System.out.println(firstNode.getProfile());
    System.out.println(secondNode.getProfile());

  }

}
