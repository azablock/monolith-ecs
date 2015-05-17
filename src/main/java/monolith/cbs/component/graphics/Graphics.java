package monolith.cbs.component.graphics;

import javafx.scene.Group;
import javafx.scene.Node;
import monolith.cbs.component.MoComponent;
import org.jetbrains.annotations.NotNull;

public class Graphics implements MoComponent {

  @NotNull
  public final Node node;

  public Graphics(@NotNull final Node node) {

    this.node = node;
  }

  @Override
  public String toString() {

    return String.format("Graphics{node=%s}", node);
  }
}
