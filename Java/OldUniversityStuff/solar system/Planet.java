/*
* This class has the purpose of modelling each particular Planet object.
*
* For this, it initializes the values which will be assigned to the object, and
* multiple methods which perform actions on this object. These actions are getting
* the values of the Planet, its calculated orbital period, and a String concatenated
* with the values of it.
*/

public class Planet{

  /*
  * I made these final fields because as soon as an object is created and
  * assigned corresponding values, these values do not need to be changed at all.
  * This is also why I chose not to have set methods, since we wouldn't use them
  * outside this class and thus are not needed.
  */
  private final String name;
  private final double mass;
  private final double distance;


  /*
  * This constructor assigns name, mass, and distance values to a Planet object.
  */
  public Planet(String planetName, double planetMass, double planetDistance){

    name = planetName;
    mass = Math.round(planetMass*1000)/1000.0;
    distance = Math.round(planetDistance*1000)/1000.0;

  }

  /*
  * The following four methods have the purpose to get certain values about the
  * Planet object, namely its name, mass, distance. The last value is calculated
  * in the fourth method.
  */

  public String getName(){

    return name;

  }

  public double getMass(){

    return mass;

  }

  public double getDistance(){

    return distance;

  }

  public double getPeriod(){

    return Math.round(Math.sqrt(distance*distance*distance)*1000)/1000.0;

  }

  /*
  * This method concatenates the values of the Planet object to a String, and returns
  * it.
  */

  public String toString(){

    return "Planet "+ name +" has a mass of "+ mass +" Earths,"
    + " is "+ distance +"AU from its star, and orbits in "+ getPeriod() +" years";

  }

}
