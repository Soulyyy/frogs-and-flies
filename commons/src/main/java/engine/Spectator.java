package engine;

/**
 * Created by Hans on 26/10/2015.
 */
public class Spectator implements Character {


  int[][] gameField;

  public Spectator(int[][] gameField) {
    this.gameField = gameField;
  }

  public Spectator() {

  }

  @Override
  public int[][] visibleMap() {
    return mask();
  }

  @Override
  public int[][] mask() {
    return gameField;
  }

  @Override
  public void updateScore() {
    //No need for this character to have this method. Others need
  }

  @Override
  public String getScore() {
    return "Spectating...";
  }
}
