package org.endamccarthy;

import java.util.ArrayList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * CustomerMenu
 * <p>
 * A sub-class of VendingMachineMenu used to display a customer menu. An object of this class is
 * created by default in VendingMachineSimulation.
 */
public class CustomerMenu extends VendingMachineMenu {

  private HBox topMenu;
  private GridPane centreMenu;
  private Button loginButton;

  /**
   * Default Constructor.
   * <p>
   * Passes a new Stage object to the overloaded constructor.
   */
  public CustomerMenu() {
    this(new Stage());
  }

  /**
   * Overloaded Constructor
   * <p>
   * Passes a Stage object to the overloaded constructor in the super-class.
   *
   * @param window This is a Stage object.
   */
  public CustomerMenu(Stage window) {
    super(window);
  }


  /**
   * run
   * <p>
   * Displays the customer menu which includes all products contained in the machine.
   *
   * @param machine This is a VendingMachine object.
   */
  @Override
  public void run(VendingMachine machine) {
    this.machine = machine;
    // set window title
    WINDOW.setTitle("Vending Machine");
    // set up overall layout
    BorderPane borderPane = new BorderPane();
    // set up top section
    topMenu = new HBox(40);
    topMenu.setPrefHeight(70);
    topMenu.setPadding(new Insets(20, 20, 0, 20));
    topMenu.getStyleClass().add("top-menu");
    topMenu.setAlignment(Pos.TOP_RIGHT);
    // set up centre section (grid)
    centreMenu = new GridPane();
    centreMenu.setMinHeight(420);
    centreMenu.setMinWidth(420);
    centreMenu.setMaxHeight(420);
    centreMenu.setMaxWidth(420);
    centreMenu.getStyleClass().add("grid");
    for (int i = 0; i < 3; i++) {
      ColumnConstraints column = new ColumnConstraints(140);
      column.setHalignment(HPos.CENTER);
      centreMenu.getColumnConstraints().add(column);
      RowConstraints row = new RowConstraints(140);
      row.setValignment(VPos.CENTER);
      centreMenu.getRowConstraints().add(row);
    }
    // add products to grid
    showProducts();
    // set up top menu buttons
    loginButton = new Button("Login");
    logoutButton = new Button("Logout");
    topMenu.getChildren().addAll(loginButton);
    // if login button is clicked...
    loginButton.setOnAction(e -> {
      // if login was successful, show users details
      if (login()) {
        showUserDetails();
      }
    });
    // if logout button is clicked...
    logoutButton.setOnAction(e -> logout());
    // set up scene
    borderPane.setTop(topMenu);
    borderPane.setCenter(centreMenu);
    Scene sceneOne = new Scene(borderPane, 560, 700);
    sceneOne.getStylesheets().add("style.css");
    WINDOW.setScene(sceneOne);
    WINDOW.show();
  }

  /**
   * logout
   * <p>
   * Logs the user out and saves the user object as null. Resets top menu.
   */
  private void logout() {
    loggedIn = false;
    user = null;
    topMenu.getChildren().clear();
    topMenu.getChildren().addAll(loginButton);
  }

  /**
   * showUserDetails
   * <p>
   * Displays the users details in the top menu.
   */
  private void showUserDetails() {
    Label userDetailsLabel = new Label(String
        .format("Hi %s\nYour balance is €%.2f", user.getUsername(),
            ((Customer) user).getBalance()));
    userDetailsLabel.setTextAlignment(TextAlignment.RIGHT);
    topMenu.getChildren().clear();
    topMenu.getChildren().addAll(userDetailsLabel, logoutButton);
  }

