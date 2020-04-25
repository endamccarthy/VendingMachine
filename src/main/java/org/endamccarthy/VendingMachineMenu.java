package org.endamccarthy;

import javafx.stage.Stage;

public abstract class VendingMachineMenu {

  protected Stage window;
  protected static User user;
  protected boolean loggedIn;

  public VendingMachineMenu() {
    this(new Stage());
  }

  public VendingMachineMenu(Stage window) {
    this.window = window;
    loggedIn = false;
  }

  public abstract void run(VendingMachine machine);

}
