package monolith.cbs.subsystem;

import monolith.cbs.component.movement.MovementPath;
import monolith.cbs.component.position.Apex;
import monolith.cbs.component.position.ApexPosition;
import monolith.cbs.entity.MoEntityManager;
import monolith.cbs.entity.MoMetaEntity;
import monolith.cbs.subsystem.tools.vm_translator.VMTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Consumer;

import static monolith.cbs.entity.MoEntityManager.*;
import static monolith.cbs.entity.MoMetaEntity.*;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class MovementSystem implements MoSubSystem {

  @Autowired
  private VMTranslator vmTranslator;

  private static final Logger LOG = getLogger(MovementSystem.class);

  @Override
  public void processOneGameTick() {

    ENTITY_MANAGER.getAllEntitiesPossessingComponent(MovementPath.class).forEach(uuid -> {

      MoMetaEntity entity = loadFromGameWorld(uuid);

      if(entity.get(MovementPath.class).getPath().isEmpty())
        entity.remove(MovementPath.class);

      else {
        Apex currentPos = entity.get(ApexPosition.class).getApex();


        LOG.debug(entity.getInternalName() + "'s movementPath: " + String.valueOf(entity.get(MovementPath.class).getPath()));



        Apex nextStep = entity.get(MovementPath.class).getNextStep();
        entity.get(ApexPosition.class).setApex(nextStep);




        vmTranslator.setCurrentActivePosition(new Apex(nextStep.getX() - currentPos.getX(),
                                                       nextStep.getY() - currentPos.getY()));
      }
    });
  }
}
