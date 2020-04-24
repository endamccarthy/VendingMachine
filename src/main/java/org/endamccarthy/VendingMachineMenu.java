package org.endamccarthy;

import javafx.stage.Stage;

public abstract class VendingMachineMenu {

  protected Stage window;
  protected static User user;

  public VendingMachineMenu() {
    this(new Stage());
  }

  public VendingMachineMenu(Stage window) {
    this.window = window;
  }

  public abstract void run(VendingMachine machine);

  protected void setupScene() {

  }

}
