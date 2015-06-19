package monolith.ui;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import monolith.cbs.component.graphics.Graphics;
import monolith.cbs.component.position.ApexPosition;
import monolith.cbs.entity.*;
import monolith.cbs.subsystem.PlayerInputSystem;
import monolith.cbs.subsystem.tools.vm_translator.VMTranslator;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;

import static monolith.cbs.component.graphics.GraphicsConstants.FIELD_WORLD_SIZE;
import static monolith.cbs.component.position.DiscretePositionUtils.pos;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class GameWorldPaneController implements Iterable<Node> {

  private static final Logger LOG = getLogger(GameWorldPaneController.class);

  @Autowired
  private MoEntityBuilderManager entityBuilderManager;

  @Autowired
  private PlayerInputSystem playerInputSystem;

  @Autowired
  private VMTranslator vmTranslator;

  @FXML
  private Pane gameWorldPane;

  @FXML
  public void initialize() {

//    entityBuilderManager.builderFor(LevelSectorBuilder.class)
//                        .build();

    entityBuilderManager.builderFor(CrateBuilder.class)
                        .apex(pos(10, 8))
                        .build();

    MoMetaEntity playerActor = entityBuilderManager.builderFor(PlayerActorBuilder.class)
                                             .apex(pos(8, 8))
                                             .build();

    Point2D initRelocatePoint = vmTranslator.fromDiscrete(playerActor.get(ApexPosition.class).getApex());
//    playerActor.get(Graphics.class).node.relocate(initRelocatePoint.getX(), initRelocatePoint.getY());
    playerActor.get(Graphics.class).node.relocate(playerActor.get(ApexPosition.class).getX() * FIELD_WORLD_SIZE,
                                                  playerActor.get(ApexPosition.class).getY() * FIELD_WORLD_SIZE);

    gameWorldPane.setOnMouseClicked(playerInputSystem::handle);


    Rectangle rectangle = new Rectangle(FIELD_WORLD_SIZE, FIELD_WORLD_SIZE, new Color(0.1, 0.1, 0.1, 1.0));
    rectangle.setStroke(Color.GREEN);
    rectangle.setStrokeWidth(2.0);
    gameWorldPane.getChildren().add(rectangle);
    gameWorldPane.setOnMouseMoved(event -> rectangle.relocate(( (int) (event.getX() / FIELD_WORLD_SIZE)) * FIELD_WORLD_SIZE,
                                                               ((int) (event.getY() / FIELD_WORLD_SIZE)) * FIELD_WORLD_SIZE));
  }

  @Override
  public Iterator<Node> iterator() {

    return gameWorldPane.getChildren().iterator();
  }

  public void addChild(@NotNull final Node node) {

    gameWorldPane.getChildren().add(node);
  }
}
