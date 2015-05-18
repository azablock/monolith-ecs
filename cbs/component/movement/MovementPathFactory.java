package monolith.cbs.component.movement;

import monolith.cbs.component.MoComponentFactory;
import monolith.cbs.component.position.Apex;
import org.jetbrains.annotations.NotNull;

public interface MovementPathFactory extends MoComponentFactory<MovementPath> {

  @NotNull
  MovementPath newMovementPath(@NotNull final Apex start, @NotNull final Apex destination);
}
