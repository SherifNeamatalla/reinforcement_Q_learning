package model;

public class ActionResult {

  private String newState;

  private double reward;

  private boolean isDone;

  public ActionResult(String newState, double reward, boolean isDone) {
    this.newState = newState;
    this.reward = reward;
    this.isDone = isDone;
  }

  public String getNewState() {
    return newState;
  }

  public void setNewState(String newState) {
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
