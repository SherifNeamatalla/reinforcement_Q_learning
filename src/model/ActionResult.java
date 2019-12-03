package model;

public class ActionResult {

  private Long newState;

  private double reward;

  private boolean isDone;

  public ActionResult(Long newState, double reward, boolean isDone) {
    this.newState = newState;
    this.reward = reward;
    this.isDone = isDone;
  }

  public Long getNewState() {
    return newState;
  }

  public void setNewState(Long newState) {
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
