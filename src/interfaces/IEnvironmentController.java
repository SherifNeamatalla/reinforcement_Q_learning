package interfaces;

import model.ActionResult;

import java.util.List;

public interface IEnvironmentController {

  List<Long> getPossibleActionsIDs();

  List<String> getPossibleStatesIDs();

  ActionResult performAction(String state, Long action);

  String reset();
}
