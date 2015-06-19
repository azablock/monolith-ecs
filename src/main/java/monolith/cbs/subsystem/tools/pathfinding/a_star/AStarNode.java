package monolith.cbs.subsystem.tools.pathfinding.a_star;

import monolith.cbs.component.position.Apex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.lang.String.format;

public class AStarNode implements Comparable<AStarNode> {

  @NotNull
  private final Integer heuristicValue;

  @NotNull
  private Integer movementCost;

  @Nullable
  private Apex parentPosition;

  @NotNull
  private final Apex nodePosition;

  public AStarNode(@NotNull Integer heuristicValue, @NotNull final Apex nodePosition, @Nullable Apex parentPosition) {

    this.heuristicValue = heuristicValue;
    this.parentPosition = parentPosition;
    this.nodePosition = nodePosition;
    movementCost = 0;
  }

  public void reparent(@Nullable final Apex parentPosition) {

    this.parentPosition = parentPosition;
  }

  @NotNull
  public Integer getMovementCost() {

    return movementCost;
  }

  public void addMovementCost(@NotNull Integer movementCost) {

    this.movementCost += movementCost;
  }

  @Nullable
  public Apex getParentPosition() {

    return parentPosition;
  }

  @NotNull
  public Apex getApexPosition() {

    return nodePosition;
  }

  @NotNull
  public Integer getOverallCost() {

    return movementCost + heuristicValue;
  }

  @Override
  public int compareTo(@NotNull AStarNode o) {

    return this.getOverallCost().compareTo(o.getOverallCost());
  }

  @Override
  public String toString() {

    return format("AStarTile{heuristicValue=%d, movementCost=%d, parentPosition=%s, nodePosition=%s}\n",
                  heuristicValue, movementCost, parentPosition, nodePosition);
  }
}
