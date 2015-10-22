package utils;

/**
 * Created by Hans on 22/10/2015.
 */
public interface Server {

  public void connect();

  public void close();

  public Connection createSocket();

}
