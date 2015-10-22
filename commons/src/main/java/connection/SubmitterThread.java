package connection;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Hans on 13/10/2015.
 */
//TODO clean this class up
public class SubmitterThread<T extends Serializable> implements Runnable {

  //private final ExecutorCompletionService<T> EXECUTOR;
  private final BlockingQueue<T> QUEUE;

  public SubmitterThread(BlockingQueue<T> blockingQueue) {
    this.QUEUE = blockingQueue;
  }
/*
  public SubmitterThread(ExecutorCompletionService<T> executorService) {
    this.EXECUTOR = executorService;
  }*/

  //Why the hell is this method here?
/*  public void submitTask(Messager<T> messager) {

    EXECUTOR.submit(messager, messager.getParameter());
  }*/

  @Override
  public void run() {
    System.out.println("ran runner");
    while(true) {
      try {
        T resp = QUEUE.take();
        //Now send to processor
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    //BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());
  }
}
