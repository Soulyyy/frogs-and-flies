package utils;

import connection.HomeworkPacket;
import connection.Processor;
import engine.Event;
import visualization.Main;

import java.io.Serializable;

/**
 * Created by Hans on 27/10/2015.
 */
public class ClientProcessor<T extends Serializable> implements Processor<HomeworkPacket> {

  @Override
  public synchronized HomeworkPacket process(HomeworkPacket message) {
    if(message.getId() == -100) {
      System.out.println("Dead");
    }
    if (message.getCharacter() != null) {
      System.out.println("NOP IM NOT NULL");
      Main.updateGameField(message.getCharacter().visibleMap());
    }
    if ("started".equals(message.getMessage())) {
    }
    if (Cache.event != null) {
      HomeworkPacket homeworkPacket = new HomeworkPacket(message.getId(), Cache.event, message.getCharacter(), "kolja");
      //Deref the old event
      Cache.event = new Event(-1);
      if(homeworkPacket != null && homeworkPacket.getCharacter() != null) {
        Main.changeScore(homeworkPacket.getCharacter().getScore());
      } else {
        Main.changeScore("No score");
      }

      System.out.println("Main score is : "+Main.score);
      return homeworkPacket;

    }
    return message;
  }
}
