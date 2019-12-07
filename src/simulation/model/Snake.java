package simulation.model;

import simulation.helper.PositionUtilHelper;
import snakeplayer.model.SnakeActionType;
import snakeplayer.model.SnakeStateType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Snake {

  private int length;
  private Position currentPosition;

  private SnakeActionType currentMovingDirection;

  private Queue<Position> pastPositions = new LinkedList<>();

  private SnakeStateType state;

  public Snake() {
    this.length = 1;
    this.currentPosition = PositionUtilHelper.buildRandomPosition();
    this.currentMovingDirection = SnakeActionType.EAST;
  }

  public void liveTick(SnakeActionType currentSnakeAction) {
    moveNewPosition(currentSnakeAction);
    /*
    if (currentPosition.getX() < 0) currentPosition.setX(SimulationConstants.MAX_X);
    if (currentPosition.getX() > SimulationConstants.MAX_X) currentPosition.setX(0);

    if (currentPosition.getY() < 0) currentPosition.setY(SimulationConstants.MAX_Y);
    if (currentPosition.getY() > SimulationConstants.MAX_Y) currentPosition.setY(0);

     */
  }

  private void moveNewPosition(SnakeActionType currentSnakeAction) {
    Position oldPosition = PositionUtilHelper.copy(currentPosition);
    switch (currentSnakeAction) {
      case NORTH:
        moveNorth();
        break;
      case EAST:
        moveEast();
        break;
      case WEST:
        moveWest();
        break;
      case SOUTH:
        moveSouth();
        break;
    }
    if (this.length > this.pastPositions.size()) this.pastPositions.add(oldPosition);
    else {
      this.pastPositions.poll();
      this.pastPositions.add(oldPosition);
    }
    while (pastPositions.size() - length > 0) {
      pastPositions.poll();
    }
  }

  private void moveEast() {

    if (currentMovingDirection == SnakeActionType.WEST) {
      currentPosition.setX(currentPosition.getX() - SimulationConstants.SCALE_PIXELS);
      currentMovingDirection = SnakeActionType.WEST;
    } else {
      currentPosition.setX(currentPosition.getX() + SimulationConstants.SCALE_PIXELS);
      currentMovingDirection = SnakeActionType.EAST;
    }
  }

  private void moveWest() {
    if (currentMovingDirection == SnakeActionType.EAST) {
      currentPosition.setX(currentPosition.getX() + SimulationConstants.SCALE_PIXELS);
      currentMovingDirection = SnakeActionType.EAST;
    } else {
      currentPosition.setX(currentPosition.getX() - SimulationConstants.SCALE_PIXELS);
      currentMovingDirection = SnakeActionType.WEST;
    }
  }

  private void moveNorth() {
    if (currentMovingDirection == SnakeActionType.SOUTH) {
      currentPosition.setY(currentPosition.getY() + SimulationConstants.SCALE_PIXELS);
      currentMovingDirection = SnakeActionType.SOUTH;
    } else {
      currentPosition.setY(currentPosition.getY() - SimulationConstants.SCALE_PIXELS);
      currentMovingDirection = SnakeActionType.NORTH;
    }
  }

  private void moveSouth() {
    if (currentMovingDirection == SnakeActionType.NORTH) {
      currentPosition.setY(currentPosition.getY() - SimulationConstants.SCALE_PIXELS);
      currentMovingDirection = SnakeActionType.NORTH;
    } else {
      currentPosition.setY(currentPosition.getY() + SimulationConstants.SCALE_PIXELS);
      currentMovingDirection = SnakeActionType.SOUTH;
    }
  }

  public List<Position> getActualBody() {

    if (pastPositions.size() == 0) return new ArrayList<>();
    List<Position> positions = new ArrayList<>(pastPositions);

    positions.remove(0);
    return positions;
  }

  public void restart() {
    this.length = 1;
    this.currentPosition = PositionUtilHelper.buildRandomPosition();
    this.currentMovingDirection = SnakeActionType.EAST;
    this.pastPositions = new LinkedList<>();
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public Position getCurrentPosition() {
    return currentPosition;
  }

  public void setCurrentPosition(Position currentPosition) {
    this.currentPosition = currentPosition;
  }

  public Queue<Position> getPastPositions() {
    return pastPositions;
  }

  public void setPastPositions(Queue<Position> pastPositions) {
    this.pastPositions = pastPositions;
  }

  public SnakeActionType getCurrentMovingDirection() {
    return currentMovingDirection;
  }

  public void setCurrentMovingDirection(SnakeActionType currentMovingDirection) {
    this.currentMovingDirection = currentMovingDirection;
  }

  public SnakeStateType getState() {
    return state;
  }

  public void setState(SnakeStateType state) {
    this.state = state;
  }


}
