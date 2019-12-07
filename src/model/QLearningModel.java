package model;

import java.util.Collections;
import java.util.Map;

public class QLearningModel {

  private Map<String, Map<Long, Double>> qValues;

  public QLearningModel(Map<String, Map<Long, Double>> qValues) {
    this.qValues = qValues;
  }

  public Long predict(String state) {
    Map<Long, Double> actionToQValueMap = qValues.get(state);

    return Collections.max(actionToQValueMap.entrySet(), Map.Entry.comparingByValue()).getKey();
  }

  public Map<String, Map<Long, Double>> getWeights() {
    return qValues;
  }

  public void setWeights(Map<String, Map<Long, Double>> qValues) {
    this.qValues = qValues;
  }

  public double predictNextBestQValue(String state) {
    Long predictedAction = predict(state);
    return this.qValues.get(state).get(predictedAction);
  }

  public void updateState(String state, Long predictedAction, double newQValue) {
    qValues.get(state).put(predictedAction, newQValue);
  }
}
