package connection;

import java.io.Serializable;

/**
 * Created by Hans on 22/10/2015.
 */
public interface Processor<T extends Serializable> {

  T process(T message);

}
