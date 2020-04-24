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
  private Button loginButton, logoutButton;

  public CustomerMenu() {

  }

  public CustomerMenu(Stage window) {
    super(window);
  }

  @Override
  public void run(VendingMachine machine) {
    // set up window
    window.setTitle("Vending Machine");

    // set up top menu
    HBox topMenu = new HBox(20);
    topMenu.setPrefHeight(140);
    topMenu.setPadding(new Insets(35));
    topMenu.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");
    topMenu.setAlignment(Pos.TOP_RIGHT);

    // set up bottom menu
    HBox bottomMenu = new HBox(20);
    bottomMenu.setPrefHeight(140);
    bottomMenu.setPadding(new Insets(35));
    bottomMenu.setStyle("-fx-background-color: rgba(2, 255, 2, 0.5);");

    // set up centre menu (grid)
    GridPane centreMenu = new GridPane();
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
    Label productName = new Label("Twix");
    Label productPrice = new Label("€0.85");
    Button buyProductButton = new Button("Buy");
    VBox product = new VBox(10);
    product.setAlignment(Pos.CENTER);
    product.getChildren().addAll(productName, productPrice, buyProductButton);
    centreMenu.add(product, 0, 0);

    // set up elements
    loginButton = new Button("Login");

    // actions
    loginButton.setOnAction(e -> {
      // if user logs in...
      if (LoginMenu.displayMenu("Login")) {
        // if user is an admin, close customer menu and open admin menu
        if (user instanceof Admin) {
          VendingMachineMenu menu = new AdminMenu(window);
          menu.run(machine);
        } else if (user instanceof Customer) {
          // update customer menu for logged in user
          Label userDetailsLabel = new Label(String
              .format("Hi %s\nYour balance is €%.2f", user.getUsername(),
                  ((Customer) user).getBalance()));
          userDetailsLabel.setTextAlignment(TextAlignment.RIGHT);
          logoutButton = new Button("Logout");
          topMenu.getChildren().clear();
          topMenu.getChildren().addAll(userDetailsLabel, logoutButton);
        }
      }
    });

    // set up scene
    topMenu.getChildren().addAll(loginButton);
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(topMenu);
    borderPane.setBottom(bottomMenu);
    borderPane.setCenter(centreMenu);
    sceneOne = new Scene(borderPane, 560, 700);
    sceneOne.getStylesheets().add("style.css");
    window.setScene(sceneOne);
    window.show();
  }

}
