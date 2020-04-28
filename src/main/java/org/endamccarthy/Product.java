package org.endamccarthy;

public class Product {

  private String description, location;
  private double price;
  private int quantity;

  public Product() {
    this("", "", 0.00, 0);
  }

  public Product(String description, String location, double price, int quantity) {
    setDescription(description);
    setLocation(location);
    setPrice(price);
    setQuantity(quantity);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity += quantity;
  }

  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    Product b = (Product) other;
    return description.equals(b.description) && price == b.price && location.equals(b.location);
  }

  public String toString() {
    return "Description: " + getDescription() + ", Location: " + getLocation() + ", Price: "
        + getPrice() + ", Quantity: " + getQuantity();
  }

}
