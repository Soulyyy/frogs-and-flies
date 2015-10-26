package utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Created by Hans on 21/10/2015.
 */
public interface Client<T extends Serializable> {

  public void connect();

  public void disconnect();

  public InputStream getInputStream();

  public OutputStream getOutputStream();

  public void submitMessage(T message);

  public T receiveMessage();



}
