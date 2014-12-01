package eecs285.proj4;


import java.net.Socket;
import java.util.ArrayList;

public class ClientList
{
  public static final ArrayList<String> clientList = new ArrayList<String>();

  public static void addClient(String cur)
  {
    clientList.add(cur);
  }

  public static void printArrayList()
  {
    System.out.println("print client list: ");
    System.out.println("Size of the list: " + clientList.size());
    for( String cur : clientList )
    {
      System.out.println(cur);
    }
  }

  // TODO removal procedures


}
