package monolith.cbs.entity;

import monolith.cbs.component.MoComponentFactoryManager;
import monolith.cbs.component.graphics.GraphicsAdditionRequest;
import monolith.cbs.component.graphics.PlayerActorGraphicsFactory;
import monolith.cbs.component.player_input.PlayerInput;
import monolith.cbs.component.position.Apex;
import monolith.cbs.component.position.ApexPosition;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PlayerActorBuilder implements MoEntityBuilder {

  private static final Logger LOG = LoggerFactory.getLogger(PlayerActorBuilder.class);

  @Autowired
  private MoComponentFactoryManager factoryManager;

  @NotNull
  private Apex apex;

  @PostConstruct
  private void init() {

    apex = new Apex(0, 0);
  }

  public PlayerActorBuilder apex(@NotNull final Apex apex) {

    this.apex = apex;
    return this;
  }

  @NotNull
  @Override
  public MoMetaEntity build() {

    MoMetaEntity playerActor = new MoMetaEntity("PlayerActor");

    playerActor.add(new PlayerInput());
    playerActor.add(new ApexPosition(apex));
    playerActor.add(new GraphicsAdditionRequest());
    playerActor.add(factoryManager.factoryOfType(PlayerActorGraphicsFactory.class).newGraphics());

    return playerActor;
  }
}
