package org.endamccarthy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddNewProductMenu {

  private static TextField descriptionInput, priceInput, quantityInput;
  private static Label descriptionErrorLabel, priceErrorLabel, quantityErrorLabel;
  private static String description;
  private static double price;
  private static int quantity;
  private static Product product;

  public static Product displayMenu(String title, String location) {
    product = null;

    // set up window
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(250);

    // set up layout
    VBox loginLayout = new VBox(10);
    loginLayout.setPadding(new Insets(10, 10, 10, 30));
    loginLayout.setAlignment(Pos.CENTER_LEFT);

    // set up elements
    Label descriptionLabel = new Label("Product Description:");
    descriptionErrorLabel = new Label();
    descriptionInput = new TextField();
    descriptionInput.setPromptText("Enter Description");
    descriptionInput.setMaxWidth(150);
    Label priceLabel = new Label("Product Price:");
    priceErrorLabel = new Label();
    priceInput = new TextField();
    priceInput.setPromptText("Enter Price");
    priceInput.setMaxWidth(150);
    Label quantityLabel = new Label("Product Quantity (1-10):");
    quantityErrorLabel = new Label();
    quantityInput = new TextField();
    quantityInput.setPromptText("Enter Quantity");
    quantityInput.setMaxWidth(150);
    Button addProductButton = new Button("Add Product");
    Button cancelButton = new Button("Cancel");

    // actions
    addProductButton.setOnAction(e -> {
      product = validateInput(location);
      if (product != null) {
        window.close();
      }
    });
    cancelButton.setOnAction(e -> window.close());

    // set up scene
    loginLayout.getChildren()
        .addAll(descriptionLabel, descriptionInput, descriptionErrorLabel, priceLabel, priceInput,
            priceErrorLabel, quantityLabel, quantityInput, quantityErrorLabel, addProductButton,
            cancelButton);
    Scene loginScene = new Scene(loginLayout, 300, 300);
    loginScene.getStylesheets().add("style.css");
    window.setScene(loginScene);
    window.showAndWait();

    return product;
  }

  // validate username and password
  private static Product validateInput(String location) {
    descriptionErrorLabel.setText("");
    priceErrorLabel.setText("");
    quantityErrorLabel.setText("");
    if (descriptionInput.getText().equals("")) {
      descriptionErrorLabel.setText("No Description Provided");
    }
    else {
      description = descriptionInput.getText();
    }
    if (!priceInput.getText().matches("\\d{1,2}([\\.]\\d{0,2})?")) {
      priceErrorLabel.setText("Invalid Price Format");
    }
    else {
      price = Double.parseDouble(priceInput.getText());
    }
    if (!quantityInput.getText().matches("\\d{1,2}") ) {
      quantityErrorLabel.setText("Invalid Quantity Format");
    }
    else if (Integer.parseInt(quantityInput.getText()) < 1 || Integer.parseInt(quantityInput.getText()) > 10){
      quantityErrorLabel.setText("Quantity must be between 1-10");
    }
    else {
      quantity = Integer.parseInt(quantityInput.getText());
    }

    if (descriptionErrorLabel.getText().equals("") && priceErrorLabel.getText().equals("") && quantityErrorLabel.getText().equals("")) {
      return new Product(description, location, price, quantity);
    }
    return null;
  }
}
