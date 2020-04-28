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

public class CustomerMenu extends VendingMachineMenu {

  private HBox topMenu;
  private GridPane centreMenu;
  private Button loginButton;

  public CustomerMenu() {
    this(new Stage());
  }

  public CustomerMenu(Stage window) {
    super(window);
  }

  @Override
  public void run(VendingMachine machine) {
    this.machine = machine;
    // set up window
    WINDOW.setTitle("Vending Machine");

    // set up top menu
    topMenu = new HBox(20);
    topMenu.setPrefHeight(105);
    topMenu.setPadding(new Insets(20));
    topMenu.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");
    topMenu.setAlignment(Pos.TOP_RIGHT);

    // set up centre menu (grid)
    centreMenu = new GridPane();
    centreMenu.setMinHeight(420);
    centreMenu.setMinWidth(420);
    centreMenu.setMaxHeight(420);
    centreMenu.setMaxWidth(420);
    centreMenu.setStyle("-fx-background-color: rgb(200, 170, 200);");
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

    // set up elements
    loginButton = new Button("Login");
    logoutButton = new Button("Logout");

    // actions
    loginButton.setOnAction(e -> {
      if (login()) {
        showUserDetails();
      }
    });
    logoutButton.setOnAction(e -> logout());

    // set up scene
    topMenu.getChildren().addAll(loginButton);
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(topMenu);
    borderPane.setCenter(centreMenu);
    Scene sceneOne = new Scene(borderPane, 560, 700);
    sceneOne.getStylesheets().add("style.css");
    WINDOW.setScene(sceneOne);
    WINDOW.show();
  }

  @Override
  protected void logout() {
    loggedIn = false;
    user = null;
    topMenu.getChildren().clear();
    topMenu.getChildren().addAll(loginButton);
  }

  private void showUserDetails() {
    Label userDetailsLabel = new Label(String
        .format("Hi %s\nYour balance is €%.2f", user.getUsername(),
            ((Customer) user).getBalance()));
    userDetailsLabel.setTextAlignment(TextAlignment.RIGHT);
    topMenu.getChildren().clear();
    topMenu.getChildren().addAll(userDetailsLabel, logoutButton);
  }

  private void showProducts() {
    centreMenu.getChildren().clear();
    for (int i = 0; i < machine.getProducts().size(); i++) {
      Label productName = new Label(machine.getProducts().get(i).getDescription());
      Label productPrice = new Label(
          String.format("€%.2f", machine.getProducts().get(i).getPrice()));
      Label productQuantity = new Label(
          "Quantity left: " + machine.getProducts().get(i).getQuantity());
      Button buyProductButton = new Button("Buy");
      if (machine.getProducts().get(i).getQuantity() < 1) {
        buyProductButton.setDisable(true);
        buyProductButton.setText("Sold Out");
      }
      VBox productLayout = new VBox(10);
      productLayout.setAlignment(Pos.CENTER);
      productLayout.getChildren()
          .addAll(productName, productPrice, productQuantity, buyProductButton);
      Product productTemp = machine.getProducts().get(i);
      buyProductButton.setOnAction(e -> buyProduct(productTemp));
      if (productTemp.getLocation().matches("[a-cA-C][1-3]")) {
        int row = Character.toUpperCase(productTemp.getLocation().charAt(0)) - 65;
        int column = Character.getNumericValue(productTemp.getLocation().charAt(1)) - 1;
        centreMenu.add(productLayout, column, row);
      }
    }
  }

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
            .displayMenu("Purchase Product", "Sorry, You do not have the required balance", "Logout",
                "Try Another Product")) {
          logout();
        }
      }
      // else if the customer DOES have enough balance...
      else {
        String message = String
            .format("Product: %s\nPrice: €%.2f\nAre you sure you want to purchase this?",
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
          }
        }
      }
    }
  }

}
