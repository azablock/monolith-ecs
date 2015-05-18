package monolith.cbs.component.position;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ApexPositionFactory implements PositionFactory {

  @NotNull
  public Position newApexPosition(@NotNull Integer x, @NotNull Integer y) {

    return new ApexPosition(new Apex(x, y));
  }

  @NotNull
  public Position newApexPosition(Apex apex) {

    return new ApexPosition(new Apex(apex.getX(), apex.getY()));
  }

}
