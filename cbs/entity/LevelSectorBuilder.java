package monolith.cbs.entity;

import monolith.cbs.component.MoComponentFactoryManager;
import monolith.cbs.component.bounding_box.BoundingBox;
import monolith.cbs.component.graphics.LevelSectorGraphicsFactory;
import monolith.cbs.component.graphics_addition_request.GraphicsAdditionRequest;
import monolith.cbs.component.level_sector.IsLevelSector;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static monolith.cbs.component.position.DiscretePositionUtils.pos;

@Component
public class LevelSectorBuilder implements MoEntityBuilder {

  @Autowired
  private MoComponentFactoryManager factoryManager;

  private BoundingBox boundingBox;

  @PostConstruct
  private void init() {

    boundingBox = BoundingBox.newInstance(pos(1, 1), pos(10, 5));
  }

  public LevelSectorBuilder boundingBox(BoundingBox boundingBox) {

    this.boundingBox = boundingBox;
    return this;
  }

  @NotNull
  @Override
  public MoMetaEntity build() {

    MoMetaEntity levelSector = new MoMetaEntity("LevelSector");

    levelSector.add(boundingBox);
    levelSector.add(factoryManager.factoryOfType(LevelSectorGraphicsFactory.class).newGraphics());
    levelSector.add(new GraphicsAdditionRequest());
    levelSector.add(new IsLevelSector());

    return levelSector;
  }
}
