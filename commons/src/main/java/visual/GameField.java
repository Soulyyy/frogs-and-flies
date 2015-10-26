package visual;

/**
 * Created by Hans on 25/10/2015.
 */
public class GameField {

  private int offset;

  private int[] field;

  private int myPosition;

  public GameField(int offset, int[] field, int myPosition) {

  }

  //TODO send requests against this to check whether in the current state, the move is valid
  //This should be done before sending move back to server
  public boolean validateMove() {
    return true;
  }
}
