package visualization;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hans on 26/10/2015.
 */
public class TmpMain extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Frogs and Files");
    Button button = new Button();
    button.setText("Say 'kush'");
    button.setOnAction(event -> System.out.println("BOOM"));
    Pane root = new Pane();
    root.getChildren().add(button);
/*    Rectangle rectangle = new Rectangle();
    rectangle.setHeight(50);
    rectangle.setWidth(50);
    rectangle.setFill(Color.YELLOW);*/
   //Arrays.stream(initializeGameField(5,5)).flatMap(Arrays::stream).forEach(j -> root.getChildren().add(j));
    //root.getChildren().add(rectangle);
    Rectangle[][] rects = initializeGameField(5,5);
    List<Rectangle> rectangleList =  Arrays.stream(rects).flatMap(Arrays::stream).map(i -> i).collect(Collectors.toList());
    Rectangle[] rectangles = rectangleList.toArray(new Rectangle[rectangleList.size()]);
    root.getChildren().addAll(rectangles);
    System.out.println(root.getChildren());
    primaryStage.setScene(new Scene(root, 800, 600));
    primaryStage.show();
    int[][] black = new int[5][5];
    int[] [] yellow = new int[5][5];
    for(int i = 0; i < yellow.length ;i++) {
      for(int j = 0; j < yellow[0].length; j++) {
        yellow[i][j] = 1;
      }
    }
    Task task = new Task<Void>() {

      @Override
      protected Void call() throws Exception {
        while (true) {
          Thread.sleep(1000);
          updateGameField(rects, black);
          Thread.sleep(1000);
          updateGameField(rects, yellow);
        }
      }
    };
    new Thread(task).start();
  }

  public Rectangle[][] initializeGameField(int M, int N) {
    Rectangle[][] field = new Rectangle[M][N];
    //Arrays.stream(field).flatMap(Arrays::stream).map(j -> j = new Rectangle(50, 50, Color.YELLOW));
    //Can this be done with a stream?
    for(int i = 0; i< M; i++) {
      for (int j = 0; j < N ; j++) {
        field[i][j] = new Rectangle(70,70, Color.YELLOW);
        field[i][j].setX(72*i + 4);
        field[i][j].setY(72*j + 4);
      }
    }
    return field;

  }

  public void updateGameField(Rectangle[][] rectangles, int[][] ints) {
    for (int i = 0; i < rectangles.length; i++) {
      for (int j = 0; j < rectangles[0].length; j++) {
        rectangles[i][j].setFill(mapIntToColor(ints[i][j]));
      }
    }

  }

  public Color mapIntToColor(int i) {
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
}
