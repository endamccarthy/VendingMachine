package org.endamccarthy;

import java.util.ArrayList;

public class VendingMachine {

  final public static int MAX_PRODUCTS = 9;
  final public static String FILENAME_PRODUCTS = "Products.dat";
  private ArrayList<Product> products;

  public VendingMachine() {
    setProducts();
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
    ArrayList<String[]> temp = FileInputService.readFromFile(FILENAME_PRODUCTS);
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
      AlertBox.displayBox("Warning",
          "There was a problem loading the product data.\nPlease ensure the correct filename is specified.",
          "Close");
    }
  }

}
