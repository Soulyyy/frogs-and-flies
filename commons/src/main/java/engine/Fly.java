package engine;

/**
 * Created by Hans on 26/10/2015.
 */
public class Fly implements Character {

  int x;
  int y;
  long startTime;

  long score;

  private int[][] gameField;

  public Fly(int[][] gameField, int x, int y) {
    //Initialize time for fly, this will compute score
    this.startTime = System.currentTimeMillis();
    this.score = 0;
    this.gameField = gameField;
    this.x = x;
    this.y = y;
  }

  @Override
  public int[][] visibleMap() {
    this.gameField = mask();
    return this.gameField;
  }

  @Override
  public int[][] mask() {
    int vision = 2;
    int[][] ints = this.gameField;
    for (int i = 0; i < ints.length; i++) {
      for (int j = 0; j < ints[0].length; j++) {
        if (!(i >= this.y - vision && i <= this.y + vision || j >= this.x - vision && j <= this.x + vision)) {
          ints[i][j] = 0;
        }
      }
    }
    return ints;
  }


  public void updateScore() {
    this.score = (System.currentTimeMillis() - startTime) / 12000;
  }
}
