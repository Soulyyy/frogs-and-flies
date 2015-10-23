package utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Created by Hans on 22/10/2015.
 */

//TODO Think whether handling connections should belong in commons(Client is also a type of connection)
public interface Connection<T extends Serializable> {

  public void close();

  public InputStream getInputStream();

  public OutputStream getOutputStream();

  //TODO think about this, it is just to gain access to Service messaging(sending message outside handler)
  public void submitMessage(T message);

}
