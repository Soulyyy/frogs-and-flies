package engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hans on 26/10/2015.
 */
//Class to contain the engine(gives the processor for the game)
public class Engine {

  static final Logger LOGGER = LoggerFactory.getLogger(Engine.class);

  List<Character> dead;
  //This field contains the whole game field
  //private static int[][] gameField;

  private static Character[][] game;

  public Engine(int M, int N) {
    game = new Character[M][N];
    dead = new LinkedList<>();
  }

  //We have the x and y coordinates, why not use them
  //This is part of the main loop
  //Check with array length
  public synchronized int[] validatePosition(int m, int n, Character character, int x, int y) {
    if (m < 0 || n < 0 || m >= game[0].length || n >= game.length) {
      return new int[]{y, x};
    }
    if (x < 0 || y < 0 || x >= game[0].length || y >= game.length) {
      x = m;
      y = n;
    }
    //Ad hoc fix, dont remove from list, always dead, always blocked
    if (dead.contains(character)) {
      for (int i = 0; i < game.length; i++) {
        for (int j = 0; j < game[0].length; j++) {
          if (game[i][j] != null && game[i][j].equals(character)) {
            game[i][j] = null;
            LOGGER.warn("SOMETHING DIED!");
            break;
          }
        }
      }
      return new int[]{-666};
    }
    if (character instanceof Frog) {
      x = ((Frog) character).x;
      y = ((Frog) character).y;

    } else if (character instanceof Fly) {
      x = ((Fly) character).x;
      y = ((Fly) character).y;
    }
    LOGGER.info("Trying to move from ({} , {}) to ({} , {})" + x, y, n, m);
    if (character instanceof Frog && !((Frog) character).checkAlive()) {
      dead.add(character);
    }
    if (m < game.length && n < game[0].length && m >= 0 && n >= 0) {
      Character newSpot = game[m][n];
      if (newSpot != null) {
        if (newSpot instanceof Fly && character instanceof Frog) {
          dead.add(newSpot);
          //newSpot = null;
          game[m][n] = null;
          character.updateScore();

        } else if (newSpot instanceof Frog && character instanceof Fly) {
          dead.add(character);
          character = null;
          newSpot.updateScore();
          return new int[]{-666};
        }
      }
      LOGGER.info("Changing values");
      LOGGER.info("x is : {} and y is : {}", x, y);
      LOGGER.info("n is : {} and m is : {}", n, m);
      //Walk through walls, a feature imo
      System.out.println(m <= 0);
      System.out.println(m);
      game[x][y] = null;
      game[m][n] = character;
    } else {
      System.out.println("BORKEN REQUEST LALALALA");
    }
    System.out.println("New position is" + m + " " + n);
    return new int[]{n, m};
  }

  public synchronized int[][] getGameField() {
    int[][] resp = new int[game.length][game[0].length];
    for (int i = 0; i < game.length; i++) {
      for (int j = 0; j < game[0].length; j++) {
        //gameField[i][j] = 1;
        if (game[i][j] == null) {
          resp[i][j] = 1;
        } else if (game[i][j] instanceof Frog) {
          resp[i][j] = 3;
        } else if (game[i][j] instanceof Fly) {
          resp[i][j] = 4;
        }
      }
    }
    return resp;
  }

  public synchronized int[][] getGameField(Character character) {
    int[][] resp = new int[game.length][game[0].length];
    for (int i = 0; i < game.length; i++) {
      for (int j = 0; j < game[0].length; j++) {
        //gameField[i][j] = 1;
        if (game[i][j] == null) {
          resp[i][j] = 1;
        } else if (game[i][j].equals(character)) {
          resp[i][j] = 2;
        } else if (game[i][j] instanceof Frog) {
          resp[i][j] = 3;
        } else if (game[i][j] instanceof Fly) {
          resp[i][j] = 4;
        }
      }
    }
    return resp;
  }

  //TODO Remove, quick hack
  public Character[][] getGame() {
    return game;
  }

  public void addCharacter(Character character, int x, int y) {
    game[y][x] = character;
  }
}
