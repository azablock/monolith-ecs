package monolith.cbs.component.move_command;

import monolith.cbs.component.MoComponent;
import monolith.cbs.component.position.Apex;
import org.jetbrains.annotations.NotNull;

public class MoveCommand implements MoComponent {

  @NotNull
  private final Apex destination;

  public MoveCommand(@NotNull Apex destination) {

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
