package org.endamccarthy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class VendingMachineMenu {

  private Stage window;
  private VBox layoutOne;
  private Scene sceneOne;
  private Button loginButton, shutdownMachine;

  public VendingMachineMenu() {
    this.window = new Stage();
    showMenu();
  }

  public VendingMachineMenu(Stage window) {
    this.window = window;
    showMenu();
  }

  private void showMenu() {
    window.setTitle("Vending Machine");
    window.setOnCloseRequest(e -> {
      e.consume();
      shutdownMachine();
    });

    layoutOne = new VBox(20);
    layoutOne.setPadding(new Insets(10));
    layoutOne.setAlignment(Pos.CENTER_LEFT);

    loginButton = new Button("Login");
    shutdownMachine = new Button("Shutdown");

    loginButton.setOnAction(e -> {
      boolean result = LoginMenu.display("Login");
      System.out.println(result);
    });
    shutdownMachine.setOnAction(e -> shutdownMachine());

    layoutOne.getChildren().addAll(loginButton, shutdownMachine);
    sceneOne = new Scene(layoutOne, 500, 600);
    sceneOne.getStylesheets().add("style.css");
    window.setScene(sceneOne);
    window.show();
  }

  protected void shutdownMachine() {
    boolean answer = ConfirmBox
        .display("Shutdown Machine", "Are you sure you want to shutdown the machine?");
    if (answer) {
      window.close();
    }
  }

}
