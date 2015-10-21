package connection;

import java.io.InputStream;
import java.io.Serializable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Hans on 14/10/2015.
 */
public class Service<T extends Serializable> {

  private ExecutorCompletionService<T> executorCompletionService;
  private SubmitterThread<T> submitterThread;
  private int threadCount;
  private InputStream input;


/*  public Service(int threadCount) {
    this.threadCount = threadCount;
    ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
    this.executorCompletionService = new ExecutorCompletionService<>(executorService);
    this.submitterThread = new SubmitterThread<>(executorCompletionService);
    new Thread(submitterThread).start();
    new Thread(new ReceiverThread<>(executorCompletionService)).start();
  }

  public Service() {
    this(10);
  }*/

  public Service(InputStream input) {
    this(input, 10);
  }

  public Service(InputStream input, int threadCount) {
    //Redundant I guess
    this.threadCount = threadCount;
    ExecutorService executorService = Executors.newFixedThreadPool(this.threadCount);
    this.executorCompletionService = new ExecutorCompletionService<>(executorService);
    this.submitterThread = new SubmitterThread<>(executorCompletionService);
    new Thread(submitterThread).start();
    new Thread(new ReceiverThread<>(executorCompletionService, input)).start();
    this.input = input;
  }

  public SubmitterThread<T> getSubmitterThread() {
    return this.submitterThread;
  }
}
