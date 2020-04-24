package org.endamccarthy;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class VendingMachineSimulation extends Application {

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    VendingMachine machine = new VendingMachine();
    VendingMachineMenu menu = new CustomerMenu(primaryStage);
    menu.run(machine);
  }

}