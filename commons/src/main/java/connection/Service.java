package connection;

import java.io.InputStream;
import java.io.Serializable;
import java.util.concurrent.*;

/**
 * Created by Hans on 14/10/2015.
 */
public class Service<T extends Serializable> {

  private ExecutorCompletionService<T> executorCompletionService;
  private SubmitterThread<T> submitterThread;
  private ReceiverThread<T> receiverThread;
  private int threadCount;
  private InputStream input;

  private BlockingQueue<T> blockingQueue;


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
    this.receiverThread = new ReceiverThread<>(executorCompletionService, input);
    new Thread(submitterThread).start();
    //DONT MAKE THIS VISIBLE, IT IS HIDDEN BECAUSE IT STARTS WITH SERVICE.
    new Thread(new ReceiverThread<>(executorCompletionService, input)).start();
    this.input = input;
    //No real correlation between values, just using threadCount
    this.blockingQueue = new LinkedBlockingQueue<>(threadCount);
  }

  public SubmitterThread<T> getSubmitterThread() {
    return this.submitterThread;
  }
}
