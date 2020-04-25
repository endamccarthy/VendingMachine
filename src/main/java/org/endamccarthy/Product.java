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

  private void setDescription(String description) {
    this.description = description;
  }

  public String getLocation() {
    return location;
  }

  private void setLocation(String location) {
    this.location = location;
  }

  public double getPrice() {
    return price;
  }

  private void setPrice(double price) {
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

}
