package eecs285.proj4;


import java.net.Socket;
import java.util.ArrayList;

public class ClientList
{
  public static final ArrayList<Client> clientList = new ArrayList<Client>();
  
  public static void addClient(Client cur)
  {
    clientList.add(cur);
  }
  
  public static void addClient(ClientServerSocket inClientSocket, String nickName)
  {
    clientList.add(new Client(inClientSocket, nickName));
  }
  
  public static void printArrayList(){
	  System.out.println("print client list: ");
	  System.out.println("Size of the list: " + clientList.size());
	  for(Client cur : clientList){
		  System.out.println(cur);
	  }
  }
  // TODO 
  // has bug in ClientList
  // print out twice  = = 
  
  
  //TODO removal procedures
  
  
  
}
