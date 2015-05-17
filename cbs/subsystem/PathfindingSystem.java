package monolith.cbs.subsystem;

import monolith.cbs.component.MoComponentFactoryManager;
import monolith.cbs.component.move_command.MoveCommand;
import monolith.cbs.component.movement.MovementPathFactory;
import monolith.cbs.component.position.Apex;
import monolith.cbs.component.position.ApexPosition;
import monolith.cbs.entity.MoMetaEntity;
import monolith.cbs.subsystem.tools.pathfinding.MockPathfinder;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static monolith.cbs.entity.MoEntityManager.ENTITY_MANAGER;
import static monolith.cbs.entity.MoMetaEntity.loadFromGameWorld;

@Component
public class PathfindingSystem implements MoSubSystem {

  @Autowired
  private MockPathfinder pathfinder;

  @Autowired
  private MoComponentFactoryManager factoryManager;

  @Override
  public void processOneGameTick() {

    ENTITY_MANAGER.getAllEntitiesPossessingComponent(MoveCommand.class).forEach(uuid -> {

      MoMetaEntity entity = loadFromGameWorld(uuid);
      Apex start = entity.get(ApexPosition.class).getApex();
      Apex destination = entity.get(MoveCommand.class).getDestination();
      entity.add(factoryManager.factoryOfType(MovementPathFactory.class).newMovementPath(start, destination));
      entity.remove(entity.get(MoveCommand.class));
    });
  }
}
