package monolith;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import monolith.cbs.subsystem.MoSubSystem;
import monolith.cbs.subsystem.PlayerInputSystem;
import monolith.cbs.subsystem.SubSystemManager;
import monolith.cbs.subsystem.tools.vm_translator.VMTranslator;
import monolith.ui.InGamePaneController;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static javafx.animation.Animation.INDEFINITE;
import static monolith.cbs.entity.MoEntityManager.ENTITY_MANAGER;
import static monolith.cbs.subsystem.tools.fps.FPSConstants.RELEASE_FPS;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class GameLoop {

  @Autowired
  private SubSystemManager subSystemManager;

  @Autowired
  private InGamePaneController inGamePaneController;

  @Autowired
  private VMTranslator vmTranslator;

  @NotNull
  private final Timeline gameLoopTimeline;

  private static final Logger LOG = getLogger(GameLoop.class);

  public GameLoop() {

    gameLoopTimeline = new Timeline();
    gameLoopTimeline.setCycleCount(INDEFINITE);

    gameLoopTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(RELEASE_FPS), event -> {

      subSystemManager.forEach(MoSubSystem::processOneGameTick);
      inGamePaneController.getDebugLabel().setText("entities: " + ENTITY_MANAGER.entityCount() + "\n" +
                                                   "components: " + ENTITY_MANAGER.componentCount() + "\n" + "mouseEvents: " +
                                                   subSystemManager.getSubSystem(PlayerInputSystem.class).eventsCount() + "\n" + "vm: " +
                                                   vmTranslator.getCurrentActivePosition());
    }));
  }

  public void start() {

    gameLoopTimeline.play();
  }

  public void stop() {

    gameLoopTimeline.stop();
  }
}