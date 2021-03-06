package eecs285.proj4;

import java.net.Inet4Address;
import java.net.Socket;
import java.net.ServerSocket; 
import java.io.DataOutputStream; 
import java.io.DataInputStream; 
import java.io.IOException; 
import java.util.Vector;

import static java.lang.System.out;

public class ClientServerSocket
{
  private String ipAddr;
  private int portNum;
  private Socket socket;
  private DataOutputStream outData; 
  private DataInputStream inData;
  
  private static int portNo = 8000;
  
  public ClientServerSocket(String inIPAddr, int inPortNum)
  {
    
    ipAddr = inIPAddr; 
    portNum = inPortNum; 
    inData = null; 
    outData = null; 
    socket = null;
  }

  public void startClient() 
  {
     try 
     {
       socket = new Socket(ipAddr, portNum);
       
       System.out.println("socket=" + socket);
       
        outData = new DataOutputStream(socket.getOutputStream()); 
        inData = new DataInputStream(socket.getInputStream());
       
       // System.out.println(recvString());
       
     }
     catch (IOException ioe) 
     {
       out.println("ERROR: Unable to connect - " + "is the server running?");

       System.exit(10); 
       }
  }


  public void startServer()
  {
    ServerSocket serverSock;
    try {
      serverSock = new ServerSocket(portNo, 50, Inet4Address.getLocalHost());
      System.out.println("The Server is started: " + serverSock);
      
      while(true){
    	  // TODO 
    	  // made the server keep running
    	  // need a thread for it
	      out.println("Waiting for client to connect...");
	      // stop until client connects
	      socket = serverSock.accept();
	      out.println("Client connection accepted" + socket);
	      
	      // create a new thread to deal with it
	      (new ServerTask(socket)).start();
	      
	      
	      System.out.println("Still running");
	      // DEBUG INFO
	      // sendString("Hallo?");
	      
	      // outData = new DataOutputStream(socket.getOutputStream()); 
	      // inData = new DataInputStream(socket.getInputStream());
      }
    }
    catch (IOException ioe) 
    { 
      out.println("ERROR: Caught exception starting server");
      System.exit(7); 
    }
    
  }
  
  

  public boolean sendString(String strToSend) 
  {
    boolean success = false;
    try 
    {
      outData.writeBytes(strToSend);
      outData.writeByte(0); //send 0 to signal the end of the string 
      success = true;
    }
    catch (IOException e) 
    {
      System.out.println("Caught IOException Writing To Socket Stream!"); System.exit(-1);
    }
    return (success); 
  }
  
  
  
  
  
  
    public String recvString()
    {
      Vector< Byte > byteVec = new Vector< Byte >(); 
      byte [] byteAry;
      byte recByte;
      String receivedString = "";
      
      try 
      {
        recByte = inData.readByte();
        while (recByte != 0)
        {
          byteVec.add(recByte);
          recByte = inData.readByte();
            
        }
        
        byteAry = new byte[byteVec.size()];
  
        for (int ind = 0; ind < byteVec.size(); ind++)
        {
          byteAry[ind] = byteVec.elementAt(ind).byteValue(); 
        }
        receivedString = new String(byteAry); 
      }
      catch (IOException ioe) 
      {
        out.println("ERROR: receiving string from socket");
        System.exit(8); 
      }
      
      return (receivedString); 
    }
}