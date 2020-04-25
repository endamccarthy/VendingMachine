package org.endamccarthy;

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

  private Scene sceneOne;
  private BorderPane borderPane;
  private HBox topMenu, bottomMenu;
  private GridPane centreMenu;
  private Button loginButton, logoutButton, buyProductButton;
  private VendingMachine machine;

  public CustomerMenu() {

  }

  public CustomerMenu(Stage window) {
    super(window);
  }

  @Override
  public void run(VendingMachine machine) {
    this.machine = machine;
    // set up window
    window.setTitle("Vending Machine");

    // set up top menu
    topMenu = new HBox(20);
    topMenu.setPrefHeight(140);
    topMenu.setPadding(new Insets(35));
    topMenu.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");
    topMenu.setAlignment(Pos.TOP_RIGHT);

    // set up bottom menu
    bottomMenu = new HBox(20);
    bottomMenu.setPrefHeight(140);
    bottomMenu.setPadding(new Insets(35));
    bottomMenu.setStyle("-fx-background-color: rgba(2, 255, 2, 0.5);");

    // set up centre menu (grid)
    centreMenu = new GridPane();
    centreMenu.setMinHeight(420);
    centreMenu.setMinWidth(420);
    centreMenu.setMaxHeight(420);
    centreMenu.setMaxWidth(420);
    centreMenu.setGridLinesVisible(true);
    centreMenu.setStyle("-fx-background-color: rgba(90, 90, 200, 0.5);");
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
    loginButton.setOnAction(e -> login());
    logoutButton.setOnAction(e -> logout());

    // set up scene
    topMenu.getChildren().addAll(loginButton);
    borderPane = new BorderPane();
    borderPane.setTop(topMenu);
    borderPane.setBottom(bottomMenu);
    borderPane.setCenter(centreMenu);
    sceneOne = new Scene(borderPane, 560, 700);
    sceneOne.getStylesheets().add("style.css");
    window.setScene(sceneOne);
    window.show();
  }

  private void login() {
    // if user logs in...
    if (LoginMenu.displayMenu("Login")) {
      // if user is an admin, close customer menu and open admin menu
      if (user instanceof Admin) {
        VendingMachineMenu adminMenu = new AdminMenu(window);
        adminMenu.loggedIn = true;
        adminMenu.run(machine);
      } else if (user instanceof Customer) {
        loggedIn = true;
        // update customer menu for logged in user
        showUserDetails();
      }
    }
  }

  private void logout() {
    loggedIn = false;
    VendingMachineMenu.user = null;
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
      Label productPrice = new Label("€" + machine.getProducts().get(i).getPrice());
      Label productQuantity = new Label("Quantity left: " + machine.getProducts().get(i).getQuantity());
      buyProductButton = new Button("Buy");
      if (machine.getProducts().get(i).getQuantity() < 1) {
        buyProductButton.setDisable(true);
        buyProductButton.setText("Sold Out");
      }
      VBox productLayout = new VBox(10);
      productLayout.setAlignment(Pos.CENTER);
      productLayout.getChildren().addAll(productName, productPrice, productQuantity, buyProductButton);
      String location = machine.getProducts().get(i).getLocation();
      Product productTemp = machine.getProducts().get(i);
      buyProductButton.setOnAction(e -> buyProduct(productTemp));
      if (location.matches("[a-cA-C][1-3]")) {
        int row = Character.toUpperCase(location.charAt(0)) - 65;
        int column = Character.getNumericValue(location.charAt(1)) - 1;
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
      System.out.println(((Customer) user).getBalance());
      System.out.println(productTemp.getPrice());
      if (((Customer) user).getBalance() < productTemp.getPrice()) {
        if (ConfirmMenu
            .display("Purchase Product", "Sorry, You do not have the required balance", "Logout",
                "Try Another Product")) {
          logout();
        }
      } else {
        String temp = String
            .format("Product: %s\nPrice: €%f\nAre you sure you want to purchase this?",
                productTemp.getDescription(), productTemp.getPrice());
        if (ConfirmMenu.display("Purchase Product", temp, "Yes", "No")) {
          ((Customer) user).setBalance(-productTemp.getPrice());
          machine.getProducts().get(machine.getProducts().indexOf(productTemp)).setQuantity(-1);
          showUserDetails();
          showProducts();
        }
      }
    }
  }

}
