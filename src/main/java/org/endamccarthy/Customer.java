package org.endamccarthy;

/**
 * Customer
 * <p>
 * A sub-class of User used to represent a customer of the vending machine. The details for each
 * customer are read in from a customer file and a new Customer object is created.
 */
public class Customer extends User {

  private double balance;

  /**
   * Default Constructor
   * <p>
   * The default constructor in the super-class will first be called before returning to this one.
   * Sets the balance of the customer to 0.00 by default.
   */
  public Customer() {
    setBalance(0.00);
  }

  /**
   * Overloaded Constructor
   * <p>
   * Passes attributes to the overloaded constructor in the super-class. Sets the balance to the
   * value passed in.
   *
   * @param username This is a String representing the username of the user.
   * @param password This is a String representing the password of the user.
   * @param balance  This is a double representing the balance of the user.
   */
  public Customer(String username, String password, double balance) {
    super(username, password);
    setBalance(balance);
  }

  /**
   * getBalance
   *
   * @return A double representing the balance of the user.
   */
  public double getBalance() {
    return (double) Math.round(balance * 100) / 100;
  }

  /**
   * setBalance
   *
   * @param balance This is a double which is added to the user balance to adjust the value.
   */
  public void setBalance(double balance) {
    this.balance += (double) Math.round(balance * 100) / 100;
  }

  /**
   * equals
   * <p>
   * Overridden method from the Object class. Determines if 2 Customer objects contain identical
   * attributes.
   *
   * @param other This is the other Customer object used to determine equality.
   * @return A boolean (true if Customers are identical, false otherwise).
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Customer)) {
      return false;
    }
    Customer b = (Customer) other;
    return getUsername().equals(b.getUsername()) && getPassword().equals(b.getPassword())
        && getBalance() == b.getBalance();
  }

  /**
   * toString
   * <p>
   * Overridden method from the Object class.
   *
   * @return A String containing the value of the Customer username and balance (password excluded
   * for security)
   */
  @Override
  public String toString() {
    return "Username: " + getUsername() + ", Balance: " + getBalance();
  }
}
