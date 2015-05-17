package monolith.cbs.component.graphics;

import javafx.scene.Group;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class LevelSectorGraphicsFactory implements GraphicsFactory {

  @NotNull
  @Override
  public Graphics newGraphics() {

    Group group = new Group();
    group.toBack();

    return new Graphics(group);
  }
}
