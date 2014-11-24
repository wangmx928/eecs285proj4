package eecs285.proj4;

import javax.swing.DefaultListModel;

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
	clientSocket.startClient(clientThreadName);
    //setup variables
    while (true)
    {
      String inData = clientSocket.recvString();
      // for debugging information
      System.out.println("clientSocket recvString: " + inData);
      

      String action = "";
      int i = 0;
      while(inData.charAt(i) != '\n')
      {
        action = action + inData.charAt(i);
        ++i;
      }
          
      if (action == "LIST_REQUEST")
      {
        sendPlaylist();
        sendRequestList();
      }
      else if (action == "VOTE"){
        String inSong = ""; 
        while(inData.charAt(i) != '\n')
        {
          inSong = inSong + inData.charAt(i);
          ++i;
        }
        DefaultListModel<Song> songRequestList = ManagerForm.getLibraryListModel();
        
        for(int j = 0; j < songRequestList.getSize(); j++){
        	Song curSong = songRequestList.getElementAt(j);
        	if (inSong == curSong.playlistString())
            {
              curSong.addOneVote();
              break;
            }
        }
        ManagerForm.updateRequestList(songRequestList);
        
        sendPlaylist();
        sendRequestList();
      }
      else {
    	// for debugging information
    	System.out.println("Thread " + clientThreadName + " error");
      }
      
    }
  }


  private void sendRequestList()  
  { 
    DefaultListModel<Song> requestListModel = ManagerForm.getLibraryListModel();
    String toSend = "REQUESTS: \n";
    
    for(int i = 0; i < requestListModel.getSize(); i++)
    {
      Song curSong = requestListModel.elementAt(i);
      toSend = toSend + curSong.toString() + '\n';
    }
    clientSocket.sendString(toSend);
  }
  private void sendPlaylist()
  {
	  DefaultListModel<Song> playlistListModel = ManagerForm.getPlaylistModel();

    String toSend = "PLAYLIST: \n";
    for(int i = 0; i < playlistListModel.getSize(); i++)
    {
      Song curSong = playlistListModel.elementAt(i);
      toSend = toSend + curSong.playlistString() + '\n';
    }
    
    clientSocket.sendString(toSend);
    return;
    
  }
  
}
