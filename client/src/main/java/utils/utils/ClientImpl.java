package utils.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Created by Hans on 21/10/2015.
 */
public class ClientImpl implements Client {

  static final Logger LOGGER = LoggerFactory.getLogger(ClientImpl.class);

  private String ip;
  private int port;

  Socket socket;

  public ClientImpl() {
    this("localhost", 6666);
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
      } else {
        LOGGER.error("Unknown IOException", e);
      }
    }
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

}
