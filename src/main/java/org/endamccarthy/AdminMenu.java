package org.endamccarthy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AdminMenu extends VendingMachineMenu {

  private Scene sceneOne;
  private Button shutdownMachineButton;

  public AdminMenu() {

  }

  public AdminMenu(Stage window) {
    super(window);
  }

  @Override
  public void run(VendingMachine machine) {
    // set up window
    window.setTitle("Vending Machine | Admin");
    window.setOnCloseRequest(e -> {
      e.consume();
      shutdownMachine();
    });

    // set up top menu
    HBox topMenu = new HBox(20);
    topMenu.setPrefHeight(140);
    topMenu.setPadding(new Insets(35));
    topMenu.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");
    topMenu.setAlignment(Pos.CENTER_RIGHT);

    // set up bottom menu
    HBox bottomMenu = new HBox(20);
    bottomMenu.setPrefHeight(140);
    bottomMenu.setPadding(new Insets(35));
    bottomMenu.setStyle("-fx-background-color: rgba(2, 255, 2, 0.5);");

    // set up elements
    shutdownMachineButton = new Button("Shutdown");

    // actions
    shutdownMachineButton.setOnAction(e -> shutdownMachine());

    // set up scene
    topMenu.getChildren().addAll(shutdownMachineButton);
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(topMenu);
    borderPane.setBottom(bottomMenu);
    sceneOne = new Scene(borderPane, 560, 700);
    sceneOne.getStylesheets().add("style.css");
    window.setScene(sceneOne);
    window.show();
  }

  private void shutdownMachine() {
    if (ConfirmMenu.display("Shutdown Machine", "Shutdown the machine?")) {
      window.close();
    }
  }

}
