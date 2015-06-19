package monolith.cbs.component.graphics;

import javafx.scene.Node;
import monolith.cbs.component.MoComponentFactory;
import org.jetbrains.annotations.NotNull;

public interface GraphicsFactory extends MoComponentFactory<Graphics> {

  @NotNull
  Graphics newGraphics();
}

