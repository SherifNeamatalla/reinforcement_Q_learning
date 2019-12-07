package simulation.model;

import simulation.helper.PositionUtilHelper;

public class Simulation {

  private Snake snake;

  private Position foodPosition;

  public Simulation(Snake snake) {

    this.snake = snake;
    generateFood();
  }

  public Snake getSnake() {
    return snake;
  }

  public void setSnake(Snake snake) {
    this.snake = snake;
  }

  public void generateFood() {
    this.foodPosition = PositionUtilHelper.buildRandomPosition();
  }

  public Position getFoodPosition() {
    return foodPosition;
  }

  public void setFoodPosition(Position foodPosition) {
    this.foodPosition = foodPosition;
  }
}
