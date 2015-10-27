package utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hans on 22/10/2015.
 */
public interface Server<T extends Serializable, U> {

  public void connect();

  public void close();

  public Connection<T> createSocket(int id);

  public List<Connection> getConnections();

}
