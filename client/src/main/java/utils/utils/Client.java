package utils.utils;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Hans on 21/10/2015.
 */
public interface Client {

  public void connect();

  public void disconnect();

  public InputStream getInputStream();

  public OutputStream getOutputStream();



}
