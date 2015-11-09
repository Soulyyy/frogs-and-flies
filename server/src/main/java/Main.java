import connection.HomeworkPacket;
import engine.Engine;
import engine.HomeworkProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Server;
import utils.ServerImpl;

/**
 * Created by Hans on 11/10/2015.
 */
public class Main {

  static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    Engine engine = new Engine(8, 8);
    Server<HomeworkPacket, HomeworkProcessor> server = new ServerImpl<>(() -> new HomeworkProcessor(engine));
    server.connect();
    int threadnumber = 0;
    while (true) {
      LOGGER.info("Current user count : {}", threadnumber);
      server.createSocket(threadnumber);
      threadnumber++;
    }

  }
}
