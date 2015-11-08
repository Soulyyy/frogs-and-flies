import connection.HomeworkPacket;
import engine.Engine;
import engine.HomeworkProcessor;
import utils.Server;
import utils.ServerImpl;

//import connection.Service;

/**
 * Created by Hans on 11/10/2015.
 */
public class Main {

  public static void main(String[] args) {
    System.out.println("Hello, World!");
    Engine engine = new Engine(8, 8);
    Server<HomeworkPacket, HomeworkProcessor> server = new ServerImpl<>(() -> new HomeworkProcessor<>(engine));
    server.connect();
    int threadnumber = 0;
    while (true) {
      System.out.println(threadnumber);
      server.createSocket(threadnumber);
      threadnumber++;
    }

  }
}
