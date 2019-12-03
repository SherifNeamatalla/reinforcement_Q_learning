import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.QLearningModel;
import snakeplayer.SnakeEnvironmentController;
import view.MainView;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    QLearningModel model = new Controller(new SnakeEnvironmentController()).train(20);

    MainView mainView = new MainView();
    primaryStage.setTitle("Genetic Evolution");
    primaryStage.setScene(new Scene(mainView, 300, 275));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
