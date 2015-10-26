package utils;

import connection.Messager;
import connection.ReceiverThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Hans on 22/10/2015.
 */
public class ConnectionImpl<T extends Serializable> implements Connection<T> {

  static final Logger LOGGER = LoggerFactory.getLogger(ConnectionImpl.class);


  private Socket socket;

  ReceiverThread<T> receiverThread;

  //SubmitterThread<T> submitterThread;

  //private BlockingQueue<T> blockingQueue;

  //Only need this constructor, server socket has all data we care about
  public ConnectionImpl(ServerSocket serverSocket, BlockingQueue<T> blockingQueue, int id) {
    try {
      LOGGER.info("Trying to aquire Socket from {}", serverSocket);
      if (serverSocket == null) {
        LOGGER.warn("This server is null.");
      } else if (serverSocket.isClosed()) {
        LOGGER.warn("The serverSocket {} is closed", serverSocket);
        this.socket = null;
      } else {
        LOGGER.info("Trying to connect socket");
        this.socket = serverSocket.accept();
      }
    } catch (IOException e) {
      LOGGER.error("Failed to aquire socket, got IOException", e);
    }
    this.receiverThread = new ReceiverThread<>(this.socket, blockingQueue, id);
    new Thread(this.receiverThread).start();
    //this.submitterThread = new SubmitterThread<>(blockingQueue);
  }


  @Override
  public void close() {
    if (this.socket == null) {
      LOGGER.warn("The Socket is null on closing");
    } else if (this.socket.isClosed()) {
      LOGGER.warn("The Socket is closed");
    } else {
      try {
        this.socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  @Override
  public InputStream getInputStream() {
    try {
      return this.socket.getInputStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public OutputStream getOutputStream() {
    try {
      return this.socket.getOutputStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void submitMessage(T message) {
    Messager messager = new Messager<T>(message, this.getOutputStream());
    new Thread(messager).start();
  }

  @Override
  public Socket getSocket() {
    return this.socket;
  }


}
