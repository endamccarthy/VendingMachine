package org.endamccarthy;

import java.util.ArrayList;
import java.util.HashMap;

public class VendingMachine {

  final public static int MAX_PRODUCTS = 9;
  final public static String FILENAME_PRODUCTS = "Products.dat";
  final public static String FILENAME_CLIENTS = "Clients.dat";
  final public static String FILENAME_ADMIN = "Admin.dat";
  private HashMap<String, User> userInfo;
  private ArrayList<Product> products;
  private ArrayList<String[]> temp;

  public VendingMachine() {
    setUserInfo();
    setProducts();
  }

  public User getUserInfo(String username) {
    return userInfo.get(username);
  }

  private void setUserInfo() {
    String username, password;
    double balance;
    userInfo = new HashMap<>();
    // read customers first
    temp = FileInputService.readFromFile(FILENAME_CLIENTS);
    if (temp != null) {
      for (String[] line : temp) {
        if (line.length == 3) {
          username = line[0].trim();
          try {
            balance = Double.parseDouble(line[1].trim());
          } catch (NumberFormatException e) {
            continue;
          }
          password = line[2].trim();
          userInfo.put(username, new Customer(username, password, balance));
        }
      }
    } else {
      System.out.println("File not found");
    }
    // read admin second
    temp = FileInputService.readFromFile(FILENAME_ADMIN);
    if (temp != null) {
      for (String[] line : temp) {
        if (line.length == 2) {
          username = line[0].trim();
          password = line[1].trim();
          userInfo.put(username, new Admin(username, password));
        }
      }
    } else {
      System.out.println("File not found");
    }
  }

  public ArrayList<Product> getProducts() {
    return products;
  }

  private void setProducts() {
    String description, location;
    double price;
    int quantity;
    // read products in from file
    products = new ArrayList<>();
    temp = FileInputService.readFromFile(FILENAME_PRODUCTS);
    if (temp != null && temp.size() <= MAX_PRODUCTS) {
      for (String[] line : temp) {
        if (line.length == 4) {
          description = line[0].trim();
          location = line[1].trim();
          try {
            price = Double.parseDouble(line[2].trim());
          } catch (NumberFormatException e) {
            continue;
          }
          try {
            quantity = Integer.parseInt(line[3].trim());
          } catch (NumberFormatException e) {
            continue;
          }
          products.add(new Product(description, location, price, quantity));
        }
      }
    } else {
      System.out.println("File not found");
    }
  }

}
