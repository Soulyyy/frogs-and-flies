package connection;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Created by Hans on 13/10/2015.
 */
public class SubmitterThread<T extends Serializable, U extends Processor<T>> implements Runnable {

  private final BlockingQueue<T> QUEUE;

  private Socket socket;

  //Synced hashmap with uid from homeworkpacket(initially 0, but need to change it)
  private ConcurrentHashMap<Integer, Socket> map;

  Supplier<U> supplier;

  public SubmitterThread(BlockingQueue<T> blockingQueue, Supplier<U> supplier) {
    this.supplier = Objects.requireNonNull(supplier);
    this.QUEUE = blockingQueue;
    if (map == null) {
      this.map = new ConcurrentHashMap<>();
    }
  }

  public SubmitterThread(BlockingQueue<T> blockingQueue, Socket socket, int id, Supplier<U> supplier) {
    this.supplier = Objects.requireNonNull(supplier);
    this.QUEUE = blockingQueue;
    this.socket = socket;
  }

  public void addSocket(int id, Socket socket) {
    map.put(id, socket);
  }

  public Socket removeSocket(int id) {
    return map.remove(id);
  }

  @Override
  public void run() {
    System.out.println("ran runner");
    while (true) {
      try {
        T input = QUEUE.take();
        U processor = supplier.get();
        //Processor processor = new HomeworkProcessor<>();
        //TODO shit cast, work with it
        Messager messager = null;
        //T resp = (T) processor.process(input);
        T resp = processor.process(input);
        if (resp instanceof HomeworkPacket) {
          System.out.println("TERXXXXX");
          HomeworkPacket homeworkPacket = (HomeworkPacket) resp;
          int id = homeworkPacket.getId();
          if (this.socket == null) {
            System.out.println(map);
            System.out.println(id);
            System.out.println(map.get(id));
            messager = new Messager<T>(resp, map.get(id).getOutputStream());
          } else {
            messager = new Messager<T>(resp, socket.getOutputStream());
          }
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
