package monolith.cbs.subsystem;

import monolith.cbs.component.MoComponent;
import org.jetbrains.annotations.NotNull;

public interface MoSubSystem {

  void processOneGameTick();

  @NotNull
  default String getSimpleName() {

    return String.valueOf(this.getClass());
  }
}
