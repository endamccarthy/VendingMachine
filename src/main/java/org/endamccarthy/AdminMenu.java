package org.endamccarthy;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AdminMenu extends VendingMachineMenu {

  private Scene sceneOne;
  private Button shutdownMachineButton, addProductButton, removeProductButton;
  private VendingMachine machine;
  private TableView<Product> table;
  private VBox vbox;
  private Product product;

  public AdminMenu() {

  }

  public AdminMenu(Stage window) {
    super(window);
  }

  @Override
  public void run(VendingMachine machine) {
    this.machine = machine;
    // set up window
    window.setTitle("Vending Machine | Admin");


    TableColumn<Product, String> locationColumn = new TableColumn<>("Location");
    locationColumn.setMinWidth(50);
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

    TableColumn<Product, Void> restockProductColumn = new TableColumn("Restock");
    Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<>() {
      @Override
      public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
        final TableCell<Product, Void> cell = new TableCell<>() {
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
        return cell;
      }
    };
    restockProductColumn.setCellFactory(cellFactory);

    TableColumn removeProductColumn = new TableColumn("Remove Product");
    cellFactory = new Callback<>() {
      @Override
      public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
        final TableCell<Product, Void> cell = new TableCell<>() {
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
        return cell;
      }
    };
    removeProductColumn.setCellFactory(cellFactory);

    table = new TableView<>();
    ObservableList<Product> products = FXCollections.observableArrayList(machine.getProducts());
    table.setItems(products);
    table.getColumns()
        .addAll(locationColumn, descriptionColumn, priceColumn, quantityColumn, restockProductColumn, removeProductColumn);

    addProductButton = new Button("Add Product");
    addProductButton.setOnAction(e -> addProduct());

    shutdownMachineButton = new Button("Shutdown");
    shutdownMachineButton.setOnAction(e -> shutdownMachine());

    vbox = new VBox(20);
    vbox.getChildren().addAll(table, addProductButton, shutdownMachineButton);
    vbox.setPadding(new Insets(10));
    vbox.setAlignment(Pos.TOP_CENTER);
    sceneOne = new Scene(vbox, 560, 700);

    sceneOne.getStylesheets().add("style.css");
    window.setScene(sceneOne);
    window.show();
  }

  private void shutdownMachine() {
    if (ConfirmMenu.display("Shutdown Machine", "Shutdown the machine?", "Yes", "No")) {
      ArrayList<String> temp;
      // update products file
      temp = new ArrayList<>();
      for (Product p : machine.getProducts()) {
        temp.add(p.getDescription() + "," + p.getLocation() + "," + p.getPrice() + "," + p
            .getQuantity());
      }
      if (!FileOutputService.writeToFile(VendingMachine.FILENAME_PRODUCTS, temp)) {
        System.out.println("Error");
      }
      window.close();
    }
  }

  private void restockProduct(Product product) {
    if (product.getQuantity() >= 10) {
      Alert.display("Restock Item", "This product is already fully stocked", "Close");
    } else if (product.getDescription().equals("")) {
      Alert.display("Restock Item", "There is no product in this location", "Close");
    } else {
      machine.getProducts().get(machine.getProducts().indexOf(product)).setQuantity(10 - product.getQuantity());
      Alert.display("Restock Item", "Product is now fully stocked", "Close");

      ObservableList<Product> products = FXCollections.observableArrayList(machine.getProducts());
      table.getItems().clear();
      table.setItems(products);
      vbox.getChildren().clear();
      vbox.getChildren().addAll(table, addProductButton, shutdownMachineButton);
    }
  }

  private void removeProduct(Product product) {
    if (ConfirmMenu.display("Remove Product", "Are you sure you want to remove this product?", "Yes", "No")) {
      machine.getProducts().get(machine.getProducts().indexOf(product)).setDescription("");
      machine.getProducts().get(machine.getProducts().indexOf(product)).setPrice(0.00);
      machine.getProducts().get(machine.getProducts().indexOf(product)).setQuantity(-product.getQuantity());
      ObservableList<Product> products = FXCollections.observableArrayList(machine.getProducts());
      table.getItems().clear();
      table.setItems(products);
      vbox.getChildren().clear();
      vbox.getChildren().addAll(table, addProductButton, shutdownMachineButton);
    }
  }

  private void addProduct() {
    String location;
    for (Product p : machine.getProducts()) {
      // if there is room for a new product...
      if (p.getDescription().equals("")) {
        location = p.getLocation();
        Product temp = AddNewProductMenu.displayMenu("Add New Product", location);
        if (temp != null) {
          p.setDescription(temp.getDescription());
          p.setPrice(temp.getPrice());
          p.setQuantity(temp.getQuantity());
          ObservableList<Product> products = FXCollections.observableArrayList(machine.getProducts());
          table.getItems().clear();
          table.setItems(products);
          vbox.getChildren().clear();
          vbox.getChildren().addAll(table, addProductButton, shutdownMachineButton);
        }
        break;
      }
    }
  }

}
