package algorithm;

import interfaces.IEnvironmentController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class QTableGenerator {
  Map<String, Map<Long, Double>> generateQTable(IEnvironmentController environmentController) {

    Map<String, Map<Long, Double>> qValues = new HashMap<>();

    List<String> possibleStates = environmentController.getPossibleStatesIDs();

    List<Long> possibleActions = environmentController.getPossibleActionsIDs();

    for (String newState : possibleStates) {

      Map<Long, Double> actionToQValueMap = generateRandomActionToQValues(possibleActions);

      qValues.put(newState, actionToQValueMap);
    }
    return qValues;
  }

  private Map<Long, Double> generateRandomActionToQValues(List<Long> possibleActions) {

    Map<Long, Double> result = new HashMap<>();
    for (Long newAction : possibleActions) {
      result.put(newAction, 0.0);
    }
    return result;
  }
}
