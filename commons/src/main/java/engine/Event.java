package engine;

import java.io.Serializable;

/**
 * Created by Hans on 26/10/2015.
 */

  /*
  1 - up
  2 - down
  3 - left
  4 - right
  5 - choose frog
  6 - choose fly
   */

//Need extra class to imitate tuple. Technically a rolling(circular) queue. Every user has an event queue
public class Event implements Serializable {

  private int firstEvent;
  private int secondEvent;

  public Event(int event) {
    firstEvent = event;
    secondEvent = 0;
  }

  public void update(int event) {
    this.secondEvent = this.firstEvent;
    this.firstEvent = event;
  }

  //TODO this might need special treatment, maybe return boolean and int
  public int[] getEvents() {
    return new int[] {firstEvent, secondEvent};
  }

  public int getFirstEvent() {
    return firstEvent;
  }

  @Override
  public String toString() {
    return "Event{" +
        "firstEvent=" + firstEvent +
        ", secondEvent=" + secondEvent +
        '}';
  }
}
