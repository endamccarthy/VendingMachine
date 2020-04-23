package org.endamccarthy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginMenu extends VendingMachineMenu {

  private static boolean answer;

  public static boolean display(String title) {
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(250);

    VBox loginLayout = new VBox(20);
    loginLayout.setPadding(new Insets(10, 10, 10, 30));
    loginLayout.setAlignment(Pos.CENTER_LEFT);

    TextField usernameInput = new TextField();
    usernameInput.setPromptText("Enter Username");
    usernameInput.setMaxWidth(150);

    TextField passwordInput = new TextField();
    passwordInput.setPromptText("Enter Password");
    passwordInput.setMaxWidth(150);

    Button loginButton = new Button("Login");
    Button cancelButton = new Button("Cancel");

    loginButton.setOnAction(e -> {
      answer = true;
      window.close();
    });
    cancelButton.setOnAction(e -> {
      window.close();
    });

    loginLayout.getChildren().addAll(usernameInput, passwordInput, loginButton, cancelButton);

    Scene loginScene = new Scene(loginLayout, 300, 300);
    loginScene.getStylesheets().add("style.css");
    window.setScene(loginScene);
    window.showAndWait();

    return answer;
  }

}

