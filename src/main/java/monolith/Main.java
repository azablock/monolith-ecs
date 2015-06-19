package monolith;

import javafx.application.Application;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import monolith.ioc.MonolithFxmlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static javafx.scene.input.KeyCombination.NO_MATCH;
import static javafx.stage.StageStyle.UNDECORATED;

public class Main extends Application {

  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  @Override
  public void start(Stage primaryStage) throws Exception {

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("monolith/meta-info/spring/monolith-spring-context.xml");
    MonolithFxmlLoader loader = applicationContext.getBean(MonolithFxmlLoader.class);
    Parent root = loader.load();

    primaryStage.setTitle("Monolith");
    primaryStage.initStyle(UNDECORATED);
    primaryStage.centerOnScreen();
    Scene scene = new Scene(root, 1280, 800);
    scene.getStylesheets().add("monolith/ui/skins/monolith-default.css");
    scene.setCursor(new ImageCursor(new Image("monolith/images/monolithCursor.png")));

    primaryStage.setFullScreenExitKeyCombination(NO_MATCH);
    primaryStage.setFullScreen(true);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {

    launch(args);
  }
}
