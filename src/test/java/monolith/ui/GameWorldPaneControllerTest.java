package monolith.ui;

import javafx.scene.Node;
import javafx.stage.Stage;
import monolith.cbs.component.MoComponentFactoryManager;
import monolith.cbs.component.graphics.Graphics;
import monolith.cbs.entity.MoEntityBuilderManager;
import monolith.cbs.entity.MoMetaEntity;
import monolith.cbs.entity.PlayerActorBuilder;
import monolith.cbs.subsystem.RenderSystem;
import monolith.test.MoBaseSpringTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.fest.assertions.Assertions.assertThat;

public class GameWorldPaneControllerTest extends MoBaseSpringTest {

  @Autowired
  private MoComponentFactoryManager componentFactoryManager;

  @Autowired
  private MoEntityBuilderManager moEntityBuilderManager;

  @Autowired
  private GameWorldPaneController gameWorldPaneController;

  @Autowired
  private RenderSystem renderSystem;

  private MoMetaEntity entity;

  @Before
  public void init() {

    entity = moEntityBuilderManager.builderFor(PlayerActorBuilder.class).build();
  }

  @Override
  public void start(Stage stage) throws Exception {

  }

  @Test
  public void shouldAddProperlyNodeFromGraphicsComponentToPane() throws Exception {

    //given
    Node node = entity.get(Graphics.class).node;

    //when
//    gameWorldPaneController.getGameWorldPane().getChildren().add(node);

    //then
//    assertThat(gameWorldPaneController.getGameWorldPane().getChildren().size()).isEqualTo(1);
  }
//
//  @Test
//     public void shouldHaveNoChildren() throws Exception {
//
//    //given
//
//    //when
//    int childrenAmount = gameWorldPaneController.getGameWorldPane().getChildren().size();
//
//    //then
//    assertThat(childrenAmount).isEqualTo(0);
//  }
//
//  @Test
//  public void shouldProperlyRemoveChildFromPane() throws Exception {
//
//    //given
//
//    //when
//    gameWorldPaneController.getGameWorldPane().getChildren().add(entity.get(Graphics.class).node);
//    entity.remove(entity.get(Graphics.class));
//    renderSystem.processOneGameTick();
//
//    //then
//    assertThat(gameWorldPaneController.getGameWorldPane().getChildren().size()).isEqualTo(0);
//  }

}