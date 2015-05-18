package monolith.cbs.entity;

import monolith.cbs.component.MoComponentFactoryManager;
import monolith.cbs.component.graphics.CrateGraphicsFactory;
import monolith.cbs.component.graphics_addition_request.GraphicsAdditionRequest;
import monolith.cbs.component.position.Apex;
import monolith.cbs.component.position.ApexPositionFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CrateBuilder implements MoEntityBuilder {

  @Autowired
  private MoComponentFactoryManager factoryManager;

  @NotNull
  private Apex apex;

  @PostConstruct
  private void init() {

    apex = new Apex(0, 0);
  }

  public CrateBuilder apex(@NotNull final Apex apex) {

    this.apex = apex;
    return this;
  }

  @NotNull
  @Override
  public MoMetaEntity build() {

    MoMetaEntity crate = new MoMetaEntity("Crate");

    crate.add(factoryManager.factoryOfType(ApexPositionFactory.class).newApexPosition(apex));
    crate.add(factoryManager.factoryOfType(CrateGraphicsFactory.class).newGraphics());
    crate.add(new GraphicsAdditionRequest());

    return crate;
  }
}
