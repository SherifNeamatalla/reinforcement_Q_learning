package controller;

import interfaces.IEnvironmentController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

class QTableGenerator {
  Map<Long, Map<Long, Double>> generateQTable(
      IEnvironmentController environmentController, double worstReward, double bestReward) {

    Map<Long, Map<Long, Double>> qValues = new HashMap<>();

    List<Long> possibleStates = environmentController.getPossibleStatesIDs();

    List<Long> possibleActions = environmentController.getPossibleActionsIDs();

    for (Long newState : possibleStates) {

      Map<Long, Double> actionToQValueMap =
          generateRandomActionToQValues(possibleActions, worstReward, bestReward);

      qValues.put(newState, actionToQValueMap);
    }
    return qValues;
  }

  private Map<Long, Double> generateRandomActionToQValues(
      List<Long> possibleActions, double worstReward, double bestReward) {

    Map<Long, Double> result = new HashMap<>();
    for (Long newAction : possibleActions) {
      double randomValue = ThreadLocalRandom.current().nextDouble(worstReward, bestReward);

      result.put(newAction, randomValue);
    }
    return result;
  }
}
