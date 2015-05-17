package monolith.cbs.subsystem;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class SubSystemManager implements Iterable<MoSubSystem> {

  @Autowired
  private PlayerInputSystem playerInputSystem;

  @Autowired
  private PathfindingSystem pathfindingSystem;

  @Autowired
  private RenderSystem renderSystem;

  @Autowired
  private MovementSystem movementSystem;

  @Autowired
  private GraphicsManagementSystem graphicsManagementSystem;

  private Map<Class<? extends MoSubSystem>, MoSubSystem> systems;

  @PostConstruct
  public void initialize() {

    systems = new HashMap<>();
    systems.put(PlayerInputSystem.class, playerInputSystem);
    systems.put(PathfindingSystem.class, pathfindingSystem);
    systems.put(RenderSystem.class, renderSystem);
    systems.put(MovementSystem.class, movementSystem);
    systems.put(GraphicsManagementSystem.class, graphicsManagementSystem);
  }

  public <T extends MoSubSystem> T getSubSystem(@NotNull Class<T> systemType) {

    return (T) systems.get(systemType);
  }

  @Override
  public Iterator<MoSubSystem> iterator() {

    return systems.values().iterator();
  }
}
