package utils;

import connection.HomeworkPacket;
import connection.Messager;
import connection.Service;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Hans on 11/10/2015.
 */
public class Main {

  //May need listener tasks as well(to submitter tasks)

  public static void main(String[] args) {
    System.out.println("Client working!");

    try {
      Socket socket = new Socket("localhost", 6666);
      BufferedOutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
      byte[] buffer = new byte[8092];
      String s = "terex";
      //outputStream.write(s.getBytes());
      //outputStream.flush();
      //String data = new String(buffer).trim();
      //System.out.println(data);
      buffer = new byte[8092];
      Service<HomeworkPacket> service = new Service<>(socket.getInputStream(), 150);
      HomeworkPacket homeworkPacket = new HomeworkPacket(1, "terefsdfsdfsd");
      service.getSubmitterThread().submitTask(new Messager<>(homeworkPacket, socket.getOutputStream()));
      //service.getSubmitterThread().submitTask(new Messager<>());
    } catch (IOException e) {
      e.printStackTrace();
    }




    //This is for load testing at the moment, don't delete
/*    for(int i = 0; i<1000;i++) {
      service.getSubmitterThread().submitTask(new Messager<>("ping"+i));
    }*/


  }
}
