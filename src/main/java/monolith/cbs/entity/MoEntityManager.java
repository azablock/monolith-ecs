package monolith.cbs.entity;

import monolith.cbs.component.MoComponent;
import monolith.error.MoDataException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.*;

import static com.google.common.collect.Collections2.filter;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * SINGLETON
 */
public class MoEntityManager {

  private static final Logger LOG = getLogger(MoEntityManager.class);

  @NotNull
  private final List<UUID> entities;

  @NotNull
  private final Map<UUID, String> entityHumanReadableNames;

  @NotNull
  private final Map<Class<? extends MoComponent>, HashMap<UUID, ? extends MoComponent>> componentStores;

  public final static MoEntityManager ENTITY_MANAGER = new MoEntityManager();

  private MoEntityManager() {

    entities = new ArrayList<>();
    entityHumanReadableNames = new HashMap<>();
    componentStores = new HashMap<>();
  }

  public <T extends MoComponent> void addComponent(@NotNull final UUID entity, @NotNull final T component) {

    HashMap<UUID, ? extends MoComponent> store = componentStores.get(component.getClass());

    if (store == null) {
      store = new HashMap<>();
      componentStores.put(component.getClass(), store);
    }

    ((HashMap<UUID, T>) store).put(entity, component);
  }

  @NotNull
  public <T extends MoComponent> T getComponent(@NotNull final UUID entity, @NotNull final Class<T> componentType) {

    HashMap<UUID, ? extends MoComponent> store = componentStores.get(componentType);
    if (store == null)
      throw new MoDataException("There is no such key (component) of class " + componentType + " in the componentStores");

    if (store.get(entity) == null)
      throw new MoDataException("There is no component of class " + componentType + " in " + nameFor(entity));

    return (T) store.get(entity);
  }

  public <T extends MoComponent> void removeComponent(@NotNull final UUID entity, @NotNull final T component) {

    HashMap<UUID, ? extends MoComponent> store = componentStores.get(component.getClass());

    if (store == null)
      throw new MoDataException("There are no entities with component of class " + component.getClass());

    T result = (T) store.remove(entity);
    if (result == null)
      throw new MoDataException(nameFor(entity) + " does not possess component of class\n\tmissing: " + component.getClass());

    store.remove(entity, component);
  }

  public <T extends MoComponent> void removeComponent(@NotNull final UUID entity, @NotNull final Class<T> componentType) {

    HashMap<UUID, ? extends MoComponent> store = componentStores.get(componentType);

    if (store == null)
      throw new MoDataException("There are no entities with component of class " + componentType);

//    T result = (T) store.remove(entity);
//    if (result == null)
//      throw new MoDataException(nameFor(entity) + " does not possess component of class\n\tmissing: " + componentType);

    store.remove(entity, getComponent(entity, componentType));

  }


  public <T extends MoComponent> boolean hasComponent(@NotNull final UUID entity, @NotNull final Class<T> componentType) {

    Collection<MoComponent> components = getAllComponentsOfEntity(entity);

    boolean hasComponent = false;

    for (MoComponent component : components)
      if (component.getClass() == componentType) {
        hasComponent = true;
        break;
      }

    return hasComponent;
  }

  @NotNull
  public <T extends MoComponent> List<T> getAllComponentsOfEntity(@NotNull final UUID entity) {

    List<T> components = new LinkedList<>();

    componentStores.values().forEach(store -> {

      T componentFromThisEntity = (T) store.get(entity);
      if (componentFromThisEntity != null)
        components.add(componentFromThisEntity);
    });

    return components;
  }

  @NotNull
  public <T extends MoComponent> Collection<T> getAllComponentsOfType(@NotNull final Class<T> componentType) {

    HashMap<UUID, ? extends MoComponent> store = componentStores.get(componentType);

    if (store == null)
      return new LinkedList<>();

    return (Collection<T>) store.values();
  }

  /**
   * @param componentType is a superclass type, which will determine, what components in the hierarchy will be considered in search
   * @param <T>           class type
   * @return all entities possessing component (superclass)
   */
  @NotNull
  public <T extends MoComponent> Set<UUID> getAllEntitiesPossessingComponent(@NotNull final Class<T> componentType) {

    Set<UUID> allEntitiesPossessingComponent = new HashSet<>();

    getAllComponentsAssignableFrom(componentType).forEach(
        component -> allEntitiesPossessingComponent.addAll(componentStores.get(component.getClass()).keySet()));

    return allEntitiesPossessingComponent;
  }

  @SafeVarargs
  @NotNull
  public final Set<UUID> getAllEntitiesPossessingComponents(Class<? extends MoComponent>... componentTypes) {

    Set<UUID> allWithAtLeastOneComponent = new HashSet<>();
    Set<Class<? extends MoComponent>> types = new HashSet<>(Arrays.asList(componentTypes));

    types.forEach(componentType -> getAllComponentsAssignableFrom(componentType)
        .forEach(component -> allWithAtLeastOneComponent.addAll(componentStores.get(component.getClass()).keySet())));

    return new HashSet<>(filter(allWithAtLeastOneComponent,
                                input -> filter(getAllComponentsOfEntity(input), component ->
                                    types.contains(component.getClass())).size() == types.size()));
  }

  @NotNull
  public <T extends MoComponent> Collection<T> getAllComponentsAssignableFrom(@NotNull final Class<T> componentType) {

    List<T> componentsAssignableFrom = new ArrayList<>();

    componentStores.forEach((aClass, store) -> store.forEach((uuid, component) -> {

      if (componentType.isAssignableFrom(component.getClass()))
        componentsAssignableFrom.add((T) component);
    }));

    return componentsAssignableFrom;
  }

  public int componentCount() {

    int componentCount = 0;

    List<Integer> componentStoreSizes = new ArrayList<>();

    componentStores.forEach((aClass, componentStore) -> componentStoreSizes.add(componentStore.size()));

    for (Integer size : componentStoreSizes)
      componentCount += size;

    return componentCount;
  }

  public int entityCount() {

    return entities.size();
  }

  @NotNull
  public <T extends MoComponent> UUID createEntity() {

    final UUID uuid = UUID.randomUUID();
    entities.add(uuid);

    return uuid;
  }

  @NotNull
  public <T extends MoComponent> UUID createEntity(@NotNull final String entityName) {

    final UUID uuid = UUID.randomUUID();
    entities.add(uuid);
    entityHumanReadableNames.put(uuid, entityName);

    return uuid;
  }

  public void killEntity(@NotNull final UUID entity) {

    componentStores.values().forEach(store -> {

      if (store.containsKey(entity))
        store.remove(entity);
    });

    entities.remove(entity);
  }

  public void setEntityName(@NotNull final UUID entity, @NotNull final String name) {

    entityHumanReadableNames.put(entity, name);
  }

  @NotNull
  public String nameFor(UUID uuid) {

    return entityHumanReadableNames.get(uuid);
  }
}