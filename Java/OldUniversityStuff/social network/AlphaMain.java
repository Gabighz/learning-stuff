/**
 * This class is meant to test the printAlphabetical method.
 *
 * @author Gabriel Ghiuzan
 *
 */

public class AlphaMain {

  public static void main(String[] args){

    BST tree = FileReader.readFile("data/profiles.txt");

    tree.printAlphabetical();

  }

}
