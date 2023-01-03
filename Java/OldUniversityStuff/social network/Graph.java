/**
 * This class can construct a graph. This graph may represent the network of
 * friendships within a BST, given a list of edges.
 * Using this, a BST representing attendees to a party is created.
 *
 * @author Gabriel Ghiuzan
 *
 */


import java.io.*;
import java.util.Scanner;
import java.util.HashSet;
import java.util.ArrayList;

public class Graph {

  // Stores a binary search tree with potential attendees.
  private BST tree;

  // Stores the name of the file which contains the edge list.
  private String filename;

  /**
   * Constructs a Profile
   *
   * @param filename The name of the file containing the edge list.
   * @param tree The binary search tree which contains potential attendees.
   */
  public Graph (String filename, BST tree) {
    this.tree = tree;
    this.filename = filename;

  }

  /**
	 * Constructs a representation of a party's attendees.
	 *
	 * @param invitations The people invited to the party.
	 * @return the BST which represents actual attendees.
	 */
  public BST bigParty (ArrayList<Profile> invitations) {

    BST party = new BST();

    HashSet<Profile> possibleAttendees = new HashSet<Profile> ();

    for (Profile invited : invitations) {
      possibleAttendees.add(invited);

    }

    for (Profile attendee : possibleAttendees) {
      party.insertProfile(attendee);

    }

    return party;

  }

  /**
	 * Makes invitations to the people on the edge list and their friends.
	 *
	 * @return an array of invitations.
	 */
  public ArrayList<Profile> decideInvitations() {

    ArrayList<String> edgeList = FileReader.readEdgeFile(filename);

    HashSet<Profile> allFriends = addFriends(edgeList);
    ArrayList<Profile> invitations = new ArrayList<Profile> ();

    for (Profile profile : allFriends) {
      invitations.add (profile);

      for (int i = 0; i < profile.numOfFriends (); i ++) {
        invitations.add (profile.getFriend(i));

      }

    }

    return invitations;

  }

  /**
	 * Populates the friend list of Profiles mentioned in the edge list.
	 *
	 * @param line the scanner of the line being read
	 * @return the constructed Profile object
	 */
  public HashSet<Profile> addFriends (ArrayList<String> edgeList) {

    // Stores all the profiles mentioned by name in the edge list.
    HashSet<Profile> allFriends = new HashSet<Profile> ();

    for (String edge : edgeList) {
      String[] thisEdge = edge.split (",");

      Profile firstProfile = searchTree (this.tree.getRoot(), thisEdge[0]);
      Profile secondProfile = searchTree (this.tree.getRoot(), thisEdge[1]);

      firstProfile.addFriend (secondProfile);
      secondProfile.addFriend (firstProfile);

      allFriends.add (firstProfile);
      allFriends.add (secondProfile);

    }

    return allFriends;

  }

  /**
	 * Searches through the BST given to the constructor with the purpose of
   * identifying the profiles of the people mentioned in the edge list.
	 *
	 * @param currentNode The current node in the tree that is being checked.
   * @param name A name in the edge list.
	 * @return The Profile from the BST which matches the name.
	 */
  private Profile searchTree (BSTNode currentNode, String name) {

    if (currentNode.getProfile ().getName ().equals (name)) {
      return currentNode.getProfile();

    } else {

      // Returns a negative or positive integer based on whether the name is
      // before or after currentNode in the alphabetical order.
      int resultComparison = name.toLowerCase().compareTo(currentNode.getProfile().getName().toLowerCase());

      // This condition is fulfilled if node is before currentNode in the alphabetical order.
      if ((resultComparison < 0) && (currentNode.getLeft() != null)) {
          return searchTree(currentNode.getLeft(), name);

        }

      // This condition is fulfilled if node is after currentNode in the alphabetical order.
      if ((resultComparison > 0) && (currentNode.getRight() != null)) {
          return searchTree(currentNode.getRight(), name);

        }

      }

    return null;

  }

}
