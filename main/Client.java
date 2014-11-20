package eecs285.proj4;

import java.net.Socket;

import eecs285.proj4.ClientThread;

public class Client
{
  private ClientThread clientThread;
  private ClientServerSocket clientSocket;
  
  public Client(ClientServerSocket inClientSocket)
  {
    clientSocket = inClientSocket;
    clientThread = new ClientThread(inClientSocket);
    clientThread.start();
  }
  
  //functions for closing sockets
  
}
