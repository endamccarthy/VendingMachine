package org.endamccarthy;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AdminMenu extends VendingMachineMenu {

  private TableView<Product> table;
  private BorderPane borderPane;
  private HBox topMenu;
  private Button addProductButton;
  private Product product;
  private TextField descriptionInput, priceInput, quantityInput;
  private Label descriptionErrorLabel, priceErrorLabel, quantityErrorLabel;
  private String description, location;
  private double price;
  private int quantity;

  public AdminMenu() {
    this(new Stage());
  }

  public AdminMenu(Stage window) {
    super(window);
  }

  @Override
  public void run(VendingMachine machine) {
    this.machine = machine;
    // set up window
    WINDOW.setTitle("Vending Machine | Admin");

    TableColumn<Product, String> locationColumn = new TableColumn<>("Location");
    locationColumn.setMinWidth(50);
    locationColumn.getStyleClass().add("-fx-text-fill: blue;");
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

    TableColumn<Product, String> descriptionColumn = new TableColumn<>("Description");
    descriptionColumn.setMinWidth(100);
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
    priceColumn.setMinWidth(50);
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
    quantityColumn.setMinWidth(50);
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    TableColumn<Product, Void> restockProductColumn = new TableColumn<>("Restock");
    Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<>() {
      @Override
      public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
        return new TableCell<>() {
          private final Button btn = new Button("Restock");

          {
            btn.setOnAction(e -> {
              product = getTableView().getItems().get(getIndex());
              restockProduct(product);
            });
          }

          @Override
          public void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
              setGraphic(null);
            } else {
              setGraphic(btn);
            }
          }
        };
      }
    };
    restockProductColumn.setCellFactory(cellFactory);

    TableColumn<Product, Void> removeProductColumn = new TableColumn<>("Remove Product");
    cellFactory = new Callback<>() {
      @Override
      public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
        return new TableCell<>() {
          private final Button btn = new Button("Remove Product");

          {
            btn.setOnAction(e -> {
              product = getTableView().getItems().get(getIndex());
              removeProduct(product);
            });
          }

          @Override
          public void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
              setGraphic(null);
            } else {
              setGraphic(btn);
            }
          }
        };
      }
    };
    removeProductColumn.setCellFactory(cellFactory);

    table = new TableView<>();
    ObservableList<Product> products = FXCollections.observableArrayList(machine.getProducts());
    table.setItems(products);
    //noinspection unchecked
    table.getColumns()
        .addAll(locationColumn, descriptionColumn, priceColumn, quantityColumn,
            restockProductColumn, removeProductColumn);

    addProductButton = new Button("Add Product");
    addProductButton.setOnAction(e -> addProduct());
    addProductButton.setDisable(true);
    for (Product p : machine.getProducts()) {
      if (p.getDescription().equals("")) {
        addProductButton.setDisable(false);
      }
    }

    Button shutdownMachineButton = new Button("Shutdown");
    shutdownMachineButton.setOnAction(e -> shutdownMachine());

    logoutButton = new Button("Logout");
    logoutButton.setOnAction(e -> logout());


    // set up top menu
    topMenu = new HBox(40);
    topMenu.setPrefHeight(70);
    topMenu.setPadding(new Insets(20, 20, 0, 20));
    topMenu.getStyleClass().add("top-menu");
    topMenu.setAlignment(Pos.TOP_RIGHT);
    topMenu.getChildren().addAll(addProductButton, shutdownMachineButton, logoutButton);
    table.setMaxSize(505, 360);
    borderPane = new BorderPane();
    borderPane.setTop(topMenu);
    borderPane.setCenter(table);
    Scene sceneOne = new Scene(borderPane, 560, 700);
    sceneOne.getStylesheets().add("style.css");
    WINDOW.setScene(sceneOne);
    WINDOW.show();
  }

  @Override
  protected void logout() {
    loggedIn = false;
    user = null;
    VendingMachineSimulation.customerMenu.run(machine);
  }

  private void shutdownMachine() {
    if (ConfirmMenu.displayMenu("Shutdown Machine", "Shutdown the machine?", "Yes", "No")) {
      ArrayList<String> temp;
      // update products file
      temp = new ArrayList<>();
      for (Product p : machine.getProducts()) {
        temp.add(p.getDescription() + "," + p.getLocation() + "," + p.getPrice() + "," + p
            .getQuantity());
      }
      if (!FileOutputService.writeToFile(VendingMachine.FILENAME_PRODUCTS, temp)) {
        AlertBox.displayBox("Warning",
            "There was a problem writing to the products file.\nPlease ensure the correct filename is specified.",
            "Close");
      }
      WINDOW.close();
    }
  }

  private void restockProduct(Product product) {
    if (product.getQuantity() >= 10) {
      AlertBox.displayBox("Restock Item", "This product is already fully stocked", "Close");
    } else if (product.getDescription().equals("")) {
      AlertBox.displayBox("Restock Item", "There is no product in this location", "Close");
    } else {
      machine.getProducts().get(machine.getProducts().indexOf(product))
          .setQuantity(10 - product.getQuantity());
      AlertBox.displayBox("Restock Item", "Product is now fully stocked", "Close");
      refreshTable();
    }
  }

  private void removeProduct(Product product) {
    if (product.getDescription().equals("")) {
      AlertBox.displayBox("Remove Product", "There is no product in this location", "Close");
    } else if (ConfirmMenu
        .displayMenu("Remove Product", "Are you sure you want to remove this product?", "Yes",
            "No")) {
      machine.getProducts().get(machine.getProducts().indexOf(product)).setDescription("");
      machine.getProducts().get(machine.getProducts().indexOf(product)).setPrice(0.00);
      machine.getProducts().get(machine.getProducts().indexOf(product))
          .setQuantity(-product.getQuantity());
      refreshTable();
    }
  }

  private void addProduct() {
    for (Product p : machine.getProducts()) {
      // if there is room for a new product...
      if (p.getDescription().equals("")) {
        location = p.getLocation();
        Product temp = showAddProductMenu();
        if (temp != null) {
          p.setDescription(temp.getDescription());
          p.setPrice(temp.getPrice());
          p.setQuantity(temp.getQuantity());
          refreshTable();
        }
        break;
      }
    }
  }

  private Product showAddProductMenu() {
    product = null;
    // set up window
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle("Add New Product");
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
      product = validateInput();
      if (product != null) {
        window.close();
      }
    });
    cancelButton.setOnAction(e -> window.close());
    HBox buttonLayout = new HBox(10);
    buttonLayout.getChildren().addAll(addProductButton, cancelButton);
    // set up scene
    loginLayout.getChildren()
        .addAll(descriptionLabel, descriptionInput, descriptionErrorLabel, priceLabel, priceInput,
            priceErrorLabel, quantityLabel, quantityInput, quantityErrorLabel, buttonLayout);
    loginLayout.getStyleClass().add("popup-background");
    Scene loginScene = new Scene(loginLayout, 300, 400);
    loginScene.getStylesheets().add("style.css");
    window.setScene(loginScene);
    window.showAndWait();
    return product;
  }

  private Product validateInput() {
    descriptionErrorLabel.setText("");
    priceErrorLabel.setText("");
    quantityErrorLabel.setText("");
    if (descriptionInput.getText().equals("")) {
      descriptionErrorLabel.setText("No Description Provided");
    } else {
      description = descriptionInput.getText();
    }
    if (!priceInput.getText().matches("\\d{1,2}([.]\\d{0,2})?")) {
      priceErrorLabel.setText("Invalid Price Format");
    } else {
      price = Double.parseDouble(priceInput.getText());
    }
    if (!quantityInput.getText().matches("\\d{1,2}")) {
      quantityErrorLabel.setText("Invalid Quantity Format");
    } else if (Integer.parseInt(quantityInput.getText()) < 1
        || Integer.parseInt(quantityInput.getText()) > 10) {
      quantityErrorLabel.setText("Quantity must be between 1-10");
    } else {
      quantity = Integer.parseInt(quantityInput.getText());
    }
    if (descriptionErrorLabel.getText().equals("") && priceErrorLabel.getText().equals("")
        && quantityErrorLabel.getText().equals("")) {
      return new Product(description, location, price, quantity);
    }
    return null;
  }

  private void refreshTable() {
    addProductButton.setDisable(true);
    for (Product p : machine.getProducts()) {
      if (p.getDescription().equals("")) {
        addProductButton.setDisable(false);
      }
    }
    ObservableList<Product> products = FXCollections.observableArrayList(machine.getProducts());
    table.getItems().clear();
    table.setItems(products);
    borderPane.setTop(topMenu);
    borderPane.setCenter(table);
  }

}
