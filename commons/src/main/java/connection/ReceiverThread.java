package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Hans on 13/10/2015.
 */
public class ReceiverThread<T extends Serializable> implements Runnable {

/*  private final ExecutorCompletionService<T> EXECUTOR;
  private final InputStream INPUT;
  //private final Socket SOCKET;*/

  private final BlockingQueue<T> QUEUE;
  private final Processor<T> PROCESSOR;

  public ReceiverThread(Processor<T> processor, BlockingQueue<T> blockingQueue) {
    this.QUEUE = blockingQueue;
    this.PROCESSOR = processor;
  }

 /* public ReceiverThread(ExecutorCompletionService<T> executorService, InputStream in) {
    this.EXECUTOR = executorService;
    this.INPUT = in;
    //this.SOCKET = socket;
  }*/

  @Override
  public void run() {
    while (true) {
      try {
        ObjectInputStream objectInputStream = new ObjectInputStream(PROCESSOR.getIntputStream());
      } catch (IOException e) {
        e.printStackTrace();
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
