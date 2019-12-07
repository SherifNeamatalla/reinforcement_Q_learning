package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import simulation.Simulator;
import simulation.model.Simulation;
import simulation.model.SimulationConstants;
import view.ai.AIGameCanvas;

public class MainView extends BorderPane {

  private Simulator simulator;
  private AIGameCanvas AIGameCanvas;

  public MainView() {
    this.AIGameCanvas = new AIGameCanvas(SimulationConstants.MAX_X, SimulationConstants.MAX_Y);
    this.setTop(new VBox(new AppToolbar(this)));

    this.setCenter(AIGameCanvas);
  }

  public void start() {

    this.simulator.start();
  }

  public void connect(Simulator simulator) {
    this.simulator = simulator;
  }

  public void draw(Simulation simulation) {
    this.AIGameCanvas.draw(simulation);
  }
}
