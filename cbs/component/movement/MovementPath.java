package monolith.cbs.component.movement;

import monolith.cbs.component.MoComponent;
import monolith.cbs.component.position.Apex;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class MovementPath implements MoComponent {

  @NotNull
  private final LinkedList<Apex> path;

  public MovementPath(@NotNull final LinkedList<Apex> path) {

    this.path = path;
  }

  @NotNull
  public Apex getNextStep() {

    Apex nextStep = path.getFirst();
    path.removeFirst();

    return nextStep;
  }

  @NotNull
  public LinkedList<Apex> getPath() {

    return path;
  }

  @Override
  public String toString() {

    return String.format("MovementPath{path=%s}", path);
  }
}
