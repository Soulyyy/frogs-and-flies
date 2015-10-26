package engine;

/**
 * Created by Hans on 26/10/2015.
 */
public interface Character {

  public int[][] visibleMap();

  //We mask the field we send
  public int[] [] mask();
}
