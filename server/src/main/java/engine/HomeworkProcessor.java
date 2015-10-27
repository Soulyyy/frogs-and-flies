package engine;

import connection.HomeworkPacket;
import connection.Processor;

import java.io.Serializable;

/**
 * Created by Hans on 22/10/2015.
 */
public class HomeworkProcessor<T extends Serializable> implements Processor<HomeworkPacket> {

  private Engine engine;

  public HomeworkProcessor(Engine engine) {
    this.engine = engine;
  }

  @Override
  public HomeworkPacket process(HomeworkPacket message) {
    System.out.println("homeworkprocessor processing");
    String string = "";
    if ("start".equals(message.getMessage())) {
      string = "started";
      return new HomeworkPacket(message.getId(), string);
    }
    return processHomeworkPacket(message);
  }


  public HomeworkPacket processHomeworkPacket(HomeworkPacket homeworkPacket) {
    Character character = (homeworkPacket.getCharacter() == null) ? new Spectator(this.engine.getGameField()) : homeworkPacket.getCharacter();
    int x = homeworkPacket.getX();
    int y = homeworkPacket.getY();
    if (homeworkPacket.getEvent() != null) {
      Event event = homeworkPacket.getEvent();
      if (character instanceof Spectator ) {
        System.out.println("SPECTATOR DONT CARE");

        //Choose frog
        if (event.getFirstEvent() == 5) {
          System.out.println("Created new fly");
          character = new Frog(this.engine.getGameField(), 0, 0);
          x = 0;
          y = 0;
        }
        //Choose fly
        else if (event.getFirstEvent() == 6) {
          //Check for a free spot to start!
          System.out.println("Created new frog");
          character = new Fly(this.engine.getGameField(), 0, 0);
          x = 0;
          y = 0;
        } else {
          System.out.println("not a valid event");
        }
      } else if (character instanceof Frog) {
        boolean doubleMove = event.getEvents()[0] == event.getEvents()[1];
        Frog tmp = (Frog) character;
      } else if (character instanceof Fly) {
        Fly tmp = (Fly) character;

      }

    }
    //Always send empty event back, null the queue
    HomeworkPacket resp = new HomeworkPacket(homeworkPacket.getId(), new Event(0), x, y, character, homeworkPacket.getUsername());
    return resp;
  }

  private void processFrog(Frog frog, boolean doubleMove) {

  }

  private void processFly(Fly fly) {

  }


}
