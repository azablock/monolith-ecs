package monolith.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import monolith.MoContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static monolith.MoUIState.NEW_GAME;

@Component
public class MenuPaneController implements NodeController {

  @Autowired
  private MoContext moContext;

  @FXML
  private Button continueButton;

  @FXML
  private Button newGameButton;

  @FXML
  private Button optionsButton;

  @FXML
  private Button exitButton;

  @FXML
  private BorderPane menuPane;

  private static final Logger LOG = LoggerFactory.getLogger(MenuPaneController.class);

  @NotNull
  @Override
  public Node getNode() {

    return menuPane;
  }

  @FXML
  public void onContinue() {

    moContext.stateProperty().setValue(NEW_GAME);
  }

  @FXML
  public void onNewGame() {

    moContext.stateProperty().setValue(NEW_GAME);
  }

  @FXML
  public void onOptions() {

  }

  @FXML
  public void onExit() {

    System.exit(0);
  }
}
