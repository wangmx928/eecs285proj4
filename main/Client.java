package eecs285.proj4;

import java.net.Socket;

import eecs285.proj4.ClientThread;

public class Client
{
  private ClientThread clientThread;
  private ClientServerSocket clientSocket;
  private String clientName;
  
  public Client(ClientServerSocket inClientSocket, String nickName, ClientForm inClientForm)
  {
    clientSocket = inClientSocket;
    clientName = nickName;
    
    // create an instance of ClientServerSocket
    //clientThread = new ClientThread(inClientSocket, clientName);
    // this will start the function run() in ClientThread.java
    (new ClientThread(inClientSocket, clientName, inClientForm)).start();
  }
  
  public String toString()
  {
    return clientName;
  }
  //functions for closing sockets
  
}
