package utils;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Hans on 22/10/2015.
 */

//TODO Think whether handling connections should belong in commons(Client is also a type of connection)
public interface Connection {

  public void close();

  public InputStream getInputStream();

  public OutputStream getOutputStream();

}
