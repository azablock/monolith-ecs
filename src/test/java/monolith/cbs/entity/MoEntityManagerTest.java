package monolith.cbs.entity;

import javafx.stage.Stage;
import monolith.cbs.component.MoComponent;
import monolith.cbs.component.graphics.Graphics;
import monolith.cbs.component.graphics_addition_request.GraphicsAdditionRequest;
import monolith.cbs.component.position.ApexPosition;
import monolith.test.MoBaseSpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static monolith.cbs.entity.MoEntityManager.ENTITY_MANAGER;
import static org.fest.assertions.Assertions.assertThat;

public class MoEntityManagerTest extends MoBaseSpringTest {

  @Autowired
  private MoEntityBuilderManager entityBuilderManager;

  @Test
  public void shouldProperlyCountEntitiesThatHaveSpecificComponentTypes() throws Exception {

    //given
    MoMetaEntity crate = entityBuilderManager.builderFor(CrateBuilder.class).build();
    MoMetaEntity playerActor = entityBuilderManager.builderFor(PlayerActorBuilder.class).build();

    //when
    Set<UUID> allWithApexPosition = ENTITY_MANAGER.getAllEntitiesPossessingComponent(ApexPosition.class);
    Set<UUID> allWithGraphics = ENTITY_MANAGER.getAllEntitiesPossessingComponent(Graphics.class);
    Set<UUID> allWithGraphicsAndRequest = ENTITY_MANAGER.getAllEntitiesPossessingComponents(Graphics.class, GraphicsAdditionRequest.class);

    //then
    assertThat(allWithApexPosition.size()).isEqualTo(2);
    assertThat(allWithGraphics.size()).isEqualTo(2);
    assertThat(allWithGraphicsAndRequest.size()).isEqualTo(2);
  }

  @Override
  public void start(Stage stage) throws Exception {

  }
}