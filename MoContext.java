package monolith;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import static monolith.MoUIState.MENU;

@Component
public class MoContext {

  @NotNull
  private final ObjectProperty<MoUIState> state;

  @NotNull
  private final Point2D mvTranslationVector;

  public MoContext() {

    state = new SimpleObjectProperty<>(MENU);
    mvTranslationVector = Point2D.ZERO;
  }

  public ObjectProperty<MoUIState> stateProperty() {

    return state;
  }

  @NotNull
  public Point2D getMvTranslationVector() {

    return mvTranslationVector;
  }
}
