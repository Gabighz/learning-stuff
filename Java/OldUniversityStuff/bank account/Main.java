
import java.util.HashSet;

public class Main {

  public static void main (String[] args){

    HashSet<BankAccount> accounts = new HashSet<BankAccount> ();

    accounts.add(new BankAccount(123000, 321000));
    accounts.add(new BankAccount(555000, 555000));
    accounts.add(new BankAccount(123000, 321000));

    for (BankAccount account : accounts) {
      System.out.printf("%d %d \n", account.getAccountNumber(), account.getSortCode());

    }

  }

}
