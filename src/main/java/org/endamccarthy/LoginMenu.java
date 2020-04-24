package org.endamccarthy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginMenu {

  private static TextField usernameInput, passwordInput;
  private static Label usernameErrorLabel, passwordErrorLabel;
  private static boolean result;

  public static boolean displayMenu(String title) {
    result = false;

    // set up window
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(250);

    // set up layout
    VBox loginLayout = new VBox(10);
    loginLayout.setPadding(new Insets(10, 10, 10, 30));
    loginLayout.setAlignment(Pos.CENTER_LEFT);

    // set up elements
    Label usernameLabel = new Label("Username: ");
    usernameErrorLabel = new Label();
    usernameInput = new TextField();
    usernameInput.setPromptText("Enter Username");
    usernameInput.setMaxWidth(150);
    Label passwordLabel = new Label("Password: ");
    passwordErrorLabel = new Label();
    passwordInput = new TextField();
    passwordInput.setPromptText("Enter Password");
    passwordInput.setMaxWidth(150);
    Button loginButton = new Button("Login");
    Button cancelButton = new Button("Cancel");

    // actions
    loginButton.setOnAction(e -> {
      if (validateUser()) {
        result = true;
        window.close();
      }
    });
    cancelButton.setOnAction(e -> window.close());

    // set up scene
    loginLayout.getChildren()
        .addAll(usernameLabel, usernameInput, usernameErrorLabel, passwordLabel, passwordInput,
            passwordErrorLabel, loginButton,
            cancelButton);
    Scene loginScene = new Scene(loginLayout, 300, 300);
    loginScene.getStylesheets().add("style.css");
    window.setScene(loginScene);
    window.showAndWait();

    return result;
  }

  // validate username and password
  private static boolean validateUser() {
    usernameErrorLabel.setText("");
    passwordErrorLabel.setText("");
    User tempUser = VendingMachine.getUserInfo(usernameInput.getText());
    if (tempUser != null) {
      if (tempUser.getPassword().equals(passwordInput.getText())) {
        VendingMachineMenu.user = tempUser;
        return true;
      } else {
        passwordErrorLabel.setText("Incorrect password, try again");
      }
    } else {
      usernameErrorLabel.setText("Sorry username does not exist, try again");
    }
    return false;
  }

}

