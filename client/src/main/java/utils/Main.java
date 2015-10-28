package utils;

import connection.HomeworkPacket;

import java.io.BufferedOutputStream;

//import connection.Service;

/**
 * Created by Hans on 11/10/2015.
 */
public class Main {

  //May need listener tasks as well(to submitter tasks)

  public static void main(String[] args) {
    Client<HomeworkPacket> client = new ClientImpl<>(ClientProcessor::new);
    client.connect();
    System.out.println("Client working!");

    BufferedOutputStream outputStream = new BufferedOutputStream(client.getOutputStream());
    client.submitMessage(new HomeworkPacket(-1, "start"));
    while (true) {
    }



  }
}
