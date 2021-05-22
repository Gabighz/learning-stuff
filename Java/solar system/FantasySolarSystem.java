/*
* This class has the purpose of modelling a fantasy solar system.
*
* For this, certain algorithmic steps are followed.
* Firstly, we receive as input the name of this solar system and the name of each
* planet until the user types 'done'.
*
* Secondly, we generate the mass and distance values within an appropriate range
* for each Planet object.
*
* Thirdly, we add the Planet object to a certain Collection which is abstracted
* and we print the summary of its values.
*
* Finally, we print a table containing the values of all the Planet objects.
*/


import java.util.Scanner;
import java.util.Random;

public class FantasySolarSystem{

  public static void main(String[] args){

    final int MINIMUM_DISTANCE = 1;
    final int RANDOM_NUMBER_RANGE_LIMIT = 50;

    Scanner in = new Scanner(System.in);
    Random rnd = new Random();
    boolean done = false;
    int planetNumber = 0;

    /*
    * We ask the user for input about the name of the solar system.
    * Then, inside a loop, we ask for the name of each planet
    * until the user types 'done'.
    */

    System.out.print("Enter the name of the solar system: ");
    String systemName = in.nextLine();

    SolarSystem FantasySystem = new SolarSystem(systemName);

    System.out.print("\nNow enter planet names - type 'done' to finish");

    do {

      System.out.print("\nEnter name: ");
      String planetName = in.nextLine();

      if (planetName.equalsIgnoreCase("done")){

        done = true;
        System.out.print("\n");

      } else {

        /*
        * We generate the mass and distance values. Both of them have their upper
        * limit set to 50 by the appropriate constant integer. The distance value
        * has to be more than 1.
        */

        double mass = rnd.nextDouble()*RANDOM_NUMBER_RANGE_LIMIT;
        double distance;

        do {

            distance = rnd.nextDouble()*RANDOM_NUMBER_RANGE_LIMIT;

          } while (distance <= MINIMUM_DISTANCE);

        /*
        * We call a method which creates a Planet object with the generated
        * values and a method which prints the summary of its values. These methods
        * are abstracted, being called from the SolarSystem class.
        */

        FantasySystem.addPlanet(planetName, mass, distance);
        FantasySystem.printSummary(planetNumber);

        /*
        * I use this integer to identify the index number of each Planet object so
        * we can easily print its values in the printSummary method.
        */
        planetNumber += 1;

      }

    } while(!done);

    /*
    * This method is called from the SolarSystem class and prints a table containing
    * the values of all the Planet objects.
    */

    FantasySystem.printTable();

  }

}
