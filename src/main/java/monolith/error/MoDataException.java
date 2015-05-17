package monolith.error;

import org.jetbrains.annotations.NotNull;

public class MoDataException extends RuntimeException {


  public MoDataException(@NotNull String message) {

    super(message);
  }
}
