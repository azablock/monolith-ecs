package monolith.cbs.component.graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static monolith.cbs.component.graphics.GraphicsConstants.FIELD_WORLD_SIZE;

@Component
public class CrateGraphicsFactory implements GraphicsFactory {

  @Value("monolith/images/crate1.png")
  private String spritePath;

  @NotNull
  @Override
  public Graphics newGraphics() {

    ImageView sprite = new ImageView(new Image(spritePath));
    sprite.setFitWidth(FIELD_WORLD_SIZE);
    sprite.setFitHeight(FIELD_WORLD_SIZE);
    sprite.setPreserveRatio(true);

    return new Graphics<>(sprite);
  }
}
