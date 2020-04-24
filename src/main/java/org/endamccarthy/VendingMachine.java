package org.endamccarthy;

import java.util.ArrayList;
import java.util.HashMap;

public class VendingMachine {

  final public static int MAX_PRODUCTS = 9;
  private static ArrayList<Product> products;
  private static HashMap<String, User> userInfo;

  public VendingMachine() {
    // read products in from file
    Product productOne = new Product("Twix", "A1", 0.80);
    Product productTwo = new Product("Snickers", "A2", 0.85);
    products = new ArrayList<>();
    products.add(productOne);
    products.add(productTwo);

    // read users in from file
    User userOne = new Customer("Enda", "123456", 2.50);
    User userTwo = new Admin("John", "password123");
    userInfo = new HashMap<>();
    userInfo.put(userOne.getUsername(), userOne);
    userInfo.put(userTwo.getUsername(), userTwo);
  }

  public static User getUserInfo(String username) {
    return userInfo.get(username);
  }

  public void getProducts() {
    System.out.println("TODO....");
  }

}
