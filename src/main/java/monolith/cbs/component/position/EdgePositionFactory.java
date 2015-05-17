package monolith.cbs.component.position;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import static monolith.cbs.component.position.EdgePosition.newInstance;

@Component
public class EdgePositionFactory implements PositionFactory {

  @NotNull
  Position newEdgePosition(@NotNull Apex first, @NotNull Apex second) {

    return newInstance(first, second);
  }
}
