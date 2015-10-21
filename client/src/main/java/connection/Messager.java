package connection;

import java.io.*;

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
    byte[] buffer = new byte[8092];
    try {
      ObjectOutputStream os = new ObjectOutputStream(outputStream);
      os.writeObject(parameter);

/*      if(T instanceof String) {
        
      }*/
    } catch (IOException e) {
      e.printStackTrace();
    }
    //System.out.println(parameter.toString());
  }
}
