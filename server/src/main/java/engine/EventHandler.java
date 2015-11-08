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
  public static int[] processEvent(Event event, boolean b, int x, int y) {
    switch (event.getFirstEvent()) {
      case 1:
        if(b) {
          return new int[]{x, y-2};
        } else {
          return new int[]{x, y-1};
        }
      case 2:
        if(b) {
          return new int[]{x, y+2};
        } else {
          return new int[]{x, y+1};
        }
      case 3:
        if(b) {
          return new int[]{x-2, y};
        } else {
          return new int[]{x-1, y};
        }
      case 4:
        if(b) {
          return new int[]{x+2, y};
        } else {
          return new int[]{x+1, y};
        }
      default:
        return new int[]{x, y};
    }
  }

}
