package monolith.cbs.subsystem;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import monolith.cbs.component.bounding_box.BoundingBox;
import monolith.cbs.component.graphics.Graphics;
import monolith.cbs.component.graphics_addition_request.GraphicsAdditionRequest;
import monolith.cbs.component.player_input.PlayerInput;
import monolith.cbs.entity.MoMetaEntity;
import monolith.ui.GameWorldPaneController;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Consumer;

import static javafx.scene.paint.Color.*;
import static monolith.cbs.component.graphics.GraphicsConstants.FIELD_WORLD_SIZE;
import static monolith.cbs.entity.MoEntityManager.ENTITY_MANAGER;
import static monolith.cbs.entity.MoMetaEntity.loadFromGameWorld;

@Component
public class GraphicsManagementSystem implements MoSubSystem {

  @Autowired
  private GameWorldPaneController gameWorldPaneController;

  @Override
  public void processOneGameTick() {

    ENTITY_MANAGER.getAllEntitiesPossessingComponents(Graphics.class,
                                                      GraphicsAdditionRequest.class).forEach(uuid -> {

      MoMetaEntity entity = loadFromGameWorld(uuid);

      if(entity.has(BoundingBox.class))
        fillInBoundingBoxGraphics(entity.get(BoundingBox.class), (Group) entity.get(Graphics.class).node);

      entity.remove(entity.get(GraphicsAdditionRequest.class));
      gameWorldPaneController.getGameWorldPane().getChildren().add(entity.get(Graphics.class).node);
    });
  }

  private void fillInBoundingBoxGraphics(@NotNull BoundingBox boundingBox, @NotNull Group group) {

    boundingBox.iterator().forEachRemaining(apex -> {

      Rectangle rectangle = new Rectangle(apex.getX() * FIELD_WORLD_SIZE,
                                          apex.getY() * FIELD_WORLD_SIZE,
                                          FIELD_WORLD_SIZE, FIELD_WORLD_SIZE);

      rectangle.setFill(GREY);
      rectangle.setStroke(BLACK);
      rectangle.setStrokeWidth(1.5);
      rectangle.setOnMouseEntered(event -> rectangle.setFill(LIGHTGREEN));
      rectangle.setOnMouseExited(event -> rectangle.setFill(GREY));

      group.getChildren().add(rectangle);
    });
  }
}
