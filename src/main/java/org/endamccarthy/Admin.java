package org.endamccarthy;

/**
 * Admin
 * <p>
 * A sub-class of User used to represent an administrator of the vending machine. The details for
 * each admin are read in from an admin file and a new Admin object is created.
 */
public class Admin extends User {

  /**
   * Default Constructor
   * <p>
   * Calls on the default constructor in the super-class.
   */
  public Admin() {
    super();
  }

  /**
   * Overloaded Constructor
   * <p>
   * Passes attributes to the overloaded constructor in the super-class.
   *
   * @param username This is a String representing the username of the user.
   * @param password This is a String representing the password of the user.
   */
  public Admin(String username, String password) {
    super(username, password);
  }

  /**
   * equals
   * <p>
   * Overridden method from the Object class. Determines if 2 Admin objects contain identical
   * attributes.
   *
   * @param other This is the other Admin object used to determine equality.
   * @return A boolean (true if Admins are identical, false otherwise).
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Admin)) {
      return false;
    }
    Admin b = (Admin) other;
    return getUsername().equals(b.getUsername()) && getPassword().equals(b.getPassword());
  }

  /**
   * toString
   * <p>
   * Overridden method from the Object class.
   *
   * @return A String containing the value of the Admin username (password excluded for security)
   */
  @Override
  public String toString() {
    return "Username: " + getUsername();
  }

}
