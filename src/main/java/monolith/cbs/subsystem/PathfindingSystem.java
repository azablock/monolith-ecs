package monolith.cbs.subsystem;

import monolith.cbs.component.MoComponentFactoryManager;
import monolith.cbs.subsystem.tools.pathfinding.MockPathfinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PathfindingSystem implements MoSubSystem {

  @Autowired
  private MockPathfinder pathfinder;

  @Autowired
  private MoComponentFactoryManager factoryManager;

  @Override
  public void processOneGameTick() {

//    ENTITY_MANAGER.getAllEntitiesPossessingComponent(MoveCommand.class).forEach(uuid -> {
//
//      MoMetaEntity entity = loadFromGameWorld(uuid);
//      Apex start = entity.get(ApexPosition.class).getApex();
//      Apex destination = entity.get(MoveCommand.class).getDestination();
//      entity.add(factoryManager.factoryOfType(MovementPathFactory.class).newMovementPath(start, destination));
//      entity.remove(entity.get(MoveCommand.class));
//    });
  }
}
