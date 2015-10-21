package connection;

import java.io.Serializable;

/**
 * Created by Hans on 19/10/2015.
 */
public class HomeworkPacket implements Serializable {
  int id;
  String message;
  //Example of what happens to transient fields
  transient String teletups = "TELETUPS";

  public HomeworkPacket() {
    this(-1, "Nothing");

  }

  public HomeworkPacket(int id, String message) {
    this.id = id;
    this.message = message;
  }

  @Override
  public String toString() {
    return "HomeworkPacket{" +
        "id:" + id +
        ", message:'" + message + '\'' +
        '}';
  }
}
