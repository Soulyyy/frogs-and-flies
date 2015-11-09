package engine;

import connection.HomeworkPacket;
import connection.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by Hans on 22/10/2015.
 */
public class HomeworkProcessor implements Processor<HomeworkPacket> {

  static final Logger LOGGER = LoggerFactory.getLogger(HomeworkProcessor.class);

  private Engine engine;

  public HomeworkProcessor(Engine engine) {
    this.engine = engine;
  }

  @Override
  public HomeworkPacket process(HomeworkPacket message) {
    LOGGER.info("HomeworkProcessor processing");
    String string;
    if ("start".equals(message.getMessage())) {
      LOGGER.debug("Message is start");
      string = "started";
      return new HomeworkPacket(message.getId(), string);
    }
    LOGGER.debug("Message is not start");
    return processHomeworkPacket(message);
  }


  public HomeworkPacket processHomeworkPacket(HomeworkPacket homeworkPacket) {
    LOGGER.info("The packet before processing is:\n" + homeworkPacket);
    Character character = (homeworkPacket.getCharacter() == null) ? new Spectator(this.engine.getGameField()) : homeworkPacket.getCharacter();
    LOGGER.debug("The packet after character check is:\n" + homeworkPacket);
    int x = homeworkPacket.getX();
    int y = homeworkPacket.getY();
    if (homeworkPacket.getEvent() != null) {
      Event event = homeworkPacket.getEvent();
      if (character instanceof Spectator) {
        LOGGER.debug("User is a spectator");
        //Choose frog
        if (event.getFirstEvent() == 5) {
          LOGGER.info("Created new frog");
          //character = new Frog(this.engine.getGameField(), 0, 0);
          while (true) {
            x = (int) Math.round(Math.random() * (engine.getGameField()[0].length - 1));
            y = (int) Math.round(Math.random() * (engine.getGameField().length - 1));
            if (engine.getGame()[y][x] == null) {
              character = new Frog(this.engine.getGameField(), y, x);
              engine.addCharacter(character, x, y);
              break;
            }
          }
        }
        //Choose fly
        else if (event.getFirstEvent() == 6) {
          //Check for a free spot to start!
          LOGGER.info("Created new fly");
          //character = new Fly(this.engine.getGameField(), 0, 0);
          while (true) {
            x = (int) Math.round(Math.random() * (engine.getGame()[0].length - 1));
            y = (int) Math.round(Math.random() * (engine.getGame().length - 1));
            if (engine.getGame()[y][x] == null) {
              character = new Fly(this.engine.getGameField(), y, x);
              engine.addCharacter(character, x, y);
              break;
            }
          }
/*          x = 0;
          y = 0;*/
        } else {
          ((Spectator) character).updateMap(engine.getGameField());
          LOGGER.warn("Not a valid event");
        }
      } else if (character instanceof Frog) {
        x = ((Frog) character).x;
        y = ((Frog) character).y;
        LOGGER.info("Found a frog, {}", homeworkPacket);
        boolean doubleMove = event.getEvents()[0] == event.getEvents()[1];

        Frog tmp = (Frog) character;
        int[] ints = processFrog(tmp, doubleMove, event, x, y);
        if (ints.length == 1) {
          LOGGER.info("DEAD");
          new HomeworkPacket(-100, "Dead");
        } else {
          x = ints[1];
          y = ints[0];
          tmp.updateMap(engine.getGameField(tmp), ints[0], ints[1]);
        }
        character = tmp;
        ((Frog) character).x = x;
        ((Frog) character).y = y;
        homeworkPacket.setX(x);
        homeworkPacket.setY(y);
        homeworkPacket.setEvent(new Event(-1));
        return homeworkPacket;

      } else if (character instanceof Fly) {
        x = ((Fly) character).x;
        y = ((Fly) character).y;
        LOGGER.info("Found a fly, {}", homeworkPacket);
        Fly tmp = (Fly) character;
        LOGGER.debug("X and Y before actual parsing: " + x + " " + y);
        int[] ints = processFly(tmp, event, x, y);
        x = ints[1];
        y = ints[0];
        if (ints.length == 1) {
          LOGGER.info("Dead");
          new HomeworkPacket(-100, "Dead");
        } else {
          tmp.updateMap(engine.getGameField(tmp), y, x);
        }
        character = tmp;
        ((Fly) character).x = x;
        ((Fly) character).y = y;
        homeworkPacket.setX(x);
        homeworkPacket.setY(y);
        homeworkPacket.setEvent(new Event(-1));
        LOGGER.info("Fly packet is: \n" + homeworkPacket);
        return homeworkPacket;
        //return new HomeworkPacket(homeworkPacket.getId(), new Event(-1), character, homeworkPacket.getUsername());

      }

    }
    //Always send empty event back, null the queue
    LOGGER.info("Set X as : " + x + " and Y as : " + y);
    HomeworkPacket resp = new HomeworkPacket(homeworkPacket.getId(), new Event(-1), character, homeworkPacket.getUsername());

    return resp;
  }

  private int[] processFrog(Frog frog, boolean doubleMove, Event event, int x, int y) {
    LOGGER.debug("Processing frog movement");
    int[] ints = EventHandler.processEvent(event, doubleMove, x, y);
    LOGGER.debug("Number of parameters: " + ints.length);
    LOGGER.debug("0:" + ints[0]);
    LOGGER.debug("1:" + ints[1]);
    return engine.validatePosition(ints[0], ints[1], frog, x, y);
  }

  private int[] processFly(Fly fly, Event event, int x, int y) {
    LOGGER.debug("Processing fly movement");
    int[] ints = EventHandler.processEvent(event, false, x, y);
    return engine.validatePosition(ints[0], ints[1], fly, x, y);
  }


}
