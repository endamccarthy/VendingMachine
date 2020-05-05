package org.endamccarthy;

/**
 * User
 * <p>
 * An abstract class representing users of the vending machine. It is extended to 2 sub-classes:
 * Admin and Customer.
 */
public abstract class User {

  protected String username, password;

  /**
   * Default Constructor
   * <p>
   * Passes default user attribute values to the overloaded constructor.
   */
  public User() {
    this("defaultUsername", "defaultPassword");
  }

  /**
   * Overloaded Constructor
   * <p>
   * Saves the values passed in as the user attributes.
   *
   * @param username This is a String representing the username of the user.
   * @param password This is a String representing the password of the user.
   */
  public User(String username, String password) {
    setUsername(username);
    setPassword(password);
  }

  /**
   * getUsername
   *
   * @return A String representing the username of the user.
   */
  public String getUsername() {
    return username;
  }

  /**
   * setUsername
   *
   * @param username This is a String representing the username of the user.
   */
  private void setUsername(String username) {
    this.username = username;
  }

  /**
   * getPassword
   *
   * @return A String representing the password of the user.
   */
  public String getPassword() {
    return password;
  }

  /**
   * setPassword
   *
   * @param password This is a String representing the password of the user.
   */
  private void setPassword(String password) {
    this.password = password;
  }
}
