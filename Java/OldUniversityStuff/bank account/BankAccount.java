public class BankAccount {

  private int accountNumber;
  private int sortCode;

  public BankAccount (int accountNumber, int sortCode) {
    this.accountNumber = accountNumber;
    this.sortCode = sortCode;

  }

  public int getAccountNumber () {
    return accountNumber;

  }

  public int getSortCode () {
    return sortCode;

  }

  public boolean equals ( Object obj ) {
    // If the references match then we then this and obj point to
    // the same object. Therefore, they are equal.
    if (this == obj) {
      return true;

    }
    // This object ( which exists ) can never be equal to a null
    // reference.
    if (obj == null) {
      return false;

    }
    // Now we know both objects exist. We need to check that their
    // classes match.
    if (obj.getClass () != this.getClass ()) {
      return false;

    }
    // Both this and obj exist and are of the same class.
    // Now we can compare the account numbers and sort codes.
    BankAccount other = (BankAccount) obj;

    if ((other.getSortCode() == this.getSortCode()) & (other.getAccountNumber() == this.getAccountNumber())){
      return true;

    } else {
      return false;

    }

  }

  public int hashCode() {
    int hash = 1;
    hash = hash * 17 + accountNumber;
    hash = hash * 31 + sortCode;
    return hash;
  }

}
