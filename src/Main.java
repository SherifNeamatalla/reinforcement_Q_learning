import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import simulation.Simulator;
import view.MainView;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {

    MainView mainView = new MainView();
    new Simulator(mainView);
    primaryStage.setTitle("Genetic Evolution");
    primaryStage.setScene(new Scene(mainView, 300, 275));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
