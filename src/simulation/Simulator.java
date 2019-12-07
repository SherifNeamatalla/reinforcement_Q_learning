package simulation;

import algorithm.QLearningController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import model.ActionResult;
import model.GenerationMetaInformation;
import model.QLearningConfiguration;
import model.QLearningModel;
import simulation.model.Simulation;
import simulation.model.SimulationConfiguration;
import simulation.model.Snake;
import snakeplayer.SnakeEnvironmentController;
import view.MainView;

public class Simulator {

  private static final int MS_PER_TICK = 1;

  private static final int SNAKE_SPEED = 10;
  private Timeline timeline;

  private MainView mainView;

  private QLearningController QLearningController;

  private Simulation simulation;

  private SimulationConfiguration simulationConfiguration;

  private GenerationMetaInformation lastGenerationMetaInformation;

  private QLearningModel snakeModel;

  private String currentState;

  private SnakeEnvironmentController snakeEnvironmentController;

  private int generationsCount = 1;

  private static final boolean TRAIN_FIRST = true;

  public Simulator(MainView mainView) {

    snakeEnvironmentController = new SnakeEnvironmentController();
    this.QLearningController =
        new QLearningController(
            new QLearningConfiguration(0.1, 0.95, 1.0, 0.005, 0.99993, snakeEnvironmentController));
    this.mainView = mainView;
    this.snakeModel = QLearningController.generateRandomModel();

    this.simulation = new Simulation(new Snake());
    this.currentState = snakeEnvironmentController.reset();

    KeyFrame keyFrame =
        new KeyFrame(
            Duration.millis(MS_PER_TICK * 25),
            actionEvent -> {
              ActionResult actionResult =
                  this.QLearningController.trainOneRound(this.currentState, this.snakeModel);
              this.currentState = actionResult.getNewState();
              this.mainView.draw(simulation);
              if (actionResult.isDone()) {
                this.simulation.getSnake().restart();
                this.simulation.generateFood();
                this.currentState = snakeEnvironmentController.reset();
              }
            });

    this.mainView.connect(this);
    snakeEnvironmentController.connect(this);
    this.timeline = new Timeline(keyFrame);
    this.timeline.setCycleCount(Animation.INDEFINITE);
    this.timeline.setOnFinished(this::onSimulationDone);
  }

  private void onSimulationDone(ActionEvent actionEvent) {

    this.mainView.draw(simulation);
  }

  public void start() {

    if (TRAIN_FIRST) {
      for (int i = 0; i < 10000; i++) {
        this.snakeModel = this.QLearningController.continueTraining(1, this.snakeModel);
        this.simulation.getSnake().restart();
        this.simulation.generateFood();
        this.currentState = this.snakeEnvironmentController.reset();
      }
    }
    this.timeline.play();
  }

  public Simulation getSimulation() {
    return simulation;
  }
}
