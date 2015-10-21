import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Hans on 11/10/2015.
 */
public class Main {

  //Datastream vs object*Stream should migrate to data, serialize and deserialize manually

  public static void main(String[] args) {
    System.out.println("Hello, World!");
    try {
      ServerSocket serverSocket = new ServerSocket(6666);
      //Register the socket in a list, create thread for each
      Socket socket = serverSocket.accept();
      BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());
      BufferedOutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
      byte[] buffer = new byte[8092];
      while (true) {
        try {
          System.out.println("Enter try");
          ObjectInputStream is = new ObjectInputStream(inputStream);
          System.out.println("Enter sleep");
          Thread.sleep(1500);

          Object object = is.readObject();
          if (object instanceof HomeworkPacket) {
            System.out.println("Homework");
          }
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        //inputStream.read(buffer);
        String data = new String(buffer).trim();
        System.out.println(data);
        if ("terex".equals(data)) {
          System.out.println("Entered");
          outputStream.write("accept".getBytes());
          outputStream.flush();
        }
        buffer = new byte[8092];
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    //System.out.println("I AM HERE");
  }
}
