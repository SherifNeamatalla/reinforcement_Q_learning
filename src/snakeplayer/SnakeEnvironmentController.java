package snakeplayer;

import interfaces.IEnvironmentController;
import model.ActionResult;
import snakeplayer.model.SnakeActionType;
import snakeplayer.model.SnakeStateType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static snakeplayer.model.SnakeActionType.*;
import static snakeplayer.model.SnakeStateType.*;

public class SnakeEnvironmentController implements IEnvironmentController {

  private static final double BEST_REWARD = 10;

  private static final double WORST_REWARD = -10;

  private static final Map<Long, SnakeStateType> idToStateMap;

  static {
    idToStateMap =
        Map.of(
            0L, LOOKING_TO_NOTHING,
            1L, LOOKING_TO_FOOD,
            2L, LOOKING_TO_BODY,
            3L, LOOKING_TO_WALL,
            4L, DEAD);
  }

  private static final Map<Long, SnakeActionType> idToActionMap;

  static {
    idToActionMap =
        Map.of(
            0L, FORWARD,
            1L, LEFT,
            2L, RIGHT);
  }

  @Override
  public List<Long> getPossibleActionsIDs() {
    return new ArrayList<>(idToActionMap.keySet());
  }

  @Override
  public List<Long> getPossibleStatesIDs() {
    return new ArrayList<>(idToStateMap.keySet());
  }

  @Override
  public ActionResult performAction(Long state, Long action) {
    return new ActionResult(state, 0, false);
  }

  @Override
  public Long reset() {
    return 0L;
  }

  @Override
  public double getBestReward() {
    return BEST_REWARD;
  }

  @Override
  public double getWorstReward() {
    return WORST_REWARD;
  }
}
