package monolith.ui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import monolith.GameLoop;
import monolith.MoContext;
import monolith.cbs.entity.MoEntityBuilderManager;
import monolith.cbs.entity.MoEntityManager;
import monolith.cbs.subsystem.SubSystemManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static javafx.scene.input.KeyCode.F1;
import static monolith.MoUIState.MENU;
import static monolith.cbs.entity.MoEntityManager.*;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class InGamePaneController implements NodeController {

  @Autowired
  private MoContext moContext;

  @Autowired
  private GameLoop gameLoop;

  @Autowired
  private MoEntityBuilderManager entityBuilderManager;

  @Autowired
  private SubSystemManager subSystemManager;

  @FXML
  private Pane inGamePane;

  @FXML
  private Button backToMenuButton;

  @FXML
  private Label debugLabel;

  private static final Logger LOG = getLogger(InGamePaneController.class);

  @FXML
  public void initialize() {

    inGamePane.setOnKeyPressed(event -> {

      if (event.getCode() == F1) debugLabel.setVisible(!debugLabel.isVisible());
    });

  }

  public Label getDebugLabel() {

    return debugLabel;
  }

  @FXML
  public void onBackToMenu() {

    gameLoop.stop();
    moContext.stateProperty().setValue(MENU);
  }

  @NotNull
  @Override
  public Node getNode() {

    return inGamePane;
  }
}
