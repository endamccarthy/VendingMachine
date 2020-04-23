package org.endamccarthy;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class VendingMachineSimulation extends Application {

  private static Stage window;
  private static VBox layoutOne;
  private static Scene sceneOne;
  private static Button loginButton, shutdownMachine;

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    window = primaryStage;
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

  private void shutdownMachine() {
    Boolean answer = ConfirmBox
        .display("Shutdown Machine", "Are you sure you want to shutdown the machine?");
    if (answer) {
      window.close();
    }
  }

}