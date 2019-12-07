package snakeplayer;

import interfaces.IEnvironmentController;
import model.ActionResult;
import simulation.Simulator;
import simulation.helper.PositionUtilHelper;
import simulation.model.Position;
import simulation.model.SimulationConstants;
import simulation.model.Snake;
import snakeplayer.model.SnakeActionType;
import snakeplayer.model.SnakeStateType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static snakeplayer.model.SnakeActionType.*;

public class SnakeEnvironmentController implements IEnvironmentController {

  private static final double BEST_REWARD = 1;

  private static final double WORST_REWARD = -2;

  private static final double MIDDLE_REWARD = -0.1;

  private static final int SNAKE_RANGE = 20;

  private static final String LOSING_STATE = "666";

  private static final Map<Long, SnakeActionType> idToActionMap;

  static {
    idToActionMap =
        Map.of(
            0L, NORTH,
            1L, SOUTH,
            2L, EAST,
            3L, WEST);
  }

  private Simulator simulator;

  private static final int VISION_RANGE = SimulationConstants.MAX_X;

  public void connect(Simulator simulator) {
    this.simulator = simulator;
  }

  @Override
  public List<Long> getPossibleActionsIDs() {
    return new ArrayList<>(idToActionMap.keySet());
  }

  @Override
  public List<String> getPossibleStatesIDs() {
    List<String> result = new ArrayList<>();

    for (int direction = 0; direction < 4; direction++) {
      for (int foodX = 0; foodX <= 2; foodX++) {
        for (int foodY = 0; foodY <= 2; foodY++) {
          for (int dangerX = 0; dangerX <= 2; dangerX++) {
            for (int dangerY = 0; dangerY <= 2; dangerY++) {

              String entry = (direction + "" + foodX + "" + foodY + "" + dangerX + "" + dangerY);
              result.add(entry);
            }
          }
        }
      }
    }

    // Losing state
    result.add(LOSING_STATE);
    return result;
  }

  private static int count = 0;

  @Override
  public ActionResult performAction(String state, Long action) {
    // I am 100% sure am being stupid cause am high,this can't be a clean solution.
    double score = 0;
    boolean isDone = false;
    Snake snake = this.simulator.getSimulation().getSnake();
    snake.liveTick(idToActionMap.get(action));
    Position foodPosition = this.simulator.getSimulation().getFoodPosition();

    if (isOffLimits(snake.getCurrentPosition()) || selfClash(snake.getCurrentPosition(), snake)) {

      isDone = true;
    }

    if (isDone) {
      return new ActionResult(LOSING_STATE, WORST_REWARD, true);
    }
    if (ateFood(snake.getCurrentPosition(), foodPosition)) {
      score = BEST_REWARD;
      snake.setLength(snake.getLength() + 1);
      this.simulator.getSimulation().generateFood();

    } else {
      score = MIDDLE_REWARD;
    }

    var newState = getNewState(snake, foodPosition);

    return new ActionResult(newState, score, false);
  }

  private boolean ateFood(Position snakeCurrentPosition, Position foodPosition) {
    return ((snakeCurrentPosition.getX() == foodPosition.getX()))
        && ((snakeCurrentPosition.getY() == foodPosition.getY()));
  }

  private boolean selfClash(Position snakePosition, Snake snake) {

    for (Position position : snake.getActualBody()) {
      if (PositionUtilHelper.areEqual(snakePosition, position)) {
        return true;
      }
    }

    return false;
  }

  private boolean isOffLimits(Position snakePosition) {
    return snakePosition.getX() > SimulationConstants.MAX_X
        || snakePosition.getY() > SimulationConstants.MAX_Y
        || snakePosition.getX() < 0
        || snakePosition.getY() < 0;
  }

  @Override
  public String reset() {
    return "00000";
  }

