package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hans on 22/10/2015.
 */
public class ServerImpl implements Server {

  static final Logger LOGGER = LoggerFactory.getLogger(ServerImpl.class);

  private int port;

  ServerSocket serverSocket;

  List<Connection> connections;

  public ServerImpl() {
    this(6666);
  }

  public ServerImpl(int port) {
    this.port = port;
  }


  @Override
  public void connect() {
    //Link them because no array access needed, only iteration
    connections = new LinkedList<>();
    try {
      //This operation blocks according to the javadoc
      serverSocket = new ServerSocket(port);
    } catch (IOException e) {
      LOGGER.error("IOException on opening ServerSocket, exiting.", e);
      System.exit(1);
    }

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
  public Connection createSocket() {
    Connection connection = new ConnectionImpl(null);
    LOGGER.debug("Connection {} is returned from socket", connection);
    return connection;
  }

  @Override
  public List<Connection> getConnections() {
    return connections;
  }
}
