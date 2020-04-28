package org.endamccarthy;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class VendingMachineSimulation extends Application {

  public static VendingMachineMenu customerMenu, adminMenu;

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    primaryStage.setOnCloseRequest(e -> {
      e.consume();
      if (ConfirmMenu.displayMenu("Close Application", "Are you sure?", "Yes", "No")) {
        primaryStage.close();
      }
    });
    VendingMachine machine = new VendingMachine();
    customerMenu = new CustomerMenu(primaryStage);
    customerMenu.run(machine);
  }

}