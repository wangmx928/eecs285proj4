package eecs285.proj4;

import java.net.Socket;

import javax.swing.DefaultListModel;

public class ServerThread extends Thread{
  
    
	  // thread for cur server
	  // private Thread serverThread;
		
	  private ClientServerSocket serverSocket;
	  private ManagerForm parentWindow;
	  
	  public ServerThread(ClientServerSocket inserverSocket, ManagerForm inparentWindow)
	  {
		// for Debug information: client input Name
		System.out.println("Creating Server Thread");
		// initialize the clientSocket
		serverSocket = inserverSocket;
		parentWindow = inparentWindow;
		
	  }

	  public void run()
	  {
		// for debugging information
		System.out.println("Running Server Thread");
	    //start server
	     serverSocket.startServer(parentWindow);
	    
	  }


}