  /**
   * showProducts
   * <p>
   * Populates the grid pane with each product contained in the machine.
   */
  private void showProducts() {
    // clear existing products from menu
    centreMenu.getChildren().clear();
    for (int i = 0; i < machine.getProducts().size(); i++) {
      Label productName = new Label(machine.getProducts().get(i).getDescription());
      productName.getStyleClass().add("label-product-description");
      Label productPrice = new Label(
          String.format("€%.2f", machine.getProducts().get(i).getPrice()));
      Label productQuantity = new Label(
          "" + machine.getProducts().get(i).getQuantity() + " left");
      Button buyProductButton = new Button("Buy");
      // if the product is sold out, disable the button and change it's text
      if (machine.getProducts().get(i).getQuantity() < 1) {
        buyProductButton.setDisable(true);
        buyProductButton.setText("Sold Out");
      }
      VBox productLayout = new VBox(10);
      productLayout.getStyleClass().add("grid-item");
      productLayout.setAlignment(Pos.CENTER);
      // if there is no product at this location, don't show any product info
      if (machine.getProducts().get(i).getDescription().equals("")) {
        buyProductButton.setText("No Products");
        productLayout.getChildren().addAll(buyProductButton);
      }
      // else show product info and buy button
      else {
        productLayout.getChildren()
            .addAll(productName, productPrice, productQuantity, buyProductButton);
      }
      Product productTemp = machine.getProducts().get(i);
      // if the buy button is clicked
      buyProductButton.setOnAction(e -> buyProduct(productTemp));
      // show the product in the assigned grid location
      if (productTemp.getLocation().matches("[a-cA-C][1-3]")) {
        int row = Character.toUpperCase(productTemp.getLocation().charAt(0)) - 65;
        int column = Character.getNumericValue(productTemp.getLocation().charAt(1)) - 1;
        centreMenu.add(productLayout, column, row);
      }
    }
  }

  /**
   * buyProduct
   * <p>
   * Checks if the user is logged in first. Allows the purchase of the product if the user has
   * enough balance and the product is available. Writes customers updated balance out to customer
   * file.
   *
   * @param productTemp This is a temporary Product object representing the selected product.
   */
  private void buyProduct(Product productTemp) {
    // show the login menu if user is not logged in
    if (!loggedIn) {
      login();
    }
    // if the user is logged in and a customer and there is at least 1 product left...
    if (loggedIn && user instanceof Customer && productTemp.getQuantity() > 0) {
      // if the user does NOT have enough balance...
      if (((Customer) user).getBalance() < productTemp.getPrice()) {
        if (ConfirmMenu
            .displayMenu("Purchase Product", "Sorry, You do not have the required balance",
                "Logout",
                "Try Another Product")) {
          logout();
        }
      }
      // else if the customer DOES have enough balance...
      else {
        String message = String
            .format("Product: %s\nPrice: €%.2f\n\nAre you sure you want to purchase this?",
                productTemp.getDescription(), productTemp.getPrice());
        // if they confirm the purchase...
        if (ConfirmMenu.displayMenu("Purchase Product", message, "Yes", "No")) {
          // update their balance saved locally (in the vending machine machine memory)
          ((Customer) user).setBalance(-productTemp.getPrice());
          // update the product quantity saved locally
          machine.getProducts().get(machine.getProducts().indexOf(productTemp)).setQuantity(-1);
          // refresh menu with updated values
          showUserDetails();
          showProducts();
          // update client.dat with new balance
          ArrayList<String> newLine = new ArrayList<>();
          newLine.add(user.getUsername());
          newLine.add("" + ((Customer) user).getBalance());
          newLine.add(user.getPassword());
          if (!FileOutputService.editSingleLine(FILENAME_CLIENTS, newLine)) {
            AlertBox.displayBox("Warning",
                "There was a problem writing to client file.\nPlease ensure the correct filename is specified.",
                "Close");
          }
          // check if they want to logout or buy another item...
          if (ConfirmMenu.displayMenu("Purchase Product", "Thank you for your purchase", "Logout",
              "Buy another item")) {
            logout();
            return;
          }
        }
        showUserDetails();
      }
    }
  }

}
