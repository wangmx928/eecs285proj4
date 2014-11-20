package eecs285.proj4;
/**
 * Thread class for all clients.
 * Each client added has thier own thread
 * activated with
 * ClientThread()
 * ClientThread.start()
 *
 */
public class ClientThread extends Thread
{
  private ClientServerSocket clientSocket;
  public ClientThread(ClientServerSocket inClientSocket)
  {
    clientSocket = inClientSocket;
  }

  public void run()
  {
    //setup vars
    while (true)
    {
      String inData = clientSocket.recvString();
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
      else if (action == ConnectionActionTypeEnum.VOTE)
      {
        //add to vote tally
      }
      else 
      {
        //TODO error
      }
      
    }
  }

}
