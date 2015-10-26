package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Hans on 13/10/2015.
 */
public class ReceiverThread<T extends Serializable> implements Runnable {

/*  private final ExecutorCompletionService<T> EXECUTOR;
  private final InputStream INPUT;
  //private final Socket SOCKET;*/

  private final BlockingQueue<T> QUEUE;
  //private final InputStream INPUTSTREAM;
  private final Socket SOCKET;

  int id;

  public ReceiverThread(Socket socket, BlockingQueue<T> blockingQueue, int id) {
    this.QUEUE = blockingQueue;
    //this.PROCESSOR = processor;
    this.SOCKET = socket;
    //this.type = clazz;
    this.id = id;
  }


  public void insertToQueue(T message) {
    try {
      QUEUE.put(message);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    while (true) {
      try {
        ObjectInputStream objectInputStream = new ObjectInputStream(SOCKET.getInputStream());
        Object object = objectInputStream.readObject();
        System.out.println("THERE");
        HomeworkPacket homeworkPacket = (HomeworkPacket) object;
        homeworkPacket.id = this.id;
        System.out.println(homeworkPacket.toString());
        //TODO reimplement
        QUEUE.put((T) homeworkPacket);
      } catch (IOException | ClassNotFoundException | InterruptedException e) {
        e.printStackTrace();
        break;
        //TODO for cleint break other way
      }
    }
    /*try {
      while (true) {
        Future<T> future = EXECUTOR.take();
        System.out.println("Got something  Iguess");
        T t = future.get();
        System.out.println(t);
        if (t == null) {
          System.out.println("No response");
        } else {
          System.out.println(t.toString());
        }
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }*/
  }
}
