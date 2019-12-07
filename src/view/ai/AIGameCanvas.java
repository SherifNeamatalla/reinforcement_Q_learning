package view.ai;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import simulation.model.Position;
import simulation.model.Simulation;
import simulation.model.SimulationConstants;
import simulation.model.Snake;

import java.util.WeakHashMap;

public class AIGameCanvas extends Canvas {

  public AIGameCanvas(double width, double height) {
    super(width, height);
    this.getGraphicsContext2D().setFill(Color.BLACK);
    this.getGraphicsContext2D().fillRect(0, 0, this.getWidth(), this.getHeight());
  }

  public void draw(Simulation simulation) {
    this.getGraphicsContext2D().clearRect(0, 0, this.getWidth(), this.getHeight());
    this.getGraphicsContext2D().setFill(Color.BLACK);
    this.getGraphicsContext2D().fillRect(0, 0, this.getWidth(), this.getHeight());

    drawFirstPosition(simulation.getSnake().getCurrentPosition());

    drawBody(simulation.getSnake());
    drawFood(simulation.getFoodPosition());
  }

  private void drawBody(Snake snake) {
    for (Position position : snake.getActualBody()) {
      drawPosition(position);
    }
  }

  private void drawFood(Position foodPosition) {
    var graphics = this.getGraphicsContext2D();
    graphics.setFill(Color.RED);
    graphics.fillRect(foodPosition.getX(), foodPosition.getY(), SimulationConstants.SCALE_PIXELS, SimulationConstants.SCALE_PIXELS);
  }

  private void drawPosition(Position position) {
    var graphics = this.getGraphicsContext2D();
    graphics.setFill(Color.WHITE);
    graphics.fillRect(position.getX(), position.getY(), SimulationConstants.SCALE_PIXELS,SimulationConstants.SCALE_PIXELS);
  }

  private void drawFirstPosition(Position position) {
    var graphics = this.getGraphicsContext2D();
    graphics.setFill(Color.WHITE);
    graphics.fillRect(position.getX(), position.getY(), SimulationConstants.SCALE_PIXELS, SimulationConstants.SCALE_PIXELS);
  }
}
