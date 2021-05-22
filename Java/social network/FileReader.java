/**
 * This class reads profiles from a text file and then populates a BST
 * with these profiles. Also prints profiles as they are created.
 *
 * @author Gabriel Ghiuzan
 *
 */

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class FileReader {

  /**
	 * Constructs a profile.
	 *
	 * @param line the scanner of the line being read
	 * @return the constructed Profile object
	 */
  public static Profile constructProfile (Scanner line) {

    String name = line.next();
    int day = line.nextInt();
    int month = line.nextInt();
    int year = line.nextInt();
    String town = line.next();
    String country = line.next();
    String nationality = line.next();
    String[] interests = line.next().split(";");

    Profile newProfile = new Profile (name, day, month, year, town, country, nationality, interests);

    System.out.println (newProfile);

    return newProfile;

  }

  /**
	 * Reads the data file used by the program and
   * returns the constructed binary search tree.
   * Also prints profiles in FileReaderMain as they are inserted.
	 *
	 * @param in the scanner of the file
	 * @return the binary search tree represented by the data file
	 */
  public static BST readProfiles (Scanner in) {

    BST tree = new BST ();

    while (in.hasNextLine()) {
			String currentLine = in.nextLine();
			Scanner line = new Scanner(currentLine).useDelimiter(",");

      tree.insertProfile (constructProfile(line));

			line.close();
		}

		in.close();

    return tree;

  }

  /**
	 * Reads a file and returns a binary search tree of profiles from this file.
	 *
	 * @param filename the name of the file
	 * @return the binary search tree from the file
	 */
  public static BST readFile (String filename) {

    File inputFile = new File (filename);
    Scanner in = null;

    try {
      in = new Scanner (inputFile);

    }
    catch (FileNotFoundException e) {
      System.out.println ("Cannot open " + filename);
      System.exit (0);

    }

    return FileReader.readProfiles(in);

  }

  /**
	 * Reads the file which contains the edge list and returns it.
	 *
	 * @param filename the name of the file
	 * @return the edge list in the file
	 */
  public static ArrayList<String> readEdgeFile (String filename) {

    File inputFile = new File (filename);
    Scanner in = null;

    try {
      in = new Scanner (inputFile);

    }
    catch (FileNotFoundException e) {
      System.out.println ("Cannot open " + filename);
      System.exit (0);

    }

    // Stores each edge as a String.
    ArrayList<String> edgeList = new ArrayList<String> ();

    while (in.hasNextLine()) {
      String currentLine = in.nextLine();
      Scanner line = new Scanner(currentLine);

      edgeList.add (line.next());

      line.close();

    }

		in.close();

    return edgeList;

  }

}
