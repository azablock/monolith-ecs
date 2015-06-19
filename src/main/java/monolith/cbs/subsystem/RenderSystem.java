package monolith.cbs.subsystem;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import monolith.cbs.component.graphics.Graphics;
import monolith.cbs.component.graphics.GraphicsAdditionRequest;
import monolith.cbs.component.player_input.PlayerInput;
import monolith.cbs.component.position.Apex;
import monolith.cbs.component.position.ApexPosition;
import monolith.cbs.entity.MoMetaEntity;
import monolith.cbs.subsystem.tools.vm_translator.VMTranslator;
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
      gameWorldPaneController.addChild(withGraphicsAdditionRequest.get(Graphics.class).node);
      withGraphicsAdditionRequest.remove(withGraphicsAdditionRequest.get(GraphicsAdditionRequest.class));
    });
  }

  private void removeGraphics() {

  }

  private void updateGraphics() {

    filter(allWithGraphics, input -> loadFromGameWorld(input).has(ApexPosition.class)).forEach(uuid -> {

      MoMetaEntity entity = loadFromGameWorld(uuid);
      Node node = entity.get(Graphics.class).node;
      Apex apex = entity.get(ApexPosition.class).getApex();
      Point2D relocatePoint = vmTranslator.fromDiscrete(apex);

      if (!entity.has(PlayerInput.class))
        node.relocate(relocatePoint.getX(), relocatePoint.getY());
    });
  }
}
