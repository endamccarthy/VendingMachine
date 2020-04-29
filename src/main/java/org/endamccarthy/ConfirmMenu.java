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

public final class ConfirmMenu {

  private static boolean answer;

  private ConfirmMenu() {
    throw new UnsupportedOperationException();
  }

  public static boolean displayMenu(String title, String message, String buttonOneText, String buttonTwoText) {
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(300);
    Label label = new Label();
    label.setText(message);

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

    VBox confirmLayout = new VBox(20);
    confirmLayout.setPadding(new Insets(20));
    HBox buttonLayout = new HBox(10);
    buttonLayout.getChildren().addAll(yesButton, noButton);
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
