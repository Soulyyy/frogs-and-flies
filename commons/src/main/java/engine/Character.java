package engine;

import java.io.Serializable;

/**
 * Created by Hans on 26/10/2015.
 */
public interface Character extends Serializable {

  int[][] visibleMap();

  //We mask the field we send
  int[] [] mask();

  void updateScore();

  String getScore();
}
