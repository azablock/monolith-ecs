package monolith.cbs.entity;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import monolith.cbs.component.MoComponentFactoryManager;
import monolith.cbs.component.bounding_box.BoundingBox;
import monolith.cbs.component.graphics.Graphics;
import monolith.cbs.component.graphics.GraphicsAdditionRequest;
import monolith.cbs.component.graphics.LevelSectorGraphicsFactory;
import monolith.cbs.component.level_sector.IsLevelSector;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static monolith.cbs.component.graphics.GraphicsConstants.FIELD_WORLD_SIZE;
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
    levelSector.add(new IsLevelSector());
    levelSector.add(new GraphicsAdditionRequest());

    Group levelSectorGroup = (Group) levelSector.get(Graphics.class).node;

    Rectangle bbRect = new Rectangle(boundingBox.getTopLeft().getX() * FIELD_WORLD_SIZE,
                                     boundingBox.getTopLeft().getY() * FIELD_WORLD_SIZE,
                                     boundingBox.getWidth() * FIELD_WORLD_SIZE,
                                     boundingBox.getHeight() * FIELD_WORLD_SIZE);
    bbRect.setFill(Color.CORAL);
    levelSectorGroup.getChildren().add(bbRect);

    levelSectorGroup.toBack();

    return levelSector;
  }
}
