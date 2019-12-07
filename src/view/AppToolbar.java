package view;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class AppToolbar extends HBox {

  private MainView mainView;
  private TextField epochsCount;

  public AppToolbar(MainView mainView) {
    this.mainView = mainView;

    this.epochsCount = new TextField();
    this.epochsCount.setText("Epochs");
    this.epochsCount
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (!newValue.matches("\\d*")) {
                epochsCount.setText(newValue.replaceAll("[^\\d]", ""));
              }
            });

    generateButtons();
  }

  private void generateButtons() {

    Button start = new Button("Start");
    start.setOnAction(this::startAction);
    this.getChildren().addAll(start, epochsCount);
  }

  private void startAction(ActionEvent actionEvent) {
    this.mainView.start();
  }
}
