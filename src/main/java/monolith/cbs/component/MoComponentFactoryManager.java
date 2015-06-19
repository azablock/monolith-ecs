package monolith.cbs.component;

import monolith.cbs.component.graphics.CrateGraphicsFactory;
import monolith.cbs.component.graphics.LevelSectorGraphicsFactory;
import monolith.cbs.component.graphics.PlayerActorGraphicsFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class MoComponentFactoryManager {

  @Autowired
  private PlayerActorGraphicsFactory playerActorGraphicsFactory;

  @Autowired
  private CrateGraphicsFactory crateGraphicsFactory;

  @Autowired
  private LevelSectorGraphicsFactory levelSectorGraphicsFactory;

  private Map<Class<? extends MoComponentFactory>, MoComponentFactory> componentFactories;

  @PostConstruct
  public void initialize() {

    componentFactories = new HashMap<>();
    componentFactories.put(PlayerActorGraphicsFactory.class, playerActorGraphicsFactory);
    componentFactories.put(LevelSectorGraphicsFactory.class, levelSectorGraphicsFactory);
    componentFactories.put(CrateGraphicsFactory.class, crateGraphicsFactory);
  }

  @NotNull
  public <T extends MoComponentFactory> T factoryOfType(@NotNull Class<T> factoryType) {

    return (T) componentFactories.get(factoryType);
  }
}
