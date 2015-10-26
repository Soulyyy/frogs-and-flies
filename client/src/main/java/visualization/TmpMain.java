package visualization;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
    StackPane root = new StackPane();
    root.getChildren().add(button);
    primaryStage.setScene(new Scene(root, 800, 600));
    primaryStage.show();
  }
}
