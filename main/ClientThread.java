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
    sendList();
  }

  public void run()
  {
    //setup vars
    while (true)
    {
      String inData = clientSocket.recvString();
      String action;
      int i = 0;
      while(inData.at(i) != :)
      {
        action = action + inData.at(i);
        ++i;
      }
      
          
      if (action == ConnectionActionTypeEnum.LIST_REQUEST)
      {
        sendList();
      }
      else if (action == ConnectionActionTypeEnum.VOTE)
      {
        String songTitle;
        while(inData.at(i) != :)
        {
          songTitle = songTitle + inData.at(i);
          ++i;
        }
        String artist;
        while(inData.at(i) != :)
        {
          artist = artist + inData.at(i);
          ++i;
        }
        //TODO
        DefaultListModel<String> songRequestList = ManagerForm.getRequestList();
        songToUpdate = songRequestList.find(Song);
        songToUpdate.vote++;
        ManagerForm.updateRequestList(songRequestList);
        
        sendList();
        
        
      }
      else 
      {
        //TODO error
      }
      
    }
  }
  
  
  private void sendList()
  {
    DefaultListModel<String> playlistListModel = ManagerForm.getPlaylist();
    DefaultListModel<String> requestListModel = ManagerForm.getRequestList();
    String toSend("PLAYLIST: ");
    for(currSong : playlistListModel)
    {
      toSend = toSend + currSong.toString();
    }
    
    toSend = toSend + " REQUESTS: ";
      
    for(currRequest : requestListModel)
    {
      toSend = toSend + currRequest.toString();
    }
    clientSocket.sendStr(toSend);
    return;
    
  }

}
