package connection;

import java.io.Serializable;
import java.util.concurrent.ExecutorCompletionService;

/**
 * Created by Hans on 13/10/2015.
 */
public class SubmitterThread<T extends Serializable> implements Runnable {

  private final ExecutorCompletionService<T> EXECUTOR;

  public SubmitterThread(ExecutorCompletionService<T> executorService) {
    this.EXECUTOR = executorService;
  }

  public void submitTask(Messager<T> messager) {
    EXECUTOR.submit(messager, messager.getParameter());
  }

  @Override
  public void run() {
    System.out.println("ran runner");
    //BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());
  }
}
