package eecs285.proj4;

import java.net.Socket;

public class ServerThread extends Thread{

	  // thread for cur server
	  // private Thread serverThread;
		
	  private ClientServerSocket serverSocket;
	  
	  public ServerThread(ClientServerSocket inserverSocket)
	  {
		// for Debug information: client input Name
		System.out.println("Creating Server Thread");
		// initialize the clientSocket
		serverSocket = inserverSocket;
	  }

	  public void run()
	  {
		// for debugging information
		System.out.println("Running Server Thread");
	    //start server
	     serverSocket.startServer();
	    
	    System.out.println("server closed");
	  }
	  
}
