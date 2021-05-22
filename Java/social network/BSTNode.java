/**
 * This class can construct a binary search tree node.
 * It has get and set methods for the left and right references
 * and for the profile contained in the node.
 *
 * @author Gabriel Ghiuzan
 *
 */

public class BSTNode {

  // Stores the reference to the left node.
  private BSTNode left;

  // Stores the reference to the right node.
  private BSTNode right;

  // Stores a Profile in the node.
  private Profile data;

  /**
   * Constructs a BSTNode.
   * @param profile The profile to be contained in the node.
   */
  public BSTNode (Profile data) {
    this.data = data;

  }

  /**
   * @return The reference to the right node.
   */
  public BSTNode getRight () {
    return right;

  }

  /**
   * @return The reference to the left node.
   */
  public BSTNode getLeft () {
    return left;

  }

  /**
   * @return The Profile object which is contained in the node.
   */
  public Profile getProfile () {
    return data;

  }

  /**
   * @param left Resets the reference to the left node.
   */
  public void setLeft (BSTNode left) {
    this.left = left;

  }

  /**
   * @param right Resets the reference to the right node.
   */
  public void setRight (BSTNode right) {
    this.right = right;

  }

  /**
   * @param Profile Resets the Profile object.
   */
  public void setProfile (Profile data) {
    this.data = data;

  }

}
