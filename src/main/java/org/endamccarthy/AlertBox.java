package org.endamccarthy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public final class AlertBox {

  private AlertBox() {
    throw new UnsupportedOperationException();
  }

  public static void displayBox(String title, String message, String buttonText) {
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(250);
    Label label = new Label();
    label.setText(message);

    Button button = new Button(buttonText);

    button.setOnAction(e -> window.close());

    VBox confirmLayout = new VBox(10);
    confirmLayout.setPadding(new Insets(10));
    confirmLayout.getChildren().addAll(label, button);
    confirmLayout.setAlignment(Pos.CENTER);
    Scene confirmScene = new Scene(confirmLayout, 350, 250);
    confirmScene.getStylesheets().add("style.css");
    window.setScene(confirmScene);
    window.showAndWait();
  }

}
