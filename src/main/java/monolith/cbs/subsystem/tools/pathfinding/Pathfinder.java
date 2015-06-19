package monolith.cbs.subsystem.tools.pathfinding;

import monolith.cbs.component.position.Apex;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public interface Pathfinder {

  @NotNull
  LinkedList<Apex> path(@NotNull final Apex start, @NotNull final Apex destination);
}