  private String getNewState(Snake snake, Position foodPosition) {

    SnakeStateType snakeStateType = new SnakeStateType();

    switch (snake.getCurrentMovingDirection()) {
      case WEST:
        snakeStateType.setMovingDirection(3L);

        break;
      case EAST:
        snakeStateType.setMovingDirection(2L);

        break;
      case NORTH:
        snakeStateType.setMovingDirection(0L);

        break;
      case SOUTH:;
        snakeStateType.setMovingDirection(1L);

        break;
    }

    getFoodPosition(snake.getCurrentPosition(), foodPosition, snakeStateType);
    getDanger(snake.getCurrentPosition(), snakeStateType, snake);
    return (snakeStateType.getMovingDirection()
        + ""
        + +snakeStateType.getFoodDirectionX()
        + ""
        + snakeStateType.getFoodDirectionY()
        + ""
        + +snakeStateType.getDangerDirectionX()
        + ""
        + snakeStateType.getDangerDirectionY());
  }

  private void getFoodPosition(
      Position snakePosition, Position position, SnakeStateType snakeStateType) {
    if (position.getY() > snakePosition.getY()) snakeStateType.setFoodDirectionY(2L);
    else if (position.getY() < snakePosition.getY()) snakeStateType.setFoodDirectionY(1L);
    else snakeStateType.setFoodDirectionY(0L);
    if (position.getX() > snakePosition.getX()) snakeStateType.setFoodDirectionX(2L);
    else if (position.getX() < snakePosition.getX()) snakeStateType.setFoodDirectionX(1L);
    else snakeStateType.setFoodDirectionX(0L);
  }

  private void getDanger(Position snakePosition, SnakeStateType snakeStateType, Snake snake) {

    double distanceToMaxX = snakePosition.getX() - SimulationConstants.MAX_X;
    double distanceToMaxY = snakePosition.getY() - SimulationConstants.MAX_Y;

    double closestX = Integer.MAX_VALUE;
    double closestY = Integer.MAX_VALUE;

    if (Math.abs(distanceToMaxX) <= SNAKE_RANGE) {
      snakeStateType.setDangerDirectionX(2L);
      closestX = Math.abs(distanceToMaxX);
    } else if (snakePosition.getX() <= SNAKE_RANGE) {
      snakeStateType.setDangerDirectionX(1L);
      closestX = snakePosition.getX();
    }
    if (Math.abs(distanceToMaxY) <= SNAKE_RANGE) {
      snakeStateType.setDangerDirectionY(2L);
      closestY = Math.abs(distanceToMaxY);
    } else if (snakePosition.getY() <= SNAKE_RANGE) {
      snakeStateType.setDangerDirectionY(1L);
      closestY = snakePosition.getY();
    }

    if (snake.getActualBody().size() > 2)
      for (Position position : snake.getActualBody().subList(0, snake.getActualBody().size() - 2)) {
        double yDifference = snakePosition.getY() - position.getY();
        double xDifference = snakePosition.getX() - position.getX();

        if (snakePosition.getX() == position.getX()
            && Math.abs(yDifference) <= SNAKE_RANGE
            && Math.abs(yDifference) <= closestY) {
          if (yDifference < 0) {
            snakeStateType.setDangerDirectionY(2L);
            closestY = Math.abs(yDifference);
          } else if (yDifference > 0) {
            snakeStateType.setDangerDirectionY(1L);
            closestY = Math.abs(yDifference);
          }
        }
        if (snakePosition.getY() == position.getY()
            && Math.abs(xDifference) <= SNAKE_RANGE
            && Math.abs(xDifference) <= closestX) {
          if (xDifference < 0) {
            snakeStateType.setDangerDirectionX(2L);
            closestX = Math.abs(xDifference);
          } else if (xDifference > 0) {
            snakeStateType.setDangerDirectionX(1L);
            closestX = Math.abs(xDifference);
          }
        }
      }

    if (snakeStateType.getDangerDirectionX() == null) snakeStateType.setDangerDirectionX(0L);
    if (snakeStateType.getDangerDirectionY() == null) snakeStateType.setDangerDirectionY(0L);
  }
}
