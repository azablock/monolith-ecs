package monolith.cbs.subsystem.tools.pathfinding.a_star;

import monolith.cbs.component.bounding_box.BoundingBox;
import monolith.cbs.component.position.Apex;
import monolith.cbs.subsystem.tools.pathfinding.Pathfinder;
import monolith.error.MoDataException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.google.common.collect.Collections2.filter;
import static monolith.cbs.component.position.DiscretePositionUtils.pos;
import static monolith.cbs.entity.MoEntityManager.ENTITY_MANAGER;
import static monolith.cbs.subsystem.tools.pathfinding.a_star.AStarConstants.VER_HOR_MOVEMENT_COST;

@Component
@Primary
public class AStarPathfinder implements Pathfinder {

  @Autowired
  private AStarNodeFactory aStarNodeFactory;

  private Map<Apex, AStarNode> openNodes;

  private Map<Apex, AStarNode> closedNodes;

  private Apex destination;

  private AStarNode currentNode;

  @NotNull
  @Override
  public LinkedList<Apex> calculatePath(@NotNull final Apex start,
                                        @NotNull final Apex destination) throws MoDataException {

    this.destination = destination;

    if (start.equals(destination))
      throw new MoDataException("start and destination are at the same position");

    currentNode = AStarNodeFactory.newNode(start, destination, null);
    openNodes.put(currentNode.getApexPosition(), currentNode);

    addNeighbourNodes(currentNode, getNeighbourPositions(currentNode.getApexPosition()));
    markAsClosed(currentNode);

    while (!openNodes.containsKey(destination)) {

      currentNode = getNodeWithSmallestOverallCost();
      markAsClosed(currentNode);
      addNeighbourNodes(currentNode, getNeighbourPositions(currentNode.getApexPosition()));

      getNeighbourPositions(currentNode.getApexPosition()).forEach(apex -> {

        AStarNode neighbour = openNodes.get(apex);
        if (neighbour.getParentPosition() == null)
          neighbour.reparent(currentNode.getApexPosition());

        else if (mustBeReparented(apex))
          neighbour.reparent(currentNode.getApexPosition());
      });
    }

    currentNode = openNodes.get(destination);

    LinkedList<Apex> path = new LinkedList<>();
    Apex currentPositionInPath = currentNode.getApexPosition();

    while (!currentPositionInPath.equals(start)) {
      path.addFirst(currentPositionInPath);
      currentNode = closedNodes.get(currentNode.getParentPosition());
      currentPositionInPath = currentNode.getApexPosition();
    }

    path.addFirst(currentPositionInPath);

    return path;
  }

  private void markAsClosed(@NotNull final AStarNode node) throws MoDataException {

    if (!openNodes.containsValue(node))
      throw new MoDataException("cannot mark node as closed, because node wasn't in openNodes collection");

    openNodes.remove(node.getApexPosition());
    closedNodes.put(node.getApexPosition(), node);
  }

  @NotNull
  private Set<Apex> getNeighbourPositions(@NotNull final Apex currentPosition) {

    Set<Apex> possibleNeighbourNodePositions = new CopyOnWriteArraySet<>();
    Collection<BoundingBox> levelShape = ENTITY_MANAGER.getAllComponentsOfType(BoundingBox.class);

    possibleNeighbourNodePositions.add(pos(currentPosition.getX() - 1, currentPosition.getY()));
    possibleNeighbourNodePositions.add(pos(currentPosition.getX() + 1, currentPosition.getY()));
    possibleNeighbourNodePositions.add(pos(currentPosition.getX(), currentPosition.getY() + 1));
    possibleNeighbourNodePositions.add(pos(currentPosition.getX(), currentPosition.getY() - 1));

    possibleNeighbourNodePositions.forEach(apex -> {

      if (filter(levelShape, boundingBox -> boundingBox.contains(apex)).isEmpty() ||
          (openNodes.containsKey(apex) || closedNodes.containsKey(apex)))
        possibleNeighbourNodePositions.remove(apex);
    });

    return possibleNeighbourNodePositions;
  }

  private void addNeighbourNodes(@NotNull final AStarNode node, @NotNull final Set<Apex> neighbourPositions) {

    neighbourPositions.forEach(apex -> {

      AStarNode newNode = AStarNodeFactory.newNode(apex, destination, node.getApexPosition());
      newNode.addMovementCost(VER_HOR_MOVEMENT_COST + node.getMovementCost());
      openNodes.put(apex, newNode);
    });
  }

  private boolean mustBeReparented(@NotNull final Apex nodePosition) {

    return (currentNode.getOverallCost() + VER_HOR_MOVEMENT_COST) <
           (openNodes.get(nodePosition).getOverallCost());
  }

  @NotNull
  private AStarNode getNodeWithSmallestOverallCost() {

    LinkedList<AStarNode> nodes = new LinkedList<>(openNodes.values());
    Collections.sort(nodes);

    return nodes.getFirst();
  }

  private static class AStarNodeFactory {

    @NotNull
    public static AStarNode newNode(@NotNull Apex nodePosition, @NotNull Apex destination, @Nullable Apex parentPosition) {

      return new AStarNode(calculateHeuristicValue(nodePosition, destination),
                           nodePosition, parentPosition);
    }

    @NotNull
    private static Integer calculateHeuristicValue(@NotNull final Apex nodePosition, @NotNull final Apex destination) {

      return Math.abs(nodePosition.getX() - destination.getX()) +
             Math.abs(nodePosition.getY() - destination.getY());
    }
  }
}
