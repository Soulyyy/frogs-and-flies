package utils;

import connection.HomeworkPacket;
import connection.Processor;

import java.io.Serializable;

/**
 * Created by Hans on 27/10/2015.
 */
public class ClientProcessor<T extends Serializable> implements Processor<HomeworkPacket> {

  @Override
  public synchronized HomeworkPacket process(HomeworkPacket message) {
    if ("started".equals(message.getMessage())) {
    }
    if (Cache.event != null) {
      HomeworkPacket homeworkPacket = new HomeworkPacket(message.getId(), Cache.event, -1, -1, message.getCharacter(), "kolja");
      //Only call UI updates here
      Cache.event = null;
      return homeworkPacket;

    }
    return message;
  }
}
