package org.endamccarthy;

public class Product {

  private String description, location;
  private double price;

  public Product() {
    this.description = "";
    this.location = "";
    this.price = 0.00;
  }

  public Product(String description, String location, double price) {
    setDescription(description);
    setLocation(location);
    setPrice(price);
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


}
