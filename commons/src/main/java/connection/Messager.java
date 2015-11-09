package connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Created by Hans on 13/10/2015.
 */
public class Messager<T extends Serializable> implements Runnable {

  static final Logger LOGGER = LoggerFactory.getLogger(Messager.class);

  public T getParameter() {
    return parameter;
  }

  private T parameter;
  private OutputStream outputStream;

  public Messager(T parameter, OutputStream outputStream) {
    this.parameter = parameter;
    this.outputStream = outputStream;
  }
  public Messager(OutputStream outputStream) {
    this.outputStream = outputStream;
    this.parameter = null;
  }

  public void run() {
    //Id we don't have data, we create it
    if (parameter == null) {
      LOGGER.info("Evaluating non-existent object");
    }
    try {
      //TODO AWESOME HACK HANS! no, remove this, sync better
      Thread.sleep(100);
      ObjectOutputStream os = new ObjectOutputStream(outputStream);
      os.writeObject(parameter);

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
