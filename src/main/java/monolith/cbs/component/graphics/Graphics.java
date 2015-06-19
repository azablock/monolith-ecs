package monolith.cbs.component.graphics;

import javafx.scene.Node;
import monolith.cbs.component.MoComponent;
import org.jetbrains.annotations.NotNull;

public class Graphics<T extends Node> implements MoComponent {

  @NotNull
  public final T node;

  public Graphics(@NotNull final T node) {

    this.node = node;
  }

  @Override
  public String toString() {

    return String.format("Graphics{node=%s}", node);
  }
}
