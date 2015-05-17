package monolith.cbs.subsystem;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import monolith.cbs.component.bounding_box.BoundingBox;
import monolith.cbs.component.graphics.Graphics;
import monolith.cbs.component.level_sector.IsLevelSector;
import monolith.cbs.component.move_command.MoveCommand;
import monolith.cbs.component.player_input.PlayerInput;
import monolith.cbs.subsystem.tools.vm_translator.VMTranslator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

import static monolith.cbs.entity.MoEntityManager.ENTITY_MANAGER;
import static monolith.cbs.entity.MoMetaEntity.loadFromGameWorld;

@Component
public class PlayerInputSystem implements MoSubSystem, EventHandler<MouseEvent> {

  @Autowired
  private VMTranslator vmTranslator;

  @NotNull
  private final LinkedList<MouseEvent> events;

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

      ENTITY_MANAGER.getAllEntitiesPossessingComponent(IsLevelSector.class).forEach(uuid -> {

        if (loadFromGameWorld(uuid).get(Graphics.class).node.contains(mouseActionPoint))

          allWithPlayerInput.forEach(playerActorEntity -> loadFromGameWorld(playerActorEntity)
              .add(new MoveCommand(vmTranslator.fromDouble(mouseActionPoint))));

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

