package monolith.cbs.component.position;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import monolith.error.MoDataException;
import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;

public class Edge {

  @NotNull
  private final Apex first;

  @NotNull
  private final Apex second;

  private Edge(@NotNull final Apex first, @NotNull final Apex second) {

    this.first = first;
    this.second = second;
  }

  @NotNull
  public static Edge newInstance(@NotNull final Apex first,
                                 @NotNull final Apex second) {

    Integer xDistance = Math.abs(first.getX() - second.getX());
    Integer yDistance = Math.abs(first.getY() - second.getY());

    if (!Range.closed(0, 1).containsAll(ImmutableList.of(xDistance, yDistance)) || first.equals(second))
      throw new MoDataException(format("illegal pair of coordinates - pair: (first=%s, second=%s) is not adjacent",
                                       first, second));

    return new Edge(first, second);
  }

  @NotNull
  public Apex getFirst() {

    return first;
  }

  @NotNull
  public Apex getSecond() {

    return second;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Edge other = (Edge) o;

    return (first.equals(other.first) && second.equals(other.second)) ||
           (first.equals(other.second) && second.equals(other.first));
  }

  @Override
  public int hashCode() {

    return first.hashCode() + second.hashCode();
  }

  @Override
  public String toString() {

    return format("EdgePosition{first=%s, second=%s}", first, second);
  }
}
