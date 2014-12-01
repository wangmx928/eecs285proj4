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
  private ClientForm clientForm;
  
  public ClientThread(ClientServerSocket inClientSocket, String clientName, ClientForm inClientForm)
  {
	// get the clientName which can be shown on GUI
	clientThreadName = clientName;
	// for Debug information: client input Name
	System.out.println("Creating " + clientThreadName);
	
	// initialize the clientSocket
    clientSocket = inClientSocket;
    
    clientForm = inClientForm;
  }

  public void run()
  {
	// for debugging information
	System.out.println("Running " + clientThreadName);
	clientSocket.startClient(clientThreadName, clientForm);
    
	
  }

}
