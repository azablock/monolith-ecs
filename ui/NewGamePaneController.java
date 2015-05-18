package monolith.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import monolith.GameLoop;
import monolith.MoContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static monolith.MoUIState.IN_GAME;

@Component
public class NewGamePaneController implements NodeController {

  @Autowired
  private MoContext moContext;

  @Autowired
  private GameLoop gameLoop;

  @FXML
  private BorderPane newGamePane;

  @FXML
  private Button startGameButton;

  @NotNull
  @Override
  public Node getNode() {

    return newGamePane;
  }

  public void onStartGame() {

    gameLoop.start();
    moContext.stateProperty().setValue(IN_GAME);
  }
}
