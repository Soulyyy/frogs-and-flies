package engine;

import java.io.Serializable;

/**
 * Created by Hans on 26/10/2015.
 */
public class Fly implements Character, Serializable {

  public int x;
  public int y;
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

  public void updateMap(int[][] gameField, int x, int y) {
    this.gameField = gameField;
    this.x = x;
    this.y = y;
    //Why not
    updateScore();
  }

  @Override
  public int[][] mask() {
    int vision = 2;
    int[][] ints = this.gameField;
    for (int i = 0; i < ints.length; i++) {
      for (int j = 0; j < ints[0].length; j++) {
        if (!(this.x + vision >= j && this.x - vision <= j && this.y + vision >= i && this.y - vision <= i)) {
          ints[j][i] = 0;

        }
      }
    }
    return ints;
  }

  @Override
  public String toString() {
    return "Fly{" +
        "x=" + x +
        ", y=" + y +
        ", startTime=" + startTime +
        ", score=" + score +
        '}';
  }

  public void updateScore() {
    this.score = (System.currentTimeMillis() - startTime) / 12;
  }

  @Override
  public String getScore() {
    return score + "";
  }
}
