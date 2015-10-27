package utils;

import connection.Messager;
import connection.Processor;
import connection.ReceiverThread;
import connection.SubmitterThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

/**
 * Created by Hans on 21/10/2015.
 */
public class ClientImpl<T extends Serializable, U extends Processor<T>> implements Client<T> {

  static final Logger LOGGER = LoggerFactory.getLogger(ClientImpl.class);

  private String ip;
  private int port;

  Socket socket;

  private BlockingQueue<T> blockingQueue;

  private ReceiverThread<T> receiverThread;

  private SubmitterThread<T, U> submitterThread;

  private Supplier<U> supplier;

  public ClientImpl(Supplier<U> supplier) {
    this.ip = "localhost";
    this.port = 6666;
    this.supplier = Objects.requireNonNull(supplier);
  }

  public ClientImpl(int port) {
    this("localhost", port);
  }

  public ClientImpl(String ip) {
    this(ip, 6666);
  }

  public ClientImpl(String ip, int port) {
    LOGGER.info("Parameters are {}, {}", ip, port);
    ip = (ip == null) ? "localhost" : ip;
    //TODO think about this but fixing unrealistic port
    port = (port == 0) ? 6666 : port;
    LOGGER.info("Initialized {} with {} and {}", ClientImpl.class, ip, port);
    this.ip = ip;
    this.port = port;
  }

  @Override
  public void connect() {
    try {
      this.socket = new Socket(ip, port);
    } catch (IOException e) {
      if (e instanceof ConnectException) {
        LOGGER.error("Failed to connect to server", e);
        return;
      } else {
        LOGGER.error("Unknown IOException", e);
        return;
      }
    }
    this.blockingQueue = new LinkedBlockingQueue<>(100);
    //0 because client doesn't know the id it has
    this.receiverThread = new ReceiverThread<>(socket, blockingQueue, 0);
    new Thread(receiverThread).start();
    this.submitterThread = new SubmitterThread<>(blockingQueue, socket, 0, supplier);
    new Thread(submitterThread).start();
  }

  @Override
  public void disconnect() {
    if (this.socket == null) {
      LOGGER.warn("Socket is null. Can't disconnect");
    } else if (this.socket.isClosed()) {
      LOGGER.warn("Trying to disconnect a closed socket.");
    } else {
      LOGGER.info("Closing socket {}", this.socket);
      try {
        this.socket.close();
      } catch (IOException e) {
        LOGGER.error("Failed to close socket {}", this.socket, e);
      }
    }


  }

  @Override
  public InputStream getInputStream() {
    if (this.socket == null) {
      LOGGER.warn("Trying to get an inputStream of a null socket.");
      return null;
    } else if (this.socket.isClosed()) {
      LOGGER.warn("Trying to get an inputStream of a closed socket.");
      return null;
    } else {
      try {
        LOGGER.info("Trying to get an inputStream of socket {}.", this.socket);
        return this.socket.getInputStream();
      } catch (IOException e) {
        LOGGER.error("Failed to get the inputStream of socket {}.", this.socket, e);
        return null;
      }
    }
  }

  @Override
  public OutputStream getOutputStream() {
    if (this.socket == null) {
      LOGGER.warn("Trying to get an outputStream of a null socket.");
      return null;
    } else if (this.socket.isClosed()) {
      LOGGER.warn("Trying to get an outputStream of a closed socket.");
      return null;
    } else {
      try {
        LOGGER.info("Trying to get an outputStream of socket {}.", this.socket);
        return this.socket.getOutputStream();
      } catch (IOException e) {
        LOGGER.error("Failed to get the outputstream of socket {}.", this.socket, e);
        return null;
      }
    }
  }

  @Override
  public void submitMessage(T message) {
    System.out.println(message.toString());
    Messager messager = new Messager<>(message, this.getOutputStream());
    new Thread(messager).start();
  }


  //TODO use utils
  @Override
  public T receiveMessage() {
    return null;
  }

  public ReceiverThread<T> getReceiverThread() {
    return receiverThread;
  }
}
