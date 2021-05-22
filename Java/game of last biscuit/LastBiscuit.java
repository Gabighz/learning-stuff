/*
 * This program splits each algorithmic task into an appropriate function.
 *
 * The first called function is the one which prints how many biscuits are left.
 * Initial input validation is performed to see if the user has typed
 * an integer. Then a second validation function checks the choice related to which barrel to pick.
 *
 * Afterwards, a function is called which checks if the provided input and the
 * chosen barrel are within the range of the remaining biscuits and, if it is
 * valid, biscuits are deducted from the chosen barrel.
 *
 * After each player's turn, a last function checks if the game has been won.
 */


import java.util.Scanner;

public class LastBiscuit{

  public static int ValidationTaken(int[] barrels, final int PLAYER_NUMBER, Scanner in){

    /*
    * This function checks if the first provided input is an integer.
    */


    int takenValue = 0; // This integer represents how many biscuits the user wants to take
    final int MINIMUM_TAKEN_VALUE = 1;
    System.out.print("Biscuits taken by player " + PLAYER_NUMBER + ": ");

    while (takenValue < MINIMUM_TAKEN_VALUE){
      while (!in.hasNextInt()){
          System.out.print("Please type valid input: ");
          in.next();
        }
      takenValue = in.nextInt();
    }


    return takenValue;

  }

  public static String ValidationBarrel(Scanner in, final String FIRST, final String SECOND, final String BOTH){

    /*
    * This function checks if the second provided input is one of the required options.
    */

    String barrel; // This string represents the barrel which the user has chosen.

    System.out.print("From barrel1 (one), barrel2 (two), or both (both)? ");
    do{
      barrel = in.nextLine();
    } while (!barrel.equalsIgnoreCase(FIRST) && !barrel.equalsIgnoreCase(SECOND) && !barrel.equalsIgnoreCase(BOTH));

    return barrel;

  }

  public static int[] DeductBiscuits(String whichBarrel, int[] barrels, int deductionValue){

    /*
    * This function checks the range of the input and
    * proceeds to deduct the desired amount of biscuits from the desired barrel.
    */

      if (whichBarrel.equals("one") && deductionValue <= barrels[0]){
        barrels[0] -= deductionValue;
      } else if (whichBarrel.equals("two") && deductionValue <= barrels[1]){
        barrels[1] -= deductionValue;
      } else if (whichBarrel.equals("both") && deductionValue <= barrels[1] && deductionValue <= barrels[0]){
        barrels[0] -= deductionValue;
        barrels[1] -= deductionValue;
      } else {
        System.out.println("Value not within range!");
        /* The last array element has the sole purpose of
        *returning if the input is not within range
        */
        barrels[2] = 0;
      }

    return barrels;

  }

  public static void PrintBiscuitsLeft(int[] barrels){
    System.out.println("Biscuits Left - Barrel 1: " + barrels[0]);
    System.out.println("Biscuits Left - Barrel 2: " + barrels[1]);
  }

  public static boolean CheckWin(int[] barrels, final int PLAYER_NUMBER){

    /*
    * This function checks if the win condition has been fulfilled
    * by one of the players.
    */

    boolean win = false;

    if ((barrels[0] == 0) && (barrels[1] == 0)){
      win = true;
      PrintBiscuitsLeft(barrels);
      System.out.println("Winner is player " + PLAYER_NUMBER);
    }

    return win;

  }

  public static void main(String[] args){

    Scanner in = new Scanner(System.in);
    boolean win = false;

    final int STARTING_AMOUNT_FIRST_BARREL = 6;
    final int STARTING_AMOUNT_SECOND_BARREL = 8;
    final int PLAYER_1 = 1;
    final int PLAYER_2 = 2;
    final String BARREL_1 = "one";
    final String BARREL_2 = "two";
    final String BOTH_BARRELS = "both";

    /* The last array element has the sole purpose of
    * being a 'truth value' of whether the input is within range
    * 1 = within range; 0 = not within range;
    * This helps to better manage the loop in which this function is contained
    * and enables a player to re-enter the input he provided within that turn.
    */
    int[] barrels = {STARTING_AMOUNT_FIRST_BARREL, STARTING_AMOUNT_SECOND_BARREL, 1};

    while (!win){
      boolean firstPlayer = false;
      boolean secondPlayer = false;

      /*
      * The game runs as long as the outer loop's condition is not fulfilled.
      * The nested loops define each player's turn and they can run again
      * in the same turn if the player's input is not within range.
      */

      while((!firstPlayer) && (!win)){
        PrintBiscuitsLeft(barrels);
        barrels[2] = 1;

        int takenValue = ValidationTaken(barrels, PLAYER_1, in);
        String barrel = ValidationBarrel(in, BARREL_1, BARREL_2, BOTH_BARRELS);
        barrels = DeductBiscuits(barrel, barrels, takenValue);
        if(barrels[2] != 0){
          firstPlayer = true;
        }
        win = CheckWin(barrels, PLAYER_1);
      }


      while((!secondPlayer) && (!win)){
        PrintBiscuitsLeft(barrels);
        barrels[2] = 1;

        int takenValue = ValidationTaken(barrels, PLAYER_2, in);
        String barrel = ValidationBarrel(in, BARREL_1, BARREL_2, BOTH_BARRELS);
        barrels = DeductBiscuits(barrel, barrels, takenValue);
        if(barrels[2] != 0){
          secondPlayer = true;
        }
        win = CheckWin(barrels, PLAYER_2);
      }

    }

  }
}
