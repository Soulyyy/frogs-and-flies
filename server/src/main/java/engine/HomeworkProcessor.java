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
      System.out.println("Message is start");
      string = "started";
      return new HomeworkPacket(message.getId(), string);
    }
    System.out.println("Message is not start");
    return processHomeworkPacket(message);
  }


  public HomeworkPacket processHomeworkPacket(HomeworkPacket homeworkPacket) {
    System.out.println("BEFORE PROCESSING THE PACKET IS:\n" + homeworkPacket);
    Character character = (homeworkPacket.getCharacter() == null) ? new Spectator(this.engine.getGameField()) : homeworkPacket.getCharacter();
    System.out.println("AFTER CHARACTER CHECK:\n" + homeworkPacket);
    int x = homeworkPacket.getX();
    int y = homeworkPacket.getY();
    if (homeworkPacket.getEvent() != null) {
      Event event = homeworkPacket.getEvent();
      if (character instanceof Spectator) {
        System.out.println("SPECTATOR DONT CARE");

        //Choose frog
        if (event.getFirstEvent() == 5) {
          System.out.println("Created new frog");
          //character = new Frog(this.engine.getGameField(), 0, 0);
          while (true) {
            x = (int) Math.round(Math.random() * (engine.getGameField()[0].length - 1));
            y = (int) Math.round(Math.random() * (engine.getGameField().length - 1));
            if (engine.getGame()[y][x] == null) {
              character = new Frog(this.engine.getGameField(), x, y);
              engine.addCharacter(character, x, y);
              break;
            }
          }
        }
        //Choose fly
        else if (event.getFirstEvent() == 6) {
          //Check for a free spot to start!
          System.out.println("Created new fly");
          //character = new Fly(this.engine.getGameField(), 0, 0);
          while (true) {
            x = (int) Math.round(Math.random() * (engine.getGameField()[0].length - 1));
            y = (int) Math.round(Math.random() * (engine.getGameField().length - 1));
            if (engine.getGame()[y][x] == null) {
              character = new Fly(this.engine.getGameField(), x, y);
              engine.addCharacter(character, x, y);
              break;
            }
          }
/*          x = 0;
          y = 0;*/
        } else {
          ((Spectator) character).updateMap(engine.getGameField());
          System.out.println("not a valid event");
        }
      } else if (character instanceof Frog) {
        x = ((Frog) character).x;
        y = ((Frog) character).y;
        System.out.println("ENTERING FROG:\n" + homeworkPacket);
        System.out.println("Found a frog");
        boolean doubleMove = event.getEvents()[0] == event.getEvents()[1];

        Frog tmp = (Frog) character;
        int[] ints = processFrog(tmp, doubleMove, event, x, y);
        if (ints.length == 1) {
          System.out.println("DEAD");
          new HomeworkPacket(-100, "Dead");
        } else {
          x = ints[1];
          y = ints[0];
          tmp.updateMap(engine.getGameField(tmp), ints[0], ints[1]);
          for (int i = 0; i < engine.getGameField().length; i++) {
            for (int j = 0; j < engine.getGameField()[0].length; j++) {
              System.out.print(engine.getGameField()[i][j]);
            }
            System.out.println();
          }
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
        System.out.println("ENTERING FLY:\n" + homeworkPacket);
        System.out.println("Found a fly");
        Fly tmp = (Fly) character;
        System.out.println("X AND Y BEFORE PARSE: " + x + " " + y);
        int[] ints = processFly(tmp, event, x, y);
        x = ints[1];
        y = ints[0];
        if (ints.length == 1) {
          System.out.println("DEAD");
          new HomeworkPacket(-100, "Dead");
        } else {
          tmp.updateMap(engine.getGameField(tmp), y, x);
          for (int i = 0; i < engine.getGameField().length; i++) {
            for (int j = 0; j < engine.getGameField()[0].length; j++) {
              System.out.print(engine.getGameField()[i][j]);
            }
            System.out.println();
          }
        }
        character = tmp;
        ((Fly) character).x = x;
        ((Fly) character).y = y;
        homeworkPacket.setX(x);
        homeworkPacket.setY(y);
        homeworkPacket.setEvent(new Event(-1));
        System.out.println("FLY PACKET IS : \n" + homeworkPacket);
        return homeworkPacket;
        //return new HomeworkPacket(homeworkPacket.getId(), new Event(-1), character, homeworkPacket.getUsername());

      }

    }
    //Always send empty event back, null the queue
    System.out.println("SET X AS : " + x + " and Y as : " + y);
    HomeworkPacket resp = new HomeworkPacket(homeworkPacket.getId(), new Event(-1), character, homeworkPacket.getUsername());

    return resp;
  }

  private int[] processFrog(Frog frog, boolean doubleMove, Event event, int x, int y) {
    System.out.println("Processing frog movement");
    int[] ints = EventHandler.processEvent(event, doubleMove, x, y);
    System.out.println("INTS LENGTH: " + ints.length);
    System.out.println("0:" + ints[0]);
    System.out.println("1:" + ints[1]);
    return engine.validatePosition(ints[0], ints[1], frog, x, y);
  }

  private int[] processFly(Fly fly, Event event, int x, int y) {
    System.out.println("Processing fly movement");
    int[] ints = EventHandler.processEvent(event, false, x, y);
    return engine.validatePosition(ints[0], ints[1], fly, x, y);
  }


}
