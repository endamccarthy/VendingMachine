package org.endamccarthy;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class VendingMachineMenu {

  final public String FILENAME_CLIENTS = "Clients.dat";
  final public String FILENAME_ADMIN = "Admin.dat";
  protected final Stage WINDOW;
  protected User user;
  private User tempUser;
  protected boolean loggedIn;
  protected VendingMachine machine;
  protected Button logoutButton;
  private HashMap<String, User> userInfo;

  public VendingMachineMenu() {
    this(new Stage());
  }

  public VendingMachineMenu(Stage window) {
    this.WINDOW = window;
    loggedIn = false;
    setUserInfo();
  }

  public abstract void run(VendingMachine machine);

  public User getUserInfo(String username) {
    return userInfo.get(username);
  }

  private void setUserInfo() {
    String username, password;
    double balance;
    userInfo = new HashMap<>();
    // read customers first
    ArrayList<String[]> temp = FileInputService.readFromFile(FILENAME_CLIENTS);
    if (temp != null) {
      for (String[] line : temp) {
        if (line.length == 3) {
          username = line[0].trim();
          try {
            balance = Double.parseDouble(line[1].trim());
          } catch (NumberFormatException e) {
            continue;
          }
          password = line[2].trim();
          userInfo.put(username, new Customer(username, password, balance));
        }
      }
    } else {
      AlertBox.displayBox("Warning",
          "There was a problem loading the client user data.\nPlease ensure the correct filename is specified.",
          "Close");
    }
    // read admin second
    temp = FileInputService.readFromFile(FILENAME_ADMIN);
    if (temp != null) {
      for (String[] line : temp) {
        if (line.length == 2) {
          username = line[0].trim();
          password = line[1].trim();
          userInfo.put(username, new Admin(username, password));
        }
      }
    } else {
      AlertBox.displayBox("Warning",
          "There was a problem loading the admin user data.\nPlease ensure the correct filename is specified.",
          "Close");
    }
  }

  protected boolean login() {
    // if user logs in...
    showLoginMenu();
    if (tempUser != null) {
      user = tempUser;
      // if user is an admin, close customer menu and open admin menu
      if (user instanceof Admin) {
        VendingMachineSimulation.adminMenu = new AdminMenu(WINDOW);
        VendingMachineSimulation.adminMenu.loggedIn = true;
        VendingMachineSimulation.adminMenu.run(machine);
      } else if (user instanceof Customer) {
        loggedIn = true;
        return true;
      }
    }
    return false;
  }

  private void showLoginMenu() {
    // set up window
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle("Login");
    window.setMinWidth(250);
    // set up layout
    VBox loginLayout = new VBox(10);
    loginLayout.setPadding(new Insets(10, 10, 10, 30));
    loginLayout.setAlignment(Pos.CENTER_LEFT);
    // set up elements
    Label usernameLabel = new Label("Username: ");
    usernameLabel.getStyleClass().add("label-form");
    Label usernameErrorLabel = new Label();
    usernameErrorLabel.getStyleClass().add("invalid-input");
    TextField usernameInput = new TextField();
    usernameInput.setPromptText("Enter Username");
    usernameInput.setMaxWidth(150);
    Label passwordLabel = new Label("Password: ");
    passwordLabel.getStyleClass().add("label-form");
    Label passwordErrorLabel = new Label();
    passwordErrorLabel.getStyleClass().add("invalid-input");
    TextField passwordInput = new TextField();
    passwordInput.setPromptText("Enter Password");
    passwordInput.setMaxWidth(150);
    HBox buttonLayout = new HBox(10);
    Button loginButton = new Button("Login");
    Button cancelButton = new Button("Cancel");
    // actions
    loginButton.setOnAction(e -> {
      usernameErrorLabel.setText("");
      passwordErrorLabel.setText("");
      tempUser = getUserInfo(usernameInput.getText());
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
    cancelButton.setOnAction(e ->  {
      tempUser = null;
      window.close();
    });
    // set up scene
    buttonLayout.getChildren().addAll(loginButton, cancelButton);
    loginLayout.getChildren()
        .addAll(usernameLabel, usernameInput, usernameErrorLabel, passwordLabel, passwordInput,
            passwordErrorLabel, buttonLayout);
    loginLayout.getStyleClass().add("popup-background");
    Scene loginScene = new Scene(loginLayout, 300, 300);
    loginScene.getStylesheets().add("style.css");
    window.setScene(loginScene);
    window.showAndWait();
  }

  protected abstract void logout();

}
