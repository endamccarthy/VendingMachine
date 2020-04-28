package org.endamccarthy;

public abstract class User {

  private String username, password;

  public User() {
    this.username = "defaultUsername";
    this.password = "defaultPassword";
  }

  public User(String username, String password) {
    setUsername(username);
    setPassword(password);
  }

  public String getUsername() {
    return username;
  }

  private void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  private void setPassword(String password) {
    this.password = password;
  }
}
