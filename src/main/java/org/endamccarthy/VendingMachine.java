package org.endamccarthy;

import java.util.ArrayList;

/**
 * VendingMachine
 * <p>
 * A class used to represent a vending machine. Stores products in an ArrayList. An object of this
 * class is created in VendingMachineSimulation. It is then passed to a menu which requires a
 * vending machine object to run.
 */
public class VendingMachine {

  final public static int MAX_PRODUCTS = 9;
  final public int MAX_STOCK_PER_PRODUCT = 10;
  final public static String FILENAME_PRODUCTS = "Products.dat";
  private ArrayList<Product> products;

  /**
   * Default Constructor
   * <p>
   * Calls the setProducts method.
   */
  public VendingMachine() {
    setProducts();
  }

  /**
   * getProducts
   *
   * @return An ArrayList of Product objects.
   */
  public ArrayList<Product> getProducts() {
    return products;
  }

  /**
   * setProducts
   * <p>
   * Uses the FileInputService class to read in products from a file. Stores the products in an
   * ArrayList called products.
   */
  private void setProducts() {
    String description, location;
    double price;
    int quantity;
    products = new ArrayList<>();
    // read products in from file
    ArrayList<String[]> temp = FileInputService.readFromFile(FILENAME_PRODUCTS);
    // if the file was read correctly and the number of lines in the file was correct...
    if (temp != null && temp.size() <= MAX_PRODUCTS) {
      for (String[] line : temp) {
        if (line.length == 4) {
          // prepare and validate each item in the line
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
          // create a new product based on the line that was read in
          products.add(new Product(description, location, price, quantity));
        }
      }
    }
    // else show warning message
    else {
      AlertBox.displayBox("Warning",
          "There was a problem loading the product data.\nPlease ensure the correct filename is specified.",
          "Close");
    }
  }

}
