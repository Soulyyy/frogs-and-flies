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
      if (character instanceof Spectator) {
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
        System.out.println("Found a frog");
        boolean doubleMove = event.getEvents()[0] == event.getEvents()[1];

        Frog tmp = (Frog) character;
        int[] ints = processFrog(tmp, doubleMove, event, x, y);
        if (ints.length == 1) {
          System.out.println("DEAD");
          new HomeworkPacket(-100, "Dead");


        } else {
          tmp.updateMap(engine.getGameField(tmp), ints[0], ints[1]);
          for(int i = 0; i< engine.getGameField().length; i++) {
            for(int j = 0; j < engine.getGameField()[0].length;j++) {
              System.out.print(engine.getGameField()[i][j]);
            }
            System.out.println();
          }
        }
        character = tmp;

      } else if (character instanceof Fly) {
        System.out.println("Found a fly");
        Fly tmp = (Fly) character;
        int[] ints = processFly(tmp, event, x, y);
        if (ints.length == 1) {
          System.out.println("DEAD");
          new HomeworkPacket(-100, "Dead");
        } else {
          tmp.updateMap(engine.getGameField(tmp), ints[0], ints[1]);
          for(int i = 0; i< engine.getGameField().length; i++) {
            for(int j = 0; j < engine.getGameField()[0].length;j++) {
              System.out.print(engine.getGameField()[i][j]);
            }
            System.out.println();
          }
        }
        character = tmp;

      }

    }
    //Always send empty event back, null the queue
    HomeworkPacket resp = new HomeworkPacket(homeworkPacket.getId(), new Event(-1), character, homeworkPacket.getUsername());
    return resp;
  }

  private int[] processFrog(Frog frog, boolean doubleMove, Event event, int x, int y) {
    System.out.println("Processing frog movement");
    int[] ints = EventHandler.processEvent(event, doubleMove, x, y);
    System.out.println("INTS LENGTH: "+ints.length);
    System.out.println("0:"+ints[0]);
    System.out.println("1:"+ints[1]);
    return engine.validatePosition(x, y, frog, ints[0], ints[1]);
  }

  private int[] processFly(Fly fly, Event event, int x, int y) {
    System.out.println("Processing fly movement");
    int[] ints = EventHandler.processEvent(event, false, x, y);
    return engine.validatePosition(x, y, fly, ints[0], ints[1]);
  }


}
