package engine;

/**
 * Created by Hans on 26/10/2015.
 */
//Class to contain the engine(gives the processor for the game)
public class Engine {

  //This field contains the whole game field
  public static int[][] gameField;

  public Engine(int M, int N) {
    gameField = new int[M][N];
  }

  public void movePlayer(int m, int n, int uid) {
    if(m < gameField.length && n < gameField[0].length) {

    }
    else {
      System.out.println("BORKEN REQUEST LALALALA");
    }
  }

  public int[][] getGameField() {
    return gameField;
  }
}
