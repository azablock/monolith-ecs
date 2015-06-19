package monolith.cbs.subsystem;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import monolith.cbs.component.bounding_box.BoundingBox;
import monolith.cbs.component.graphics.Graphics;
import monolith.cbs.component.graphics.GraphicsConstants;
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

import static monolith.cbs.component.graphics.GraphicsConstants.*;
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

      allWithPlayerInput.forEach(uuid -> {
        MoMetaEntity withPlayerInput = loadFromGameWorld(uuid);
        Node node = withPlayerInput.get(Graphics.class).node;
        Point2D nodeCurrentPos = new Point2D(node.getLayoutX(), node.getLayoutY());
        Apex magnitude = vmTranslator.vectorMagnitudeBetween(mouseActionPoint, nodeCurrentPos);

        withPlayerInput.add(new PathfindingRequest(translated(withPlayerInput.get(ApexPosition.class).getApex(),
                                                              magnitude.getX(),
                                                              magnitude.getY())));


        Point2D deltaPoint = new Point2D(mouseActionPoint.getX() - node.getLayoutX(), mouseActionPoint.getY() - node.getLayoutY());

        LOG.debug("distance between mouseAction and playerActorGraphics: " + deltaPoint + " , FIELD_WORLD_SIZE: " + FIELD_WORLD_SIZE);
        LOG.debug("vector magnitude (discrete): " + magnitude);
        LOG.debug("fromDouble (vm): " + vmTranslator.fromDouble(deltaPoint) + "\n");

        vmTranslator.setCurrentActivePosition(magnitude); //to nie tu, tylko w movement czy cos, ale narazie jest tmp
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

