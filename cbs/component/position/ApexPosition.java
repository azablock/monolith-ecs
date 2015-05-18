package monolith.cbs.component.position;

import org.jetbrains.annotations.NotNull;

public class ApexPosition implements Position {

  @NotNull
  private Apex apex;

  public ApexPosition(@NotNull Apex apex) {

    this.apex = apex;
  }

  public Integer getX() {

    return apex.getX();
  }

  public Integer getY() {

    return apex.getY();
  }

  @NotNull
  public Apex getApex() {

    return apex;
  }

  public void setApex(@NotNull Apex apex) {

    this.apex = apex;
  }

  @Override
  public String toString() {

    return String.format("ApexPosition{%s}", apex);
  }
}
