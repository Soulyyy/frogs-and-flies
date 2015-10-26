package connection;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Hans on 13/10/2015.
 */
public class SubmitterThread<T extends Serializable> implements Runnable {

  private final BlockingQueue<T> QUEUE;

  private Socket socket;

  //Synced hashmap with uid from homeworkpacket(initially 0, but need to change it)
  private HashMap<Integer, Socket> map;

  public SubmitterThread(BlockingQueue<T> blockingQueue) {
    this.QUEUE = blockingQueue;
    if (map == null) {
      this.map = new HashMap<>();
    }
  }

  public void addSocket(int id, Socket socket) {
    map.put(id, socket);
  }

  public SubmitterThread(BlockingQueue<T> blockingQueue, Socket socket, int id) {
    if (map == null) {
      map = new HashMap<>();
    }
    this.QUEUE = blockingQueue;
    map.put(id, socket);
    this.socket = socket;
  }

  @Override
  public void run() {
    System.out.println("ran runner");
    while (true) {
      try {
        T input = QUEUE.take();
        Processor processor = new HomeworkProcessor<>();
        //TODO shit cast, work with it
        Messager messager = null;
        T resp = (T) processor.process(input);
        if (resp instanceof HomeworkPacket) {
          HomeworkPacket homeworkPacket = (HomeworkPacket) resp;
          int id = homeworkPacket.id;
          System.out.println(map);
          System.out.println(map.get(id));
          messager = new Messager<T>(resp, map.get(id).getOutputStream());
        }
        if (messager != null) {
          new Thread(messager).start();
        }
      } catch (InterruptedException | IOException e) {
        e.printStackTrace();
      }
    }
  }
}
