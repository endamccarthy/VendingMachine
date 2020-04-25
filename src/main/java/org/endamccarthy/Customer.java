package org.endamccarthy;

public class Customer extends User {

  private double balance;

  public Customer() {
    this.balance = 0.00;
  }

  public Customer(String username, String password, double balance) {
    super(username, password);
    setBalance(balance);
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance += balance;
  }
}
