package org.endamccarthy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * ConfirmMenu
 * <p>
 * A Utility class used to display a pop-up window requiring user confirmation.
 */
public final class ConfirmMenu {

  private static boolean answer;

  /**
   * Default Constructor
   * <p>
   * As this is a utility class which cannot be instantiated, the constructor is private and throws
   * an UnsupportedOperationException.
   */
  private ConfirmMenu() {
    throw new UnsupportedOperationException();
  }

  /**
   * displayMenu
   *
   * @param title         This is title of the pop-up window.
   * @param message       This is the message displayed to the user.
   * @param buttonOneText This is the text shown on button one.
   * @param buttonTwoText This is the text shown on button two.
   * @return A boolean (true if button one is clicked, false otherwise).
   */
  public static boolean displayMenu(String title, String message, String buttonOneText,
      String buttonTwoText) {
    // set up window
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(300);
    // set up label
    Label label = new Label();
    label.setText(message);
    // set up buttons
    Button yesButton = new Button(buttonOneText);
    Button noButton = new Button(buttonTwoText);
    yesButton.setOnAction(e -> {
      answer = true;
      window.close();
    });
    noButton.setOnAction(e -> {
      answer = false;
      window.close();
    });
    HBox buttonLayout = new HBox(10);
    buttonLayout.getChildren().addAll(yesButton, noButton);
    // set up overall layout
    VBox confirmLayout = new VBox(20);
    confirmLayout.setPadding(new Insets(20));
    confirmLayout.getChildren().addAll(label, buttonLayout);
    confirmLayout.setAlignment(Pos.CENTER_LEFT);
    confirmLayout.getStyleClass().add("popup-background");
    Scene confirmScene = new Scene(confirmLayout, 350, 250);
    confirmScene.getStylesheets().add("style.css");
    window.setScene(confirmScene);
    window.showAndWait();
    return answer;
  }

}
