package interfaces;

import model.Action;
import model.ActionResult;
import model.State;

import java.util.List;

public interface IEnvironmentController {

  List<Action> getPossibleActions();

  List<State> getPossibleStates();

  ActionResult performAction(State state, Action action);

  State reset();

  boolean isDone(State state);
}
