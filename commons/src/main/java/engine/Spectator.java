package engine;

import java.io.Serializable;

/**
 * Created by Hans on 26/10/2015.
 */
public class Spectator implements Character, Serializable {


  int[][] gameField;

  public Spectator(int[][] gameField) {
    this.gameField = gameField;
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

  public void updateMap(int[][] gameField) {
    this.gameField = gameField;
  }

  @Override
  public String getScore() {
    return "Spectating...";
  }
}
