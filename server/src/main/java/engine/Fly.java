package engine;

/**
 * Created by Hans on 26/10/2015.
 */
public class Fly implements Character {

  long startTime;

  long score;

  public Fly() {
    //Initialize time for fly, this will compute score
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


  public void updateScore() {
    this.score = (System.currentTimeMillis() - startTime) / 12000;
  }
}
