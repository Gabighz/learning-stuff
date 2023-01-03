/**
 * This class can construct a binary search tree.
 * It has get and set methods for the root node, it is able to insert profiles
 * by the alphabetical order of their names, and to print the tree in that order.
 *
 * @author Gabriel Ghiuzan
 *
 */

public class BST {

  // Stores the root node of the binary search tree.
  private BSTNode rootNode;

  /**
	 * Constructs an empty BST.
	 */
  public BST () {
    this.rootNode = null;

  }

  /**
   * @return The root node.
   */
  public BSTNode getRoot () {
    return rootNode;

  }

  /**
   * @param rootNode Resets the root node.
   */
  public void setRoot (BSTNode rootNode) {
    this.rootNode = rootNode;

  }

  /**
   * @param profile Inserts a profile to the tree.
   * The profile to be inserted becomes root node if there is no root node.
   * Otherwise, the tree is searched and the profile is placed in its alphabetical order.
   */
  public void insertProfile (Profile profile) {

    BSTNode node = new BSTNode (profile);

    if (this.rootNode == null) {
      setRoot (node);

    } else {
      searchTree (rootNode, node);

    }

  }

  /**
   * @param currentNode The current node in the tree that is being checked.
   * @param node The node to be inserted in the tree.
   *
   * The tree is traversed relative to the alphabetical order of node to currentNode.
   * When node is before or after currentNode and the place node should be in is null,
   * node is inserted there. Otherwise, the traversal continues.
   */
  private void searchTree (BSTNode currentNode, BSTNode node) {

    // Returns a negative or positive integer based on whether node is
    // before or after currentNode in the alphabetical order.
    int resultComparison = node.getProfile().getName().toLowerCase().compareTo(currentNode.getProfile().getName().toLowerCase());

    // This condition is fulfilled if node is before currentNode in the alphabetical order.
    if (resultComparison < 0) {

      if (currentNode.getLeft() == null) {
        currentNode.setLeft (node);

      } else {
        searchTree (currentNode.getLeft(), node);

      }

    }

    // This condition is fulfilled if node is after currentNode in the alphabetical order.
    if (resultComparison > 0) {

      if (currentNode.getRight() == null) {
        currentNode.setRight (node);

      } else {
        searchTree (currentNode.getRight(), node);

      }

    }

  }

  /**
	 * Calls in order traversal on the rootNode in order to print the binary tree
   * in an alphabetical order.
	 */
  public void printAlphabetical () {
    inOrder (getRoot());

  }

  /**
	 * Recursive implementation of in order traversal.
	 */
  private void inOrder (BSTNode n) {

    if (n.getLeft() != null){
      inOrder (n.getLeft());

    }

    // Prints the name of the profile contained in the node.
    System.out.println (n.getProfile().getName());

    if (n.getRight() != null){
      inOrder (n.getRight());

    }

  }

}
