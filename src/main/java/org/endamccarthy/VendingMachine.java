package org.endamccarthy;

import java.util.ArrayList;
import java.util.HashMap;

public class VendingMachine {

  final public static int MAX_PRODUCTS = 9;
  private static ArrayList<Product> products;
  private static HashMap<String, User> userInfo;

  public VendingMachine() {
    // read products in from file
    Product productOne = new Product("Twix", "A1", 0.80, 3);
    Product productTwo = new Product("Snickers", "A2", 0.85, 4);
    Product productThree = new Product("Twix", "A3", 3.50, 5);
    Product productFour = new Product("Snickers", "B1", 0.85, 2);
    Product productFive = new Product("Twix", "B2", 0.80, 1);
    Product productSix = new Product("Snickers", "B3", 0.85, 0);
    Product productSeven = new Product("Twix", "C1", 0.80, 4);
    Product productEight = new Product("Snickers", "C2", 0.85, 9);
    Product productNine = new Product("Snickers", "C3", 0.85, 10);
    products = new ArrayList<>();
    products.add(productOne);
    products.add(productTwo);
    products.add(productThree);
    products.add(productFour);
    products.add(productFive);
    products.add(productSix);
    products.add(productSeven);
    products.add(productEight);
    products.add(productNine);

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

  public ArrayList<Product> getProducts() {
    return products;
  }

}
