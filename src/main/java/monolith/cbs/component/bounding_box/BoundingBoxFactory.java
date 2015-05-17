package monolith.cbs.component.bounding_box;

import monolith.cbs.component.MoComponentFactory;
import monolith.cbs.component.position.Apex;
import org.jetbrains.annotations.NotNull;

import static monolith.cbs.component.bounding_box.BoundingBox.*;

public class BoundingBoxFactory implements MoComponentFactory<BoundingBox> {

  @NotNull
  public BoundingBox newBoundingBox(@NotNull Apex topLeft, @NotNull Apex bottomRight) {

    return newInstance(topLeft, bottomRight);
  }
}
