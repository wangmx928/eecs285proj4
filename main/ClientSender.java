package eecs285.proj4;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import eecs285.proj4.*;


public class ClientSender
{
    
    private ClientServerSocket socket;
    private ClientForm parentWindow;
    ClientSender(ClientServerSocket inSocket, ClientForm inParentWindow)
    {
        socket = inSocket;
        parentWindow = inParentWindow;
    }
    
    //	TODO: why making this function static? - 24th(15:30)
    public /*static*/ void SendVote(Song inSong)
    {
      // fixed typos - 24th(15:30)
      socket.sendString(String.format("%s\n%s", ConnectionActionTypeEnum.VOTE, inSong.playlistString()));
      String newPlaylist = socket.recvString();
      String newRequestList = socket.recvString();
      updatePlaylist(newPlaylist);
      updateRequestList(newRequestList);
    }
    public /*static*/ void SendListRequest(Song inSong)
    {
      // fixed typos - 24th(15:30)
      socket.sendString(String.format("%s\n", ConnectionActionTypeEnum.LIST_REQUEST));
      String newPlaylist = socket.recvString();
      String newRequestList = socket.recvString();
      updatePlaylist(newPlaylist);
      updateRequestList(newRequestList);
    }
                       
                       
    // Changed the way of parsing string here - 24th(15:40)                  
    public void updateRequestList(String newRequestList)
    {
      DefaultListModel<Song> updatedRequestList = new DefaultListModel<Song>();
      ArrayList<Song> updatedRequestArrayList = new ArrayList<Song>();
      // Replaced the original code of parsing string
      String arr[] = newRequestList.split(" ");
      String read = arr[0]; //first word
      if (read != "REQUESTS")
      {
         // added ERRORINFO - 24th(16:00)
    	 System.out.println("Error in UpdateRequestList function"
    			 + ": the first word action is not REQUESTS"
    			 + " the word got here is \"" + read + "\"");
      }
      String insongTitle, inartist;
      Integer invoteCount;
      for(int i = 1; i < arr.length; i++){
    	  insongTitle = arr[i];
    	  i = i + 2;
    	  inartist = arr[i];
    	  i = i + 2;
    	  invoteCount = Integer.parseInt(arr[i]);
    	  
    	  // DEBUG INFO
    	  System.out.println("title: " + insongTitle 
    	                   + "artist: " + inartist
    	                   + "voteCount: " + invoteCount);
    	  
    	  Song temp = new Song(insongTitle, inartist, invoteCount);
    	  updatedRequestArrayList.add(temp);
    	  updatedRequestList.addElement(temp);
    	  
      }
      // 	   Note: it's completely new requestList
      //	   does not contain the previous data
      parentWindow.setRequestListModel(updatedRequestList);
      parentWindow.setRequestListArray(updatedRequestArrayList);
      
    }
                       
  public void updatePlaylist(String newPlaylist)
  {
                DefaultListModel<String> updatedPlaylist = new DefaultListModel<String>();
                String read = "";
                int i = 0;
                while (newPlaylist.charAt(i) != '\n')
                {
                    read = read + newPlaylist.charAt(i);
                    ++i;
                }
                if (read != "PLAYLIST")
                {
                    //ERROR
                	System.out.println("Error in updatePlayelist Function: not receiving PLAYLIST"
                			+ "\nshowing word: " + read);
                }
                i++;
                read = "";
                while (newPlaylist.charAt(i) != '\0')
                {
                	if(newPlaylist.charAt(i) != '\n')
                		read = read + newPlaylist.charAt(i);
                	else
                    {
                		System.out.println("addElent to updatedPlayList: " + read);
                        updatedPlaylist.addElement(read);
                        read = "";
                    }
                    ++i;
                }
                parentWindow.setPlaylistModel(updatedPlaylist);
                
            }
}








