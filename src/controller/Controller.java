package controller;

import interfaces.IEnvironmentController;
import model.Action;
import model.ActionResult;
import model.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {

  private static final double LEARNING_RATE = 0.1;

  private static final double DISCOUNT_RATE = 0.95;

  private static final double BEST_REWARD = 10;

  private static final double WORST_REWARD = -10;

  private static final double EPSILON = 0.1;

  private Map<State, Map<Action, Double>> qValues;

  public void train(IEnvironmentController environmentController, int numberOfGames) {

    qValues = QTableGenerator.generateQTable(environmentController, WORST_REWARD, BEST_REWARD);

    for (int i = 0; i < numberOfGames; i++) {
      State state = environmentController.reset();

      while (!environmentController.isDone(state)) {
        Action randomAction =
            predictAction(state, environmentController.getPossibleActions().size());

        ActionResult actionResult = environmentController.performAction(state, randomAction);

        double currentQValue = qValues.get(state).get(randomAction);

        double newQValue =
            getNewQValue(currentQValue, actionResult.getReward(), actionResult.getNewState());

        qValues.get(state).put(randomAction, newQValue);

        state = actionResult.getNewState();
      }
    }
  }

  private Action predictAction(State state, int actionSpaceSize) {
    double randomValue = ThreadLocalRandom.current().nextDouble();

    if (randomValue <= EPSILON) {
      int randomActionIndex = ThreadLocalRandom.current().nextInt(actionSpaceSize);
      // Not so bad for performance as the action space shouldn't be that big.
      return new ArrayList<>(qValues.get(state).keySet()).get(randomActionIndex);
    }

    return getBestQValueAction(state);
  }

  // The Q Formula (1-LR) * oldQ + LR * ( Reward + DiscountRate * nextStateBestQValue )
  private double getNewQValue(double currentQValue, double reward, State newState) {

    double nextStateBestQValue = getBestQValue(newState);
    return (1 - LEARNING_RATE) * currentQValue
        + LEARNING_RATE * (reward + (DISCOUNT_RATE * nextStateBestQValue));
  }

  private double getBestQValue(State state) {
    Map<Action, Double> actionToQValueMap = qValues.get(state);

    return actionToQValueMap.get(getBestQValueAction(state));
  }

  private Action getBestQValueAction(State state) {
    Map<Action, Double> actionToQValueMap = qValues.get(state);

    return Collections.max(actionToQValueMap.entrySet(), Map.Entry.comparingByValue()).getKey();
  }
}
