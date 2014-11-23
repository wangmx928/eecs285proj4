package eecs285.proj4;
/**
 * Thread class for all clients.
 * Each client added has their own thread
 * activated with
 * ClientThread()
 * ClientThread.start()
 *
 */
public class ClientThread extends Thread
{
  // thread for cur client
  private String clientThreadName;
  private ClientServerSocket clientSocket;
  
  public ClientThread(ClientServerSocket inClientSocket, String clientName)
  {
	// get the clientName which can be shown on GUI
	clientThreadName = clientName;
	// for Debug information: client input Name
	System.out.println("Creating " + clientThreadName);
	
	// initialize the clientSocket
    clientSocket = inClientSocket;
    
  }

  public void run()
  {
	// for debugging information
	System.out.println("Running " + clientThreadName);
	clientSocket.startClient();
    //setup variables
    while (true)
    {
      String inData = clientSocket.recvString();
      // for debugging information
      System.out.println("clientSocket recvString: " + inData);
      
      //TODO parse string
      ConnectionActionTypeEnum action = null;
          
      if (action == ConnectionActionTypeEnum.LIST_REQUEST)
      {
        //send list
      }
      else if (action == ConnectionActionTypeEnum.SONG_REQUEST)
      {
        //ask for approval
      }
      else if (action == ConnectionActionTypeEnum.VOTE){
        //add to vote tally
      }
      else {
    	// for debugging information
    	System.out.println("Thraed " + clientThreadName + " error");
      }
      
    }
  }

}
