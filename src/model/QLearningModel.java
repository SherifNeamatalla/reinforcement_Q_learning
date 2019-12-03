package model;

import java.util.Collections;
import java.util.Map;

public class QLearningModel {

  private Map<Long, Map<Long, Double>> qValues;

  public QLearningModel(Map<Long, Map<Long, Double>> qValues) {
    this.qValues = qValues;
  }

  public Long predict(Long state) {
    Map<Long, Double> actionToQValueMap = qValues.get(state);

    return Collections.max(actionToQValueMap.entrySet(), Map.Entry.comparingByValue()).getKey();
  }

  public Map<Long, Map<Long, Double>> getWeights() {
    return qValues;
  }

  public void setWeights(Map<Long, Map<Long, Double>> qValues) {
    this.qValues = qValues;
  }

  public double predictBestValue(Long state) {
    Map<Long, Double> actionToQValueMap = qValues.get(state);

    return actionToQValueMap.get(predict(state));
  }
}
