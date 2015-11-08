package utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * Created by Hans on 22/10/2015.
 */

//TODO Think whether handling connections should belong in commons(Client is also a type of connection)
public interface Connection {

  OutputStream getOutputStream();

  Socket getSocket();

  void close();

}
