package model;

public class ActionResult {

  private State newState;

  private double reward;

  private boolean isDone;

  public ActionResult(State newState, double reward, boolean isDone) {
    this.newState = newState;
    this.reward = reward;
    this.isDone = isDone;
  }

  public State getNewState() {
    return newState;
  }

  public void setNewState(State newState) {
    this.newState = newState;
  }

  public double getReward() {
    return reward;
  }

  public void setReward(double reward) {
    this.reward = reward;
  }

  public boolean isDone() {
    return isDone;
  }

  public void setDone(boolean done) {
    isDone = done;
  }
}
