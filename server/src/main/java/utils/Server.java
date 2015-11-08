package utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hans on 22/10/2015.
 */
public interface Server<T extends Serializable, U> {

  void connect();

  void close();

  Connection createSocket(int id);

  List<Connection> getConnections();

}
