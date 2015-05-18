package monolith.cbs.entity;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface MoEntityBuilder {

  @NotNull
  MoMetaEntity build();
}
