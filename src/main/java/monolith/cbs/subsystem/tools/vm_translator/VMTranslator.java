package monolith.cbs.subsystem.tools.vm_translator;

import javafx.geometry.Point2D;
import monolith.cbs.component.position.Apex;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static java.lang.Math.*;
import static monolith.cbs.component.graphics.GraphicsConstants.FIELD_WORLD_SIZE;
import static monolith.cbs.component.position.DiscretePositionUtils.pos;
import static monolith.cbs.component.position.DiscretePositionUtils.translated;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class VMTranslator {

  @NotNull
  private Apex currentActivePosition;

  private static final Logger LOG = getLogger(VMTranslator.class);

  public VMTranslator() {

    currentActivePosition = pos(0, 0);
  }

  public Apex fromDouble(@NotNull final Point2D point2D) {

    int discreteX = (int) ((point2D.getX() / FIELD_WORLD_SIZE)) + currentActivePosition.getX();
    int discreteY = (int) ((point2D.getY() / FIELD_WORLD_SIZE)) + currentActivePosition.getY();

    return new Apex(discreteX, discreteY);
  }

  public Apex fromDouble(double x, double y) {

    int discreteX = (int) ((x / FIELD_WORLD_SIZE)) + currentActivePosition.getX();
    int discreteY = (int) ((y / FIELD_WORLD_SIZE)) + currentActivePosition.getY();

    return new Apex(discreteX, discreteY);
  }

  public Point2D fromDiscrete(@NotNull final Apex apex) {

    return new Point2D((apex.getX() - currentActivePosition.getX()) * FIELD_WORLD_SIZE,
                       (apex.getY() - currentActivePosition.getY()) * FIELD_WORLD_SIZE);
  }

  public Point2D fromDiscrete(int x, int y) {

    return new Point2D((x - currentActivePosition.getX()) * FIELD_WORLD_SIZE,
                       (y - currentActivePosition.getY()) * FIELD_WORLD_SIZE);
  }

  public Apex vectorMagnitudeBetween(@NotNull final Point2D start, @NotNull final Point2D destination) {

    double deltaX = start.getX() - destination.getX();
    double deltaY = start.getY() - destination.getY();

    int magnitudeX = (int) floor(deltaX / FIELD_WORLD_SIZE);
    int magnitudeY = (int) floor(deltaY / FIELD_WORLD_SIZE);

    return new Apex(magnitudeX, magnitudeY);
  }

  public void setCurrentActivePosition(@NotNull final Apex deltaMagnitude) {

    this.currentActivePosition = translated(this.currentActivePosition, deltaMagnitude.getX(), deltaMagnitude.getY());
  }

  @NotNull
  public Apex getCurrentActivePosition() {

    return currentActivePosition;
  }

  @Override
  public String toString() {

    return String.format("VMTranslator{currentActivePosition=%s}", currentActivePosition);
  }
}
