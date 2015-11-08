package visualization;

import connection.HomeworkPacket;
import engine.Event;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import utils.Cache;
import utils.Client;
import utils.ClientImpl;
import utils.ClientProcessor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hans on 26/10/2015.
 */

//FRONT END DEVELOPMENT REQUIRES NO CODE QUALITY
//            (Only CSS)
public class Main extends Application {

  static Rectangle[][] rects;

  static String ip;

  static String name = "";

  public static String score = "0";

  public static Label scoreLabel;

  public static void changeScore(String score) {
    Platform.runLater(() -> scoreLabel.setText(score));
  }

  public static void main(String[] args) {
    if (args.length == 1) {
      ip = args[0];
      name = "default";
    } else if (args.length > 1) {
      ip = args[0];
      name = args[1];
    } else {
      ip = "localhost";
      name = "default";
    }
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Client<HomeworkPacket> client = new ClientImpl<>(ip, ClientProcessor::new);
    client.connect();
    System.out.println("Client working!");
    client.submitMessage(new HomeworkPacket(-1, "start"));
    Label nameLabel = new Label(name);

    scoreLabel = new Label("0");
    scoreLabel.setLayoutX(650);
    scoreLabel.setLayoutY(100);
    nameLabel.setLayoutX(650);
    nameLabel.setLayoutY(200);

    Pane root = new Pane();
    //root.getChildren().add(button);
    rects = initializeGameField(8, 8);
    List<Rectangle> rectangleList = Arrays.stream(rects).flatMap(Arrays::stream).collect(Collectors.toList());
    Rectangle[] rectangles = rectangleList.toArray(new Rectangle[rectangleList.size()]);
    System.out.println(root.getChildren());
    Scene scene = new Scene(root, 800, 600);
    Button fly = chooseFly(scene, primaryStage);
    fly.setLayoutX(700);
    fly.setLayoutY(100);
    root.getChildren().add(fly);
    Button frog = chooseFrog(scene, primaryStage);
    frog.setLayoutX(700);
    frog.setLayoutY(200);

    root.getChildren().add(frog);
    //menu.getChildren().addAll(rectangles);
    root.getChildren().addAll(rectangles);
    primaryStage.setScene(scene);
    primaryStage.show();

    fly.setOnAction(event -> {
          root.getChildren().removeAll(fly, frog);
          root.getChildren().addAll(scoreLabel, nameLabel);
          Cache.event = new Event(6);
          primaryStage.setScene(scene);
          primaryStage.show();
        }
    );

    frog.setOnAction(event -> {
          root.getChildren().removeAll(fly, frog);
          root.getChildren().addAll(scoreLabel, nameLabel);
          Cache.event = new Event(5);
          primaryStage.setScene(scene);
          primaryStage.show();
        }
    );

    scene.setOnKeyPressed(event -> {
      if (Cache.event == null) {
        Cache.event = new Event(-1);
      }
      if (event.getCode() == KeyCode.UP) {
        Cache.event.update(1);
      } else if (event.getCode() == KeyCode.DOWN) {
        Cache.event.update(2);
      } else if (event.getCode() == KeyCode.LEFT) {
        Cache.event.update(3);
      } else if (event.getCode() == KeyCode.RIGHT) {
        Cache.event.update(4);
      }
    });
  }

  public Rectangle[][] initializeGameField(int M, int N) {
    Rectangle[][] field = new Rectangle[M][N];
    //Arrays.stream(field).flatMap(Arrays::stream).map(j -> j = new Rectangle(50, 50, Color.YELLOW));
    //Can this be done with a stream?
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        field[i][j] = new Rectangle(70, 70, Color.YELLOW);
        field[i][j].setX(72 * i + 4);
        field[i][j].setY(72 * j + 4);
      }
    }
    return field;

  }

  public static void updateGameField(int[][] ints) {
    for (int i = 0; i < rects.length; i++) {
      for (int j = 0; j < rects[0].length; j++) {
        rects[i][j].setFill(mapIntToColor(ints[i][j]));
        System.out.print(ints[i][j]);
      }
      System.out.println();
    }

  }

  public static Color mapIntToColor(int i) {
    switch (i) {
      case 0:
        return Color.BLACK;
      case 1:
        return Color.YELLOW;
      case 2:
        return Color.RED;
      case 3:
        return Color.GREEN;
      case 4:
        return Color.BLUE;
      default:
        return null;
    }
  }


  public Button chooseFly(Scene scene, Stage stage) {
    Button button = new Button("Choose Fly");
    button.setOnAction(event -> {
      Cache.event = new Event(6);
      stage.setScene(scene);
      stage.show();
    });

    return button;
  }


  public Button chooseFrog(Scene scene, Stage stage) {
    Button button = new Button("Choose Frog");
    button.setOnAction(event -> {
      Cache.event = new Event(5);
      stage.setScene(scene);
      stage.show();

    });
    return button;
  }
}
