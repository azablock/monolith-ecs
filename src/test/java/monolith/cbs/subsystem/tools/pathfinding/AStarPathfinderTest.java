package monolith.cbs.subsystem.tools.pathfinding;

import javafx.stage.Stage;
import monolith.cbs.component.position.Apex;
import monolith.cbs.component.position.DiscretePositionUtils;
import monolith.cbs.entity.LevelSectorBuilder;
import monolith.cbs.entity.MoEntityBuilderManager;
import monolith.test.MoBaseSpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.fest.assertions.Assertions.assertThat;

import java.util.LinkedList;

public class AStarPathfinderTest extends MoBaseSpringTest {

  @Autowired
  private AStarPathfinder aStarPathfinder;

  @Autowired
  private MoEntityBuilderManager builderManager;

  @Test
  public void shouldGiveExpectedPath1() throws Exception {

    //given
    Apex start = DiscretePositionUtils.pos(1, 1);
    Apex destination = DiscretePositionUtils.pos(3, 1);
    LinkedList<Apex> expectedPath = new LinkedList<>();
    expectedPath.add(new Apex(2, 1));
    expectedPath.add(new Apex(3, 1));

    //when
    builderManager.builderFor(LevelSectorBuilder.class).build();
    LinkedList<Apex> aStarPath = aStarPathfinder.path(start, destination);

    //then
    assertThat(aStarPath).isEqualTo(expectedPath);
  }

  @Override
  public void start(Stage stage) throws Exception {

  }
}