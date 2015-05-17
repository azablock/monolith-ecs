package monolith.cbs.subsystem;

import monolith.cbs.component.graphics.Graphics;
import monolith.cbs.component.player_input.PlayerInput;
import monolith.cbs.entity.MoMetaEntity;
import monolith.cbs.subsystem.tools.vm_translator.VMTranslator;
import monolith.ui.GameWorldPaneController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static monolith.cbs.entity.MoEntityManager.ENTITY_MANAGER;
import static monolith.cbs.entity.MoMetaEntity.loadFromGameWorld;

@Component
public class RenderSystem implements MoSubSystem {

  @Autowired
  private VMTranslator vmTranslator;

  @Autowired
  private GameWorldPaneController gameWorldPaneController;

  @Override
  public void processOneGameTick() {

    ENTITY_MANAGER.getAllEntitiesPossessingComponent(Graphics.class).forEach(uuid -> {

      MoMetaEntity withGraphics = loadFromGameWorld(uuid);

      if (!withGraphics.has(PlayerInput.class)) //TMP
        vmTranslator.translate(withGraphics.get(Graphics.class).node);
    });
  }
}
