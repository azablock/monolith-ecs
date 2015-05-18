package monolith.cbs.entity;

import monolith.error.MoDataException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class MoEntityBuilderManager {

  @Autowired
  private PlayerActorBuilder playerActorBuilder;

  @Autowired
  private CrateBuilder crateBuilder;

  @Autowired
  private LevelSectorBuilder levelSectorBuilder;

  private Map<Class<? extends MoEntityBuilder>, MoEntityBuilder> builders;

  @PostConstruct
  public void initialize() {

    builders = new HashMap<>();
    builders.put(PlayerActorBuilder.class, playerActorBuilder);
    builders.put(CrateBuilder.class, crateBuilder);
    builders.put(LevelSectorBuilder.class, levelSectorBuilder);
  }

  @NotNull
  public <T extends MoEntityBuilder> T builderFor(@NotNull Class<T> builderType) {

    if (!builders.containsKey(builderType))
      throw new MoDataException("There is no builder in collection of given type: " + builderType);

    return (T) builders.get(builderType);
  }
}
