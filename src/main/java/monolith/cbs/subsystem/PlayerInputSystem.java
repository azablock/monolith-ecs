package monolith.cbs.subsystem;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import monolith.cbs.component.bounding_box.BoundingBox;
import monolith.cbs.component.graphics.Graphics;
import monolith.cbs.component.pathfinding.PathfindingRequest;
import monolith.cbs.component.player_input.PlayerInput;
import monolith.cbs.component.position.Apex;
import monolith.cbs.component.position.ApexPosition;
import monolith.cbs.entity.MoMetaEntity;
import monolith.cbs.subsystem.tools.vm_translator.VMTranslator;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

import static monolith.cbs.component.position.DiscretePositionUtils.translated;
import static monolith.cbs.entity.MoEntityManager.ENTITY_MANAGER;
import static monolith.cbs.entity.MoMetaEntity.loadFromGameWorld;

@Component
public class PlayerInputSystem implements MoSubSystem, EventHandler<MouseEvent> {

  @Autowired
  private VMTranslator vmTranslator;

  @NotNull
  private final LinkedList<MouseEvent> events;

  private static final Logger LOG = LoggerFactory.getLogger(PlayerInputSystem.class);

  public PlayerInputSystem() {

    events = new LinkedList<>();
  }

  @Override
  public void processOneGameTick() {

    Set<UUID> allLevelSectors = ENTITY_MANAGER.getAllEntitiesPossessingComponents(BoundingBox.class, Graphics.class);
    Set<UUID> allWithPlayerInput = ENTITY_MANAGER.getAllEntitiesPossessingComponent(PlayerInput.class);

    if (!events.isEmpty()) {
      MouseEvent event = events.getFirst();
      Point2D mouseActionPoint = new Point2D(event.getX(), event.getY());
//
//      ENTITY_MANAGER.getAllEntitiesPossessingComponent(IsLevelSector.class).forEach(uuid -> {
//
//        if (loadFromGameWorld(uuid).get(Graphics.class).node.contains(mouseActionPoint))
//
//          allWithPlayerInput
//              .forEach(playerActorEntity -> StackPane.setAlignment(loadFromGameWorld(playerActorEntity).get(Graphics.class).node, Pos.CENTER));
//      });

      allWithPlayerInput.forEach(uuid -> {
        MoMetaEntity withPlayerInput = loadFromGameWorld(uuid);
        Node node = withPlayerInput.get(Graphics.class).node;
        Point2D nodeCurrentPos = new Point2D(node.getLayoutX(), node.getLayoutY());
        Apex magnitude = vmTranslator.vectorMagnitudeBetween(mouseActionPoint, nodeCurrentPos);

        withPlayerInput.add(new PathfindingRequest(translated(withPlayerInput.get(ApexPosition.class).getApex(),
                                                              magnitude.getX(),
                                                              magnitude.getY())));

        LOG.debug(String.valueOf(node.getLayoutX()) + " " + String.valueOf(node.getLayoutY()));
        LOG.debug(String.valueOf(mouseActionPoint));
        LOG.debug(String.valueOf(magnitude));

        vmTranslator.setCurrentActivePosition(magnitude);
      });

      events.removeFirst();
    }
  }

  @Override
  public void handle(MouseEvent event) {

    events.addLast(event);
  }

  public int eventsCount() {

    return events.size();
  }
}

