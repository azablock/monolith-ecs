package monolith.cbs.component.graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static javafx.scene.paint.Color.BLUE;
import static monolith.cbs.component.graphics.GraphicsConstants.*;
import static monolith.cbs.component.graphics.GraphicsConstants.FIELD_WORLD_SIZE;

@Component
public class PlayerActorGraphicsFactory implements GraphicsFactory {

  @Value("monolith/images/playerActor1.png")
  private String spritePath;

  @NotNull
  @Override
  public Graphics<ImageView> newGraphics() {

    ImageView sprite = new ImageView(new Image(spritePath));
    sprite.setFitWidth(FIELD_WORLD_SIZE);
    sprite.setFitHeight(FIELD_WORLD_SIZE);
    sprite.setPreserveRatio(true);

    return new Graphics<>(sprite);
  }
}
