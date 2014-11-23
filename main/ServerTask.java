package eecs285.proj4;

import java.net.Socket;

public class ServerTask extends Thread{
	  private Socket socket;
	  
	  public ServerTask(Socket soc){
		  socket = soc;
		  System.out.println("socket in ServerThread: " + socket);
	  }
	  
	  private void handleSocket() throws Exception{
		  // TODO 
		  // handling the communication information with clients
		  System.out.println("Inside of handle Socket function right now");
		  ClientList.printArrayList();
	  }
	  
	  public void run(){
		  try {
			handleSocket();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
}
