package interfaces;

import model.ActionResult;

import java.util.List;

public interface IEnvironmentController {

  List<Long> getPossibleActionsIDs();

  List<Long> getPossibleStatesIDs();

  ActionResult performAction(Long state, Long action);

  Long reset();

  double getBestReward();

  double getWorstReward();
}
