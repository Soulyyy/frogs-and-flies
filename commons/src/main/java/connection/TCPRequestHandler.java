package connection;

import java.util.Queue;

/**
 * Created by Hans on 14/10/2015.
 */
public class TCPRequestHandler<T> {

  private Queue<T> queue;
  private T defaultMessage;

  public TCPRequestHandler(Queue<T> queue, T message) {
    this.queue = queue;
    this.defaultMessage = message;
  }

  //Useless I think
  public TCPRequestHandler(Queue<T> queue) {
    this(queue, null);

  }

  public void poll() {


  }

  public void poll(T message) {

  }
}
