package org.endamccarthy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * AlertBox
 * <p>
 * A Utility class used to display a pop-up window (usually called when user attempts invalid
 * procedure).
 */
public final class AlertBox {

  /**
   * Default Constructor
   * <p>
   * As this is a utility class which cannot be instantiated, the constructor is private and throws
   * an UnsupportedOperationException.
   */
  private AlertBox() {
    throw new UnsupportedOperationException();
  }

  /**
   * displayBox
   *
   * @param title      This is title of the pop-up window.
   * @param message    This is the message displayed to the user.
   * @param buttonText This is the text shown on the button.
   */
  public static void displayBox(String title, String message, String buttonText) {
    // set up window
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(250);
    // set up label
    Label label = new Label();
    label.setText(message);
    // set up button
    Button button = new Button(buttonText);
    button.setOnAction(e -> window.close());
    // set up overall layout
    VBox confirmLayout = new VBox(10);
    confirmLayout.setPadding(new Insets(10));
    confirmLayout.getChildren().addAll(label, button);
    confirmLayout.setAlignment(Pos.CENTER);
    confirmLayout.getStyleClass().add("popup-background");
    Scene confirmScene = new Scene(confirmLayout, 350, 250);
    confirmScene.getStylesheets().add("style.css");
    window.setScene(confirmScene);
    window.showAndWait();
  }

}
