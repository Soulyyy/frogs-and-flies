package connection;

import java.io.Serializable;

/**
 * Created by Hans on 22/10/2015.
 */
public interface Processor<T extends Serializable> extends Runnable {
  //Want this getter to force having an inputStream on the processor level
  void getIntputStream();

}
