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
    return (double) Math.round(balance * 100) / 100;
  }

  public void setBalance(double balance) {
    this.balance += (double) Math.round(balance * 100) / 100;
  }
}
