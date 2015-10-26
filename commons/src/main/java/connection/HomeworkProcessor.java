package connection;

import java.io.Serializable;

/**
 * Created by Hans on 22/10/2015.
 */
public class HomeworkProcessor<T extends Serializable> implements Processor<T> {

  public HomeworkProcessor() {
  }

  @Override
  public T process(T message) {
    if(message instanceof HomeworkPacket) {
      HomeworkPacket input = (HomeworkPacket) message;
      String string = "";
      if("start".equals(input.message)) {
        string = "started";
      }
      HomeworkPacket resp = new HomeworkPacket(input.id, string);
      return (T) resp;
    }
    return null;
  }
}
