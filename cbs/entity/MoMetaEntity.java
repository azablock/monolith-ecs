package monolith.cbs.entity;

import monolith.cbs.component.MoComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

import static monolith.cbs.entity.MoEntityManager.*;

public class MoMetaEntity {

  private final UUID entity;

  private String internalName = null;

  private MoMetaEntity(UUID entity, String internalName) {

    this.entity = entity;
    this.internalName = internalName;
  }

  @NotNull
  public static MoMetaEntity loadFromGameWorld(@NotNull final UUID entity) {

    return new MoMetaEntity(entity, ENTITY_MANAGER.nameFor(entity));
  }

  public MoMetaEntity() {

    entity = ENTITY_MANAGER.createEntity();
  }

  public MoMetaEntity(@NotNull final String internalName) {

    entity = ENTITY_MANAGER.createEntity(internalName);
  }

  public MoMetaEntity(@NotNull final MoComponent... components) {

    this();

    for (MoComponent component : components)
      this.add(component);
  }

  public MoMetaEntity(@NotNull final String internalName, @NotNull final MoComponent... components) {

    entity = ENTITY_MANAGER.createEntity(internalName);

    for (MoComponent component : components)
      this.add(component);

  }

  public void add(@NotNull final MoComponent component) {

    ENTITY_MANAGER.addComponent(entity, component);
  }

  public void addAll(@NotNull final MoComponent... components) {

    for (MoComponent component : components)
      ENTITY_MANAGER.addComponent(entity, component);
  }

  @NotNull
  public <T extends MoComponent> T get(@NotNull final Class<T> componentType) {

    return ENTITY_MANAGER.getComponent(entity, componentType);
  }

  public <T extends MoComponent> boolean has(@NotNull final Class<T> componentType) {

    return ENTITY_MANAGER.hasComponent(entity, componentType);
  }

  public void remove(@NotNull final MoComponent component) {

    ENTITY_MANAGER.removeComponent(entity, component);
  }

  @NotNull
  public List<? extends MoComponent> getAll() {

    return ENTITY_MANAGER.getAllComponentsOfEntity(entity);
  }

  public void removeAll() {

    getAll().forEach(this::remove);
  }

  public void kill() {

    ENTITY_MANAGER.killEntity(entity);
  }

  public UUID getEntity() {

    return entity;
  }

  public String getInternalName() {

    return internalName;
  }
}
