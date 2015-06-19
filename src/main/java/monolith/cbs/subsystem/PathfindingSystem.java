package monolith.cbs.subsystem;

import monolith.cbs.component.MoComponentFactoryManager;
import monolith.cbs.component.movement.MovementPath;
import monolith.cbs.component.pathfinding.PathfindingRequest;
import monolith.cbs.component.position.ApexPosition;
import monolith.cbs.entity.MoMetaEntity;
import monolith.cbs.subsystem.tools.pathfinding.Pathfinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static monolith.cbs.entity.MoEntityManager.ENTITY_MANAGER;
import static monolith.cbs.entity.MoMetaEntity.loadFromGameWorld;

@Component
public class PathfindingSystem implements MoSubSystem {

  @Autowired
  private Pathfinder pathfinder;

  @Autowired
  private MoComponentFactoryManager factoryManager;

  @Override
  public void processOneGameTick() {

    ENTITY_MANAGER.getAllEntitiesPossessingComponent(PathfindingRequest.class).forEach(uuid -> {

      MoMetaEntity entity = loadFromGameWorld(uuid);
      entity.add(new MovementPath(pathfinder.path(entity.get(ApexPosition.class).getApex(),
                                                  entity.get(PathfindingRequest.class).getDestination())));
      entity.remove(PathfindingRequest.class);
    });
  }
}
