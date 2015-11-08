package utils;

import connection.Processor;
import connection.SubmitterThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

/**
 * Created by Hans on 22/10/2015.
 */

//Server receives, each connection submits, has type for now
public class ServerImpl<T extends Serializable, U extends Processor<T>> implements Server<T, U> {

  static final Logger LOGGER = LoggerFactory.getLogger(ServerImpl.class);

  private int port;

  ServerSocket serverSocket;

  List<Connection> connections;

  Supplier<U> supplier;

  //Server owns the processing queue, no need for type here I think
  private BlockingQueue<T> blockingQueue;

  private SubmitterThread<T, U> submitterThread;

  public ServerImpl(Supplier<U> supplier) {
    this.supplier = Objects.requireNonNull(supplier);
    this.port = 6666;
  }

  public ServerImpl(int port, Supplier<U> supplier) {
    this.supplier = Objects.requireNonNull(supplier);
    this.port = port;

  }


  @Override
  public void connect() {
    //Link them because no array access needed, only iteration
    connections = new LinkedList<>();
    blockingQueue = new LinkedBlockingQueue<>(100);
    try {
      //This operation blocks according to the javadoc
      serverSocket = new ServerSocket(port);
    } catch (IOException e) {
      LOGGER.error("IOException on opening ServerSocket, exiting.", e);
      System.exit(1);
    }
    this.submitterThread = new SubmitterThread<>(blockingQueue, supplier);
    new Thread(submitterThread).start();

  }

  @Override
  public void close() {
    try {
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Connection createSocket(int id) {
    Connection connection = new ConnectionImpl<>(this.serverSocket, blockingQueue, id);
    this.submitterThread.addSocket(id, connection.getSocket());
    LOGGER.debug("Connection {} is returned from socket", connection);
    return connection;
  }

  @Override
  public List<Connection> getConnections() {
    return connections;
  }
}
