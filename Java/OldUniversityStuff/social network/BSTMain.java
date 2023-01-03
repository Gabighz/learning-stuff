/**
 * This class is meant to test the BST class.
 *
 * @author Gabriel Ghiuzan
 *
 */

public class BSTMain {

  public static void main (String[] args) {

    BST tree = new BST ();

    Profile firstProfile = new Profile ("Mark", 20, 10, 1990, "Brighton", "United Kingdom", "British", new String[] {"rugby", "beer", "television"});
    Profile secondProfile = new Profile ("Steve", 15, 8, 1989, "Milan", "Italy", "Italian", new String[] {"books", "gardening", "crafting"});
    Profile thirdProfile = new Profile ("Casper", 10, 2, 1970, "London", "United Kingdom", "British", new String[] {"football", "fishing", "philosophy"});

    tree.insertProfile (firstProfile);
    tree.insertProfile (secondProfile);
    tree.insertProfile (thirdProfile);
    tree.printAlphabetical ();
    System.out.println ();

    Profile fourthProfile = new Profile ("Daniel", 10, 2, 1970, "London", "United Kingdom", "British", new String[] {"football", "fishing", "philosophy"});
    tree.insertProfile (fourthProfile);

    tree.printAlphabetical();
    System.out.println ();
    System.out.println (tree.getRoot().getLeft().getRight().getProfile().getName());

  }

}
