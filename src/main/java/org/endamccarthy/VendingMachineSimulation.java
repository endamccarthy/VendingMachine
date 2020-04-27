package org.endamccarthy;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class VendingMachineSimulation extends Application {

  public static VendingMachine machine;

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage primaryStage) throws IOException {

    primaryStage.setOnCloseRequest(e -> {
      e.consume();
      if (ConfirmMenu.display("Close Application", "Are you sure?", "Yes", "No")) {
        primaryStage.close();
      }
    });

    machine = new VendingMachine();
    VendingMachineMenu menu = new CustomerMenu(primaryStage);
    menu.run(machine);

  }

}