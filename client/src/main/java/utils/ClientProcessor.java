package utils;

import connection.HomeworkPacket;
import connection.Processor;
import engine.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Hans on 27/10/2015.
 */
public class ClientProcessor implements Processor<HomeworkPacket> {

  static final Logger LOGGER = LoggerFactory.getLogger(ClientProcessor.class);

  @Override
  public synchronized HomeworkPacket process(HomeworkPacket message) {
    if (message.getId() == -100 || message.getId() == -666) {
      LOGGER.info("Dead");
      System.exit(0);
    }
    if (message.getCharacter() != null) {
      LOGGER.debug("Message is not null");
      Main.updateGameField(message.getCharacter().visibleMap());
    }
    if (Cache.event != null) {
      HomeworkPacket homeworkPacket = new HomeworkPacket(message.getId(), Cache.event, message.getCharacter(), "name");
      //Deref the old event
      Cache.event = new Event(-1);
      if (homeworkPacket.getCharacter() != null) {
        Main.changeScore(homeworkPacket.getCharacter().getScore());
      } else {
        Main.changeScore("No score");
      }
      LOGGER.info("Score is: {}", Main.score);
      return homeworkPacket;

    }
    return message;
  }
}
