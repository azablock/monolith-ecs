package monolith.cbs.component.position;

import org.jetbrains.annotations.NotNull;

public class EdgePosition implements Position {

  @NotNull
  private final Edge edge;

  public EdgePosition(@NotNull final Edge edge) {

    this.edge = edge;
  }

  @NotNull
  public Edge getEdge() {

    return edge;
  }

  @Override
  public String toString() {

    return String.format("EdgePosition{edge=%s}", edge);
  }
}
