package engine;

import java.io.Serializable;

/**
 * Created by Hans on 26/10/2015.
 */
public interface Character extends Serializable {

  public int[][] visibleMap();

  //We mask the field we send
  public int[] [] mask();

  public void updateScore();
}
