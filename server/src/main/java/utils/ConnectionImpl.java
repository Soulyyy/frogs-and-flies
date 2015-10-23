package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Hans on 22/10/2015.
 */
public class ConnectionImpl<T> implements Connection {

  static final Logger LOGGER = LoggerFactory.getLogger(ConnectionImpl.class);


  private Socket socket;

  //Only need this constructor, server socket has all data we care about
  public ConnectionImpl(ServerSocket serverSocket) {
    try {
      LOGGER.info("Trying to aquire Socket from {} with String description {}", serverSocket);
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
  }


  @Override
  public void close() {
    if(this.socket == null) {
      LOGGER.warn("The Socket is null on closing");
    }else if(this.socket.isClosed()) {
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
      InputStream inputStream = this.socket.getInputStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public OutputStream getOutputStream() {
    try {
      OutputStream outputStream = this.socket.getOutputStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void submitMessage(T message) {

  }


}
