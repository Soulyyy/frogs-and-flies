import connection.HomeworkPacket;
import utils.Server;
import utils.ServerImpl;

//import connection.Service;

/**
 * Created by Hans on 11/10/2015.
 */
public class Main {

  //Datastream vs object*Stream should migrate to data, serialize and deserialize manually

  public static void main(String[] args) {
    System.out.println("Hello, World!");
    Server<HomeworkPacket> server = new ServerImpl<>();
    server.connect();
    int threadnumber = 0;
    while (true) {
      System.out.println(threadnumber);
      server.createSocket(threadnumber);
      //Connection<HomeworkPacket> con = server.createSocket();
      //con.submitMessage(new HomeworkPacket(threadnumber, "start"));
      threadnumber++;
    }

  }
}
