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

  //We have the x and y coordinates, why not use them
  public synchronized int[] movePlayer(int m, int n, int uid, int x, int y) {
    int tx = -1;
    int ty = -1;
    if(m < gameField.length && n < gameField[0].length) {

    }
    else {
      System.out.println("BORKEN REQUEST LALALALA");
    }
    return new int[]{x, y};
  }

  public synchronized int[][] getGameField() {
    return gameField;
  }
}
