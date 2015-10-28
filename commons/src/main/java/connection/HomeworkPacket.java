package connection;

import engine.Character;
import engine.Event;

import java.io.Serializable;

/**
 * Created by Hans on 19/10/2015.
 */
public class HomeworkPacket implements Serializable {
  private int id;
  private String message;

  private String username;

  private int x;
  private int y;

  private Character character;

  private Event event;

  //Example of what happens to transient fields
  transient String teletups = "TELETUPS";

  public HomeworkPacket() {
    this(-1, "Nothing");

  }

  public HomeworkPacket(int id, Event event, Character character, String username) {
    this.id = id;
    this.event = event;
    this.character = character;
    this.username = username;
  }

  public HomeworkPacket(int id, String message) {
    this.id = id;
    this.message = message;
  }

  @Override
  public String toString() {
    return "HomeworkPacket{" +
        "event=" + event +
        ", character=" + character +
        ", y=" + y +
        ", x=" + x +
        ", message='" + message + '\'' +
        ", id=" + id +
        '}';
  }

  public String getMessage() {
    return message;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public Event getEvent() {
    return event;
  }

  public Character getCharacter() {
    return character;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
