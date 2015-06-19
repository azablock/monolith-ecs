package monolith.cbs.component.pathfinding;

import monolith.cbs.component.MoComponent;
import monolith.cbs.component.position.Apex;
import org.jetbrains.annotations.NotNull;

public class PathfindingRequest implements MoComponent {

  @NotNull
  private final Apex destination;

  public PathfindingRequest(@NotNull Apex destination) {

    this.destination = destination;
  }

  @NotNull
  public Apex getDestination() {

    return destination;
  }

  @Override
  public String toString() {

    return String.format("MoveCommand{destination=%s}", destination);
  }
}
