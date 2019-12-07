package view.human;

import simulation.model.Simulation;
import simulation.model.Snake;
import snakeplayer.model.SnakeActionType;
import view.ai.AIGameCanvas;

import java.util.Scanner;

public class HumanGameCanvas extends AIGameCanvas {

  private Simulation simulation;

  public HumanGameCanvas(double width, double height) {
    super(width, height);
    boolean isRunning = true;

    this.simulation = new Simulation(new Snake());

    Snake snake = simulation.getSnake();

    Scanner scanner = new Scanner(System.in);
    while (isRunning) {
      draw(simulation);
      char input;
      switch (input = scanner.nextLine().charAt(0)) {
        case 'l':
          snake.liveTick(SnakeActionType.WEST);
          break;
        case 'r':
          snake.liveTick(SnakeActionType.EAST);

          break;
        case 'u':
          snake.liveTick(SnakeActionType.NORTH);

          break;
        case 'd':
          snake.liveTick(SnakeActionType.SOUTH);

          break;
      }
    }
  }
}
