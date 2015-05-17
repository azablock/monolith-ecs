package monolith.cbs.subsystem;

import monolith.cbs.component.movement.MovementPath;
import monolith.cbs.component.position.ApexPosition;
import monolith.cbs.entity.MoMetaEntity;
import monolith.cbs.subsystem.tools.vm_translator.VMTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static monolith.cbs.entity.MoEntityManager.ENTITY_MANAGER;
import static monolith.cbs.entity.MoMetaEntity.loadFromGameWorld;

@Component
public class MovementSystem implements MoSubSystem {

  @Autowired
  private VMTranslator vmTranslator;

  @Override
  public void processOneGameTick() {

    ENTITY_MANAGER.getAllEntitiesPossessingComponent(MovementPath.class).forEach(uuid -> {

      MoMetaEntity entity = loadFromGameWorld(uuid);
      MovementPath path = entity.get(MovementPath.class);

      if (path.getPath().isEmpty())
        entity.remove(path);

      else {
        entity.get(ApexPosition.class).setApex(path.getNextStep());
        vmTranslator.setCurrentActivePosition(entity.get(ApexPosition.class).getApex());
      }
    });
  }
}
