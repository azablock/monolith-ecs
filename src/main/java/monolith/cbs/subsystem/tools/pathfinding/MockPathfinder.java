package monolith.cbs.subsystem.tools.pathfinding;

import monolith.cbs.component.position.Apex;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class MockPathfinder implements Pathfinder {

  @NotNull
  @Override
  public LinkedList<Apex> path(@NotNull Apex start, @NotNull Apex destination) {

    LinkedList<Apex> path = new LinkedList<>();
    path.addLast(destination);

    return path;
  }
}
