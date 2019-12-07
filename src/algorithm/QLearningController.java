package algorithm;

import model.ActionResult;
import model.QLearningConfiguration;
import model.QLearningModel;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class QLearningController {

  private QLearningConfiguration qLearningConfiguration;

  public QLearningController(QLearningConfiguration qLearningConfiguration) {
    this.qLearningConfiguration = qLearningConfiguration;
  }

  public QLearningModel trainNew(int numberOfGames) {

    // Initialize model with random weights.

    QLearningModel model =
        new QLearningModel(
            new QTableGenerator()
                .generateQTable(qLearningConfiguration.getEnvironmentController()));

    return train(numberOfGames, model);
  }

  public QLearningModel continueTraining(int numberOfGames, QLearningModel model) {

    return train(numberOfGames, model);
  }

  private QLearningModel train(int numberOfGames, QLearningModel model) {
    for (int i = 0; i < numberOfGames; i++) {
      String state = qLearningConfiguration.getEnvironmentController().reset();

      while (true) {
        var actionResult = trainOneRound(state, model);

        state = actionResult.getNewState();

        if (actionResult.isDone()) break;
      }
    }
    return model;
  }

  private Long predictAction(String state, int actionSpaceSize, QLearningModel model) {
    double randomValue = ThreadLocalRandom.current().nextDouble();

    if (randomValue <= qLearningConfiguration.getEpsilon()) {
      int randomActionIndex = ThreadLocalRandom.current().nextInt(actionSpaceSize);
      // Not so bad for performance as the action space shouldn't be that big.
      return new ArrayList<>(model.getWeights().get(state).keySet()).get(randomActionIndex);
    }

    return model.predict(state);
  }

  // The Q Formula (1-LR) * oldQ + LR * ( Reward + DiscountRate * nextStateBestQValue )
  private double getNewQValue(
      double currentQValue, double reward, String newState, QLearningModel model) {

    double nextStateBestQValue = model.predictNextBestQValue(newState);
    return (1 - qLearningConfiguration.getLearningRate()) * currentQValue
        + qLearningConfiguration.getLearningRate()
            * (reward + (qLearningConfiguration.getGamma() * nextStateBestQValue));
  }

  public QLearningModel generateRandomModel() {
    return new QLearningModel(
        new QTableGenerator().generateQTable(qLearningConfiguration.getEnvironmentController()));
  }

  public ActionResult trainOneRound(String state, QLearningModel snakeModel) {

    Long predictedAction =
        predictAction(
            state,
            qLearningConfiguration.getEnvironmentController().getPossibleActionsIDs().size(),
            snakeModel);

    ActionResult actionResult =
        qLearningConfiguration.getEnvironmentController().performAction(state, predictedAction);

    double currentQValue = snakeModel.getWeights().get(state).get(predictedAction);

    double newQValue =
        getNewQValue(
            currentQValue, actionResult.getReward(), actionResult.getNewState(), snakeModel);

    snakeModel.updateState(state, predictedAction, newQValue);

    if (qLearningConfiguration.getEpsilon() >= qLearningConfiguration.getEpsilonMin())
      qLearningConfiguration.setEpsilon(
          qLearningConfiguration.getEpsilon() * qLearningConfiguration.getEpsilonDecay());

    return actionResult;
  }
}
