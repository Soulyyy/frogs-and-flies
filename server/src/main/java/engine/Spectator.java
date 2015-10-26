package engine;

/**
 * Created by Hans on 26/10/2015.
 */
public class Spectator implements Character {

  Engine engine;

  public Spectator(Engine engine) {
    this.engine = engine;
  }

  @Override
  public int[][] visibleMap() {
    return mask();
  }

  @Override
  public int[][] mask() {
    return engine.getGameField();
  }
}
