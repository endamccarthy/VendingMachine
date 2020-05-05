package org.endamccarthy;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Vending Machine
 * <p>
 * This program simulates a vending machine. It displays a GUI created using JavaFX. It has 2 types
 * of users, customers and admins. Users can login using their username and password. Customers can
 * purchase products if they have the required balance. Admins can restock the products, remove
 * products, add new products and shutdown the machine.
 *
 * @author Enda McCarthy
 * @version 1.0
 * @since 2020-05-05
 */
public class VendingMachineSimulation extends Application {

  public static VendingMachineMenu customerMenu, adminMenu;

  /**
   * main
   * <p>
   * This is the main method which launches the JavaFX application.
   *
   * @param args Unused.
   */
  public static void main(String[] args) {
    launch();
  }

  /**
   * start
   * <p>
   * The start method from the JavaFX Application class is overridden. This is called from the
   * launch method called in main.
   *
   * @param primaryStage This is the application window.
   */
  @Override
  public void start(Stage primaryStage) {

    // confirm that the user wants to close the application
    primaryStage.setOnCloseRequest(e -> {
      e.consume();
      if (ConfirmMenu.displayMenu("Close Application", "Are you sure?", "Yes", "No")) {
        primaryStage.close();
      }
    });

    // create the vending machine and show the customer menu by default
    VendingMachine machine = new VendingMachine();
    customerMenu = new CustomerMenu(primaryStage);
    customerMenu.run(machine);

  }

}