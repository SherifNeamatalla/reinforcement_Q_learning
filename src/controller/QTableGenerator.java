package controller;

import interfaces.IEnvironmentController;
import model.Action;
import model.State;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class QTableGenerator {
  public static Map<State, Map<Action, Double>> generateQTable(
      IEnvironmentController environmentController,double worstReward,double bestReward) {

    Map<State, Map<Action, Double>> qValues = new HashMap<>();

    List<State> possibleStates = environmentController.getPossibleStates();

    List<Action> possibleActions = environmentController.getPossibleActions();

    for (int i = 0; i < possibleStates.size(); i++) {

      State newState = possibleStates.get(i);

      Map<Action, Double> actionToQValueMap = generateRandomActionToQValues(possibleActions,worstReward,bestReward);

      qValues.put(newState, actionToQValueMap);
    }
    return qValues;
  }

  public static Map<Action, Double> generateRandomActionToQValues(List<Action> possibleActions,double worstReward,double bestReward) {

    Map<Action, Double> result = new HashMap<>();
    for (int i = 0; i < possibleActions.size(); i++) {
      Action newAction = possibleActions.get(i);

      double randomValue = ThreadLocalRandom.current().nextDouble(worstReward, bestReward);

      result.put(newAction, randomValue);
    }
    return result;
  }
}
