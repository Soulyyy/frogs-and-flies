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
        HomeworkPacket homeworkPacket = (HomeworkPacket) object;
        if (homeworkPacket.getId() == -1) {
          homeworkPacket.setId(this.id);
        }
        System.out.println(homeworkPacket.toString());
        //TODO reimplement
        System.out.println("PUTTING THIS GUY:\n"+homeworkPacket);
        QUEUE.put((T) homeworkPacket);
      } catch (IOException | ClassNotFoundException | InterruptedException e) {
        e.printStackTrace();
        break;
        //TODO for client break other way
      }
    }
  }
}
