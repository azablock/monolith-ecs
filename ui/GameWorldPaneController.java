package monolith.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import monolith.cbs.entity.CrateBuilder;
import monolith.cbs.entity.LevelSectorBuilder;
import monolith.cbs.entity.MoEntityBuilderManager;
import monolith.cbs.entity.PlayerActorBuilder;
import monolith.cbs.subsystem.PlayerInputSystem;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;

import static monolith.cbs.component.position.DiscretePositionUtils.pos;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class GameWorldPaneController implements Iterable<Node> {

  private static final Logger LOG = getLogger(GameWorldPaneController.class);

  @Autowired
  private MoEntityBuilderManager entityBuilderManager;

  @Autowired
  private PlayerInputSystem playerInputSystem;

  @FXML
  private Pane gameWorldPane;

  @FXML
  public void initialize() {

    entityBuilderManager.builderFor(LevelSectorBuilder.class)
                        .build();

    entityBuilderManager.builderFor(CrateBuilder.class)
                        .apex(pos(4, 3))
                        .build();

    entityBuilderManager.builderFor(PlayerActorBuilder.class)
                        .apex(pos(2, 5))
                        .build();

    gameWorldPane.setOnMouseClicked(playerInputSystem::handle);
  }

  public Pane getGameWorldPane() {

    return gameWorldPane;
  }

  @Override
  public Iterator<Node> iterator() {

    return gameWorldPane.getChildren().iterator();
  }
}
