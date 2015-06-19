package monolith.cbs.subsystem;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import monolith.cbs.component.bounding_box.BoundingBox;
import monolith.cbs.component.graphics.Graphics;
import monolith.cbs.component.graphics.GraphicsAdditionRequest;
import monolith.cbs.component.player_input.PlayerInput;
import monolith.cbs.component.position.ApexPosition;
import monolith.cbs.entity.MoMetaEntity;
import monolith.cbs.subsystem.tools.vm_translator.VMTranslator;
import monolith.error.MoDataException;
import monolith.ui.GameWorldPaneController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

import static com.google.common.collect.Collections2.filter;
import static monolith.cbs.entity.MoEntityManager.ENTITY_MANAGER;
import static monolith.cbs.entity.MoMetaEntity.loadFromGameWorld;

@Component
public class RenderSystem implements MoSubSystem {

  @Autowired
  private VMTranslator vmTranslator;

  @Autowired
  private GameWorldPaneController gameWorldPaneController;

  private Set<UUID> allWithGraphics;

  @Override
  public void processOneGameTick() {

    allWithGraphics = ENTITY_MANAGER.getAllEntitiesPossessingComponent(Graphics.class);

    addGraphics();
    removeGraphics();
    updateGraphics();
  }

  private void addGraphics() {

    filter(allWithGraphics, input -> loadFromGameWorld(input).has(GraphicsAdditionRequest.class)).forEach(uuid -> {

      MoMetaEntity withGraphicsAdditionRequest = loadFromGameWorld(uuid);
      gameWorldPaneController.addChild(withGraphicsAdditionRequest.get(Graphics.class).node, withGraphicsAdditionRequest.has(ApexPosition.class));
      withGraphicsAdditionRequest.remove(withGraphicsAdditionRequest.get(GraphicsAdditionRequest.class));
    });
  }

  private void removeGraphics() {

  }

  private void updateGraphics() {

    allWithGraphics.forEach(uuid -> {

      MoMetaEntity entity = loadFromGameWorld(uuid);
      Node node = entity.get(Graphics.class).node;
      Point2D relocatePoint;

      if (entity.has(ApexPosition.class))
        relocatePoint = vmTranslator.fromDiscrete(entity.get(ApexPosition.class).getApex());

      else if (entity.has(BoundingBox.class))
        relocatePoint = vmTranslator.fromDiscrete(entity.get(BoundingBox.class).getTopLeft());

      else throw new MoDataException("cannot relocate node");

      if (!entity.has(PlayerInput.class))
        node.relocate(relocatePoint.getX(), relocatePoint.getY());
    });
  }
}
