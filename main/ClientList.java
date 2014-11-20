package eecs285.proj4;


import java.net.Socket;
import java.util.ArrayList;

public class ClientList
{
  private ArrayList <Client> clientList;
  
  public ClientList()
  {
    clientList = new ArrayList<Client>();
  }
 
  
  public void addClient(ClientServerSocket inClientSocket)
  {
    clientList.add(new Client(inClientSocket));
  }
  
  //TODO removal procedures
  
  
  
}
