package utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Created by Hans on 21/10/2015.
 */
public interface Client<T extends Serializable> {

  void connect();

  void disconnect();

  InputStream getInputStream();

  OutputStream getOutputStream();

  void submitMessage(T message);

  T receiveMessage();

}
