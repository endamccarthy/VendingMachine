package org.endamccarthy;

/**
 * Product
 * <p>
 * A class which represents a product in the vending machine. The details for each product are read
 * in from a product file and a new Product object is created.
 */
public class Product {

  private String description, location;
  private double price;
  private int quantity;

  /**
   * Default Constructor
   * <p>
   * Passes default product attributes to the overloaded constructor.
   */
  public Product() {
    this("", "", 0.00, 0);
  }

  /**
   * Overloaded Constructor
   * <p>
   * Saves the values passed in as the product attributes.
   *
   * @param description This is a String representing the name of the product (e.g Twix).
   * @param location    This is a String representing the product location in the vending machine
   *                    (e.g A1).
   * @param price       This is a double representing the price of the product in Euros (e.g 1.30).
   * @param quantity    This is an int representing the quantity of the product currently in stock
   *                    (e.g 10).
   */
  public Product(String description, String location, double price, int quantity) {
    setDescription(description);
    setLocation(location);
    setPrice(price);
    setQuantity(quantity);
  }

  /**
   * getDescription
   *
   * @return A String representing the product name.
   */
  public String getDescription() {
    return description;
  }

  /**
   * setDescription
   *
   * @param description This is a String representing the product name.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * getLocation
   *
   * @return A String representing the product location in the vending machine.
   */
  public String getLocation() {
    return location;
  }

  /**
   * setLocation
   *
   * @param location This is a String representing the product location in the vending machine.
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * getPrice
   *
   * @return A double representing the price of the product in Euros.
   */
  public double getPrice() {
    return price;
  }

  /**
   * setPrice
   *
   * @param price This is a double representing the price of the product in Euros.
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * getQuantity
   *
   * @return An int representing the quantity of the product in the vending machine.
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * setQuantity
   *
   * @param quantity This is an int which is added to the product quantity to adjust the value.
   */
  public void setQuantity(int quantity) {
    this.quantity += quantity;
  }

  /**
   * equals
   * <p>
   * Overridden method from the Object class. Determines if 2 Product objects contain identical
   * attributes.
   *
   * @param other This is the other Product object used to determine equality.
   * @return A boolean (true if Products are identical, false otherwise).
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Product)) {
      return false;
    }
    Product b = (Product) other;
    return getDescription().equals(b.getDescription()) && getLocation().equals(b.getLocation())
        && getPrice() == b.getPrice() && getQuantity() == b.getQuantity();
  }

  /**
   * toString
   * <p>
   * Overridden method from the Object class.
   *
   * @return A String containing the value of each Product attribute.
   */
  @Override
  public String toString() {
    return "Description: " + getDescription() + ", Location: " + getLocation() + ", Price: "
        + getPrice() + ", Quantity: " + getQuantity();
  }

}
