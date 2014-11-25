package eecs285.proj4;

import java.net.Socket;

import javax.swing.DefaultListModel;

public class ServerTask extends Thread{
	  private Socket socket;
	  private ClientServerSocket serverSocket;
	  public ServerTask(Socket soc, ClientServerSocket inserverSocket){
		  socket = soc;
		  serverSocket = inserverSocket;
		  System.out.println("socket in ServerThread: " + socket);
	  }
	  
	  private void handleSocket() throws Exception{
		  // TODO 
		  // handling the communication information with clients
		  System.out.println("Inside of handle Socket function right now");
		  ClientList.printArrayList();
		  // send data to new client
		  sendPlaylist();
	      sendRequestList();
		  
		     //setup variables
		     while (true)
		     {
		       String inData = serverSocket.recvString();
		       // for debugging information
		       System.out.println("clientSocket recvString: " + inData);
		       

		       String action = "";
		       int i = 0;
		       while(inData.charAt(i) != '\n')
		       {
		         action = action + inData.charAt(i);
		         ++i;
		       }
		       System.out.println("here is the action before if else in ServerTask.java: " + action);
		       if (action.equals("LIST_REQUEST"))
		       {
		    	 serverSocket.sendString("LIST_REQUEST");
		    	 System.out.println("Server thread gets action list_request, sent Playlist info and requestlist info");
		         sendPlaylist();
		         sendRequestList();
		       }
		       else if (action.equals("VOTE"))
		       {
		         serverSocket.sendString("VOTE");
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
		     	System.out.println("Thread \"" + serverSocket + "\" error");
		       }
		       
		     }
	  }
	  
	  public void run(){
		  try {
			handleSocket();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	    serverSocket.sendString(toSend);
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
	    System.out.println(toSend);
	    serverSocket.sendString(toSend);
	    return;
	    
	  }
	  
}
