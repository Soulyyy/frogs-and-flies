package connection;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;

/**
 * Created by Hans on 13/10/2015.
 */
public class ReceiverThread<T> implements Runnable {

  private final ExecutorCompletionService<T> EXECUTOR;
  private final InputStream INPUT;
  //private final Socket SOCKET;

  public ReceiverThread(ExecutorCompletionService<T> executorService, InputStream in) {
    this.EXECUTOR = executorService;
    this.INPUT = in;
    //this.SOCKET = socket;
  }

  @Override
  public void run() {
    try {
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
    }
  }
}