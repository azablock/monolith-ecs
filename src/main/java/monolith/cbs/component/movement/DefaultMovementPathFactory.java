package monolith.cbs.component.movement;

import monolith.cbs.component.position.Apex;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class DefaultMovementPathFactory implements MovementPathFactory {

  @NotNull
  @Override
  public MovementPath newMovementPath(@NotNull final Apex start, @NotNull final Apex destination) {

    LinkedList<Apex> path = new LinkedList<>();
    path.addLast(destination);

    return new MovementPath(path);
  }
}
