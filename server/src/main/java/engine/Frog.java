package engine;

/**
 * Created by Hans on 26/10/2015.
 */
public class Frog implements Character {

  long startTime;

  long score;

  public Frog() {
    this.startTime = System.currentTimeMillis();
    this.score = 0;
  }

  @Override
  public int[][] visibleMap() {
    return new int[0][];
  }

  @Override
  public int[][] mask() {
    return new int[0][];
  }

  public boolean checkAlive() {
    return startTime+12000 > System.currentTimeMillis();
  }

  public void updateScore() {
    score++;
    this.startTime = System.currentTimeMillis();
  }
}
