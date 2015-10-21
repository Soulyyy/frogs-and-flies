package utils;

import connection.HomeworkPacket;
import connection.Messager;
import connection.Service;
import utils.utils.Client;
import utils.utils.ClientImpl;

import java.io.BufferedOutputStream;

/**
 * Created by Hans on 11/10/2015.
 */
public class Main {

  //May need listener tasks as well(to submitter tasks)

  public static void main(String[] args) {
    Client client = new ClientImpl();
    client.connect();
    System.out.println("Client working!");
    BufferedOutputStream outputStream = new BufferedOutputStream(client.getOutputStream());

/*      Socket socket = new Socket("localhost", 6666);
      byte[] buffer = new byte[8092];
      String s = "terex";*/
      //outputStream.write(s.getBytes());
      //outputStream.flush();
      //String data = new String(buffer).trim();
      //System.out.println(data);
      //buffer = new byte[8092];
      Service<HomeworkPacket> service = new Service<>(client.getInputStream(), 150);
      HomeworkPacket homeworkPacket = new HomeworkPacket(1, "terefsdfsdfsd");
      service.getSubmitterThread().submitTask(new Messager<>(homeworkPacket, client.getOutputStream()));
      //service.getSubmitterThread().submitTask(new Messager<>());


    //This is for load testing at the moment, don't delete
/*    for(int i = 0; i<1000;i++) {
      service.getSubmitterThread().submitTask(new Messager<>("ping"+i));
    }*/


  }
}
