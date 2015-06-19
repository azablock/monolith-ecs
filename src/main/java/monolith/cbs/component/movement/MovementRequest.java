package monolith.cbs.component.movement;

import monolith.cbs.component.MoComponent;
import monolith.cbs.component.position.Apex;
import org.jetbrains.annotations.NotNull;

public class MovementRequest implements MoComponent {

  @NotNull
  private final Apex movementStep;

  public MovementRequest(@NotNull Apex movementStep) {

    this.movementStep = movementStep;
  }
}
