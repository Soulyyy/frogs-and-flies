package connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Created by Hans on 13/10/2015.
 */
public class Messager<T extends Serializable> implements Runnable {

  public T getParameter() {
    return parameter;
  }

  private T parameter;
  private OutputStream outputStream;
  private T response = null;

  public Messager(T parameter, OutputStream outputStream) {
    this.parameter = parameter;
    this.outputStream = outputStream;
  }

  /*  public Messager() {
      this(null,null);
    }*/
  public Messager(OutputStream outputStream) {
    this.outputStream = outputStream;
    this.parameter = null;
  }

  public void run() {
    //Id we don't have data, we create it
    if (parameter == null) {
      System.out.println("evaling non-existent object");
      Object object = "TERE";
    }
    //BufferedInputStream is = new BufferedInputStream(inputStream);
    try {
      //TODO AWESOME HACK HANS! no, remove this, sync better
      Thread.sleep(500);
      ObjectOutputStream os = new ObjectOutputStream(outputStream);
      os.writeObject(parameter);

/*      if(T instanceof String) {
        
      }*/
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    //System.out.println(parameter.toString());
  }
}
