/*
* This class has the purpose of modelling a solar system.
*
* For this, it initializes an ArrayList to hold a collection of Planet objects,
* a constant string which will represent the name of the modelled solar system,
* and multiple methods which perform actions to the objects. These actions are
* adding a new Planet object with certain parameters to the ArrayList,
* and displaying certain information about these objects.
*/


import java.util.ArrayList;

public class SolarSystem{

  private ArrayList<Planet> ourSystem = new ArrayList<>();
  private final String systemName;

  /*
  * Firstly, we have a constructor with the same name as the class which takes
  * a String as a parameter and assigns the name of the solar system to that String.
  */
  public SolarSystem(String name){

    systemName = name;

  }

  /*
  * This method creates a new Planet object which has name, mass, and distance
  * values. Then the object is added in the ourSystem ArrayList.
  */

  public void addPlanet(String name, double mass, double distance){

    ourSystem.add(new Planet(name, mass, distance));

  }

  /*
  * This method creates a number of concatenated Strings
  * (equal to the amount of objects in the ArrayList), each containing the values
  * of a particular Planet object. The resulting String is then returned.
  */

  public String toString(){

    String toPrint = systemName + "\n";

    for(Planet element : ourSystem){

      toPrint += element + "\n";

    }

    return toPrint;

  }

  /*
  * This method is called in the FantasySolarSystem class to print a table
  * which contains the values of all the Planet objects.
  */

  public void printTable(){

    System.out.println("Name\t\tMass\t\tDistance\tPeriod (years)");

    for(Planet element : ourSystem){

      System.out.println(element.getName() + "\t\t" +
                        element.getMass() + "\t\t" +
                        element.getDistance() + "\t\t" +
                        element.getPeriod() + "\n");

    }

  }

  /*
  * This method is called in the FantasySolarSystem class to print the values of
  * a particular Planet object which is held in the planetNumber index of the
  * ourSystem ArrayList.
  */

  public void printSummary(int planetNumber){

    System.out.println("\n" + ourSystem.get(planetNumber));

  }

}
