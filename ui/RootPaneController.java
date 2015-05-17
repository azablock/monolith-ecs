package monolith.ui;

import monolith.MoContext;
import monolith.MoUIState;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static monolith.MoUIState.MENU;
import static monolith.MoUIState.NEW_GAME;

@Component
public class RootPaneController {

  @Autowired
  private MenuPaneController menuPaneController;

  @Autowired
  private NewGamePaneController newGamePaneController;

  @Autowired
  private InGamePaneController inGamePaneController;

  @Autowired
  private MoContext moContext;

  @PostConstruct
  public void initialize() {

    moContext.stateProperty().addListener((observable, currentState, newState) -> {

      NodeController currentStateController = stateController(currentState);
      NodeController newStateController = stateController(newState);

      currentStateController.getNode().setVisible(false);
      newStateController.getNode().setVisible(true);
    });
  }

  @NotNull
  private NodeController stateController(MoUIState state) {

    return state == MENU ? menuPaneController : state == NEW_GAME ? newGamePaneController : inGamePaneController;
  }
}
