package controller;

import interfaces.IEnvironmentController;
import model.ActionResult;
import model.QLearningModel;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {

  private static final double LEARNING_RATE = 0.1;

  private static final double DISCOUNT_RATE = 0.95;

  private static final double EPSILON = 0.1;

  private final IEnvironmentController environmentController;

  public Controller(IEnvironmentController environmentController) {
    this.environmentController = environmentController;
  }

  public QLearningModel train(int numberOfGames) {

    // Initialize model with random weights.

    QLearningModel model =
        new QLearningModel(
            new QTableGenerator()
                .generateQTable(
                    environmentController,
                    environmentController.getWorstReward(),
                    environmentController.getBestReward()));

    for (int i = 0; i < numberOfGames; i++) {
      Long state = environmentController.reset();

      while (true) {
        Long randomAction =
            predictAction(state, environmentController.getPossibleActionsIDs().size(), model);

        ActionResult actionResult = environmentController.performAction(state, randomAction);

        double currentQValue = model.getWeights().get(state).get(randomAction);

        double newQValue =
            getNewQValue(
                currentQValue, actionResult.getReward(), actionResult.getNewState(), model);

        // Update weights
        model.getWeights().get(state).put(randomAction, newQValue);

        state = actionResult.getNewState();

        if (actionResult.isDone()) break;
      }
    }
    return model;
  }

  private Long predictAction(Long state, int actionSpaceSize, QLearningModel model) {
    double randomValue = ThreadLocalRandom.current().nextDouble();

    if (randomValue <= EPSILON) {
      int randomActionIndex = ThreadLocalRandom.current().nextInt(actionSpaceSize);
      // Not so bad for performance as the action space shouldn't be that big.
      return new ArrayList<>(model.getWeights().get(state).keySet()).get(randomActionIndex);
    }

    return model.predict(state);
  }

  // The Q Formula (1-LR) * oldQ + LR * ( Reward + DiscountRate * nextStateBestQValue )
  private double getNewQValue(
      double currentQValue, double reward, Long newState, QLearningModel model) {

    double nextStateBestQValue = model.predictBestValue(newState);
    return (1 - LEARNING_RATE) * currentQValue
        + LEARNING_RATE * (reward + (DISCOUNT_RATE * nextStateBestQValue));
  }
}
