package engine;

/**
 * Created by Hans on 26/10/2015.
 */
public class EventHandler {

  //After knowing what String enums do internally, I refuse to use them
  /*
  1 - up
  2 - down
  3 - left
  4 - right
  5 - choose frog
  6 - choose fly
   */

  //TODO Maybe store uid location?
  public void processEvent(int event, int uid) {
    switch (event) {
      case 1:
        break;
      case 2:
        break;
      case 3:
        break;
      case 4:
        break;
      case 5:
        break;
      default:
        System.out.println("REALLY BROKEN");
        System.exit(1);
    }
  }

  public void processUpMovement() {

  }

  public void processDownMovement() {

  }

  public void processLeftMovement() {

  }

  public void processRightMovement() {

  }

  public void processCharacterPick(boolean b) {

  }

}
