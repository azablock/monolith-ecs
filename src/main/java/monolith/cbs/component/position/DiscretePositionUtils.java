package monolith.cbs.component.position;

import org.jetbrains.annotations.NotNull;

import static monolith.cbs.component.position.Edge.*;
import static monolith.cbs.component.position.EdgePosition.*;

public class DiscretePositionUtils {

  /**
   * Utility classes (containing static methods only) should not be instantiated
   */
  private DiscretePositionUtils() {

  }

  @NotNull
  public static Apex pos(@NotNull Integer x, @NotNull Integer y) {

    return new Apex(x, y);
  }

  @NotNull
  public static Apex translated(Apex coordinates, @NotNull Integer u,
                                                 @NotNull Integer v) {

    return pos(coordinates.getX() + u, coordinates.getY() + v);
  }

  public static Apex north(Apex coordinates) {

    return pos(coordinates.getX(), coordinates.getY() - 1);
  }

  public static Apex northEast(Apex coordinates) {

    return pos(coordinates.getX() + 1, coordinates.getY() - 1);
  }

  public static Apex east(Apex coordinates) {

    return pos(coordinates.getX() + 1, coordinates.getY());
  }

  public static Apex southEast(Apex coordinates) {

    return pos(coordinates.getX() + 1, coordinates.getY() + 1);
  }

  public static Apex south(Apex coordinates) {

    return pos(coordinates.getX(), coordinates.getY() + 1);
  }

  public static Apex southWest(Apex coordinates) {

    return pos(coordinates.getX() - 1, coordinates.getY() + 1);
  }

  public static Apex west(Apex coordinates) {

    return pos(coordinates.getX() - 1, coordinates.getY());
  }

  public static Apex northWest(Apex coordinates) {

    return pos(coordinates.getX() - 1, coordinates.getY() - 1);
  }

  public static Edge withNorthOf(Apex coordinates) {

    return newInstance(coordinates, north(coordinates));
  }

  public static Edge withNorthEastOf(Apex coordinates) {

    return newInstance(coordinates, northEast(coordinates));
  }

  public static Edge withEastOf(Apex coordinates) {

    return newInstance(coordinates, east(coordinates));
  }

  public static Edge withSouthEastOf(Apex coordinates) {

    return newInstance(coordinates, southEast(coordinates));
  }

  public static Edge withSouthOf(Apex coordinates) {

    return newInstance(coordinates, south(coordinates));
  }

  public static Edge withSouthWestOf(Apex coordinates) {

    return newInstance(coordinates, southWest(coordinates));
  }

  public static Edge withWestOf(Apex coordinates) {

    return newInstance(coordinates, west(coordinates));
  }

  public static Edge withNorthWestOf(Apex coordinates) {

    return newInstance(coordinates, northWest(coordinates));
  }
}
