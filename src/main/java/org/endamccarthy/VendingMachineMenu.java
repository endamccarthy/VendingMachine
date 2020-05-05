package org.endamccarthy;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * VendingMachineMenu
 * <p>
 * An abstract class used to represent a vending machine menu. It is extended to 2 sub-classes:
 * CustomerMenu and AdminMenu. It requires a VendingMachine object to run.
 */
public abstract class VendingMachineMenu {

  final public String FILENAME_CLIENTS = "Clients.dat";
  final public String FILENAME_ADMIN = "Admin.dat";
  final protected Stage WINDOW;
  protected User user;
  protected boolean loggedIn;
  protected VendingMachine machine;
  protected Button logoutButton;
  private User tempUser;
  private HashMap<String, User> userInfo;

  /**
   * Default Constructor
   * <p>
   * Passes a new Stage object to the overloaded constructor.
   */
  public VendingMachineMenu() {
    this(new Stage());
  }

  /**
   * Overloaded Constructor
   * <p>
   * Saves a Stage object to WINDOW. Sets the default state of loggedIn as false. Calls setUserInfo
   * to load in customer accounts from file.
   *
   * @param window This is a Stage object.
   */
  public VendingMachineMenu(Stage window) {
    this.WINDOW = window;
    loggedIn = false;
    setUserInfo();
  }

  /**
   * run
   * <p>
   * An abstract method which is implemented individually in CustomerMenu and AdminMenu.
   *
   * @param machine This is a VendingMachine object.
   */
  public abstract void run(VendingMachine machine);

  /**
   * getUserInfo
   *
   * @param username This is String representing a username.
   * @return A User object.
   */
  public User getUserInfo(String username) {
    return userInfo.get(username);
  }

  /**
   * setUserInfo
   * <p>
   * Uses the FileInputService class to read in users from both a customer and an admin file. Stores
   * the users in a HashMap called userInfo.
   */
  private void setUserInfo() {
    String username, password;
    double balance;
    userInfo = new HashMap<>();
    // read in from customers file first
    ArrayList<String[]> temp = FileInputService.readFromFile(FILENAME_CLIENTS);
    // if the file was read correctly...
    if (temp != null) {
      for (String[] line : temp) {
        if (line.length == 3) {
          // prepare and validate each item in the line
          username = line[0].trim();
          try {
            balance = Double.parseDouble(line[1].trim());
          } catch (NumberFormatException e) {
            continue;
          }
          password = line[2].trim();
          // create a new customer based on the line that was read in
          userInfo.put(username, new Customer(username, password, balance));
        }
      }
    }
    // else show warning message
    else {
      AlertBox.displayBox("Warning",
          "There was a problem loading the client user data.\nPlease ensure the correct filename is specified.",
          "Close");
    }
    // read in from admin file next
    temp = FileInputService.readFromFile(FILENAME_ADMIN);
    // if the file was read correctly...
    if (temp != null) {
      for (String[] line : temp) {
        if (line.length == 2) {
          // prepare and validate each item in the line
          username = line[0].trim();
          password = line[1].trim();
          // create a new admin based on the line that was read in
          userInfo.put(username, new Admin(username, password));
        }
      }
    }
    // else show warning message
    else {
      AlertBox.displayBox("Warning",
          "There was a problem loading the admin user data.\nPlease ensure the correct filename is specified.",
          "Close");
    }
  }

  /**
   * login
   * <p>
   * Called when the login button in CustomerMenu is clicked. Calls a method to display a login
   * window (which also validates credentials). Checks if the user is an admin or a customer.
   *
   * @return A boolean (true if the login was successful and user is a customer, otherwise false).
   */
  protected boolean login() {
    showLoginMenu();
    // if user logs in successfully...
    if (tempUser != null) {
      user = tempUser;
      // if user is an admin, open the admin menu
      if (user instanceof Admin) {
        VendingMachineSimulation.adminMenu = new AdminMenu(WINDOW);
        VendingMachineSimulation.adminMenu.loggedIn = true;
        VendingMachineSimulation.adminMenu.run(machine);
      }
      // else keep the customer menu opened
      else if (user instanceof Customer) {
        loggedIn = true;
        return true;
      }
    }
    return false;
  }

  /**
   * showLoginMenu
   * <p>
   * Called from the login method. If credentials are valid, then user is saved to the tempUser
   * object (otherwise tempUser is null).
   */
  private void showLoginMenu() {
    // set up new window
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle("Login");
    window.setMinWidth(250);
    // set up overall layout
    VBox loginLayout = new VBox(10);
    loginLayout.setPadding(new Insets(10, 10, 10, 30));
    loginLayout.setAlignment(Pos.CENTER_LEFT);
    loginLayout.getStyleClass().add("popup-background");
    // set up username section
    Label usernameLabel = new Label("Username: ");
    usernameLabel.getStyleClass().add("label-form");
    Label usernameErrorLabel = new Label();
    usernameErrorLabel.getStyleClass().add("invalid-input");
    TextField usernameInput = new TextField();
    usernameInput.setPromptText("Enter Username");
    usernameInput.setMaxWidth(150);
    // set up password section
    Label passwordLabel = new Label("Password: ");
    passwordLabel.getStyleClass().add("label-form");
    Label passwordErrorLabel = new Label();
    passwordErrorLabel.getStyleClass().add("invalid-input");
    PasswordField passwordInput = new PasswordField();
    passwordInput.setPromptText("Enter Password");
    passwordInput.setMaxWidth(150);
    // set up button section
    Button loginButton = new Button("Login");
    Button cancelButton = new Button("Cancel");
    HBox buttonLayout = new HBox(10);
    buttonLayout.getChildren().addAll(loginButton, cancelButton);
    // if login button is clicked
    loginButton.setOnAction(e -> {
      usernameErrorLabel.setText("");
      passwordErrorLabel.setText("");
      tempUser = getUserInfo(usernameInput.getText());
      // validate username and password
      if (tempUser != null) {
        if (tempUser.getPassword().equals(passwordInput.getText())) {
          window.close();
        } else {
          passwordErrorLabel.setText("Incorrect password, try again");
        }
      } else {
        usernameErrorLabel.setText("Sorry username does not exist, try again");
      }
    });
    // if cancel button is clicked
    cancelButton.setOnAction(e -> {
      tempUser = null;
      window.close();
    });
    // set up scene
    loginLayout.getChildren()
        .addAll(usernameLabel, usernameInput, usernameErrorLabel, passwordLabel, passwordInput,
            passwordErrorLabel, buttonLayout);
    Scene loginScene = new Scene(loginLayout, 300, 300);
    loginScene.getStylesheets().add("style.css");
    window.setScene(loginScene);
    window.showAndWait();
  }

}
