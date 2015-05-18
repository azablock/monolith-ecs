package monolith.cbs.component.bounding_box;

import com.google.common.base.Objects;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import monolith.cbs.component.MoComponent;
import monolith.cbs.component.position.Apex;
import monolith.cbs.component.position.DiscretePositionUtils;
import monolith.error.MoDataException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static java.lang.String.format;
import static monolith.cbs.component.position.DiscretePositionUtils.*;

public class BoundingBox implements MoComponent, Iterable<Apex> {

  @NotNull
  private final Apex topLeft;

  @NotNull
  private final Apex bottomRight;

  private BoundingBox(@NotNull final Apex topLeft,
                      @NotNull final Apex bottomRight) {

    this.topLeft = topLeft;
    this.bottomRight = bottomRight;
  }

  public static BoundingBox newInstance(@NotNull Apex topLeft,
                                        @NotNull Apex bottomRight) {

    if (topLeft.getX() >= bottomRight.getX() || topLeft.getY() >= bottomRight.getY())
      throw new MoDataException(format("illegal coordinates, one or more coefficients of top left " +
                                       "corner's coordinates is greater than respective bottom " +
                                       "right corner's coefficient\ntop left: %s\nbottom right: %s",
                                       topLeft, bottomRight));

    return new BoundingBox(topLeft, bottomRight);
  }

  @NotNull
  public Apex getTopLeft() {

    return topLeft;
  }

  @NotNull
  public Apex getBottomRight() {

    return bottomRight;
  }

  @NotNull
  public Integer getWidth() {

    return bottomRight.getX() - topLeft.getX();
  }

  @NotNull
  public Integer getHeight() {

    return bottomRight.getY() - topLeft.getY();
  }

  public boolean contains(@NotNull final Apex coordinates) {

    return horizontalRange().contains(coordinates.getX()) &&
           verticalRange().contains(coordinates.getY());
  }

  public boolean contains(@NotNull final BoundingBox boundingBox) {

    return contains(boundingBox.getTopLeft()) && contains(boundingBox.getBottomRight());
  }

  public boolean intersects(@NotNull final BoundingBox boundingBox) {

    return horizontalRange().isConnected(boundingBox.horizontalRange()) &&
           verticalRange().isConnected(boundingBox.verticalRange());
  }

  private Range<Integer> horizontalRange() {

    return Range.closed(topLeft.getX(), bottomRight.getX() - 1);
  }

  private Range<Integer> verticalRange() {

    return Range.closed(topLeft.getY(), bottomRight.getY() - 1);
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BoundingBox that = (BoundingBox) o;

    return Objects.equal(topLeft, that.topLeft) &&
           Objects.equal(bottomRight, that.bottomRight);
  }

  @Override
  public int hashCode() {

    return Objects.hashCode(topLeft, bottomRight);
  }

  @NotNull
  public Collection<Apex> containedApexPosition() {

    Iterable<Integer> horizontal = ContiguousSet.create(horizontalRange(), DiscreteDomain.integers());
    Iterable<Integer> vertical = ContiguousSet.create(verticalRange(), DiscreteDomain.integers());

    List<Apex> containedPosition = new ArrayList<>();

    for (Integer x : horizontal)
      for (Integer y : vertical)
        containedPosition.add(new Apex(x, y));

    return containedPosition;
  }

  @Override
  public String toString() {

    return format("BoundingBox{topLeft=%s, bottomRight=%s}", topLeft, bottomRight);
  }

  @Override
  public Iterator<Apex> iterator() {

    List<Apex> apexes = new ArrayList<>();

    for (int y = topLeft.getY(); y < bottomRight.getY(); y++)
      for (int x = topLeft.getX(); x < bottomRight.getX(); x++)
        apexes.add(pos(x, y));

    return apexes.iterator();
  }
}
