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

  // TODO: why making this function static? - 24th(15:30)
  public/* static */void SendVote(Song inSong)
  {
    // fixed typos - 24th(15:30)
    socket.sendString(String.format("%s\n%s", ConnectionActionTypeEnum.VOTE,
        inSong.playlistString()));
    String newPlaylist = socket.recvString();
    String newRequestList = socket.recvString();
    updatePlaylist(newPlaylist);
    updateRequestList(newRequestList);
  }

  public/* static */void SendListRequest(Song inSong)
  {
    // fixed typos - 24th(15:30)
    socket.sendString(String.format("%s\n",
        ConnectionActionTypeEnum.LIST_REQUEST));
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

    String read = "";
    int i = 0;
    while( newRequestList.charAt(i) != '\n' )
    {
      read = read + newRequestList.charAt(i);
      i++;
    }

    if( !read.equals("REQUESTS: ") )
    {
      // added ERRORINFO - 24th(16:00)
      System.out.println("Error in UpdateRequestList function"
          + ": the first word action is not REQUESTS"
          + " the word got here is \"" + read + "\"");
    }
    else
    {

      while( i < newRequestList.length() - 1 )
      {
        i++;

        /*
         * String insongTitle, inartist; Integer invoteCount; read = ""; for(i =
         * i + 1; newRequestList.charAt(i) != '-'; i++) read = read +
         * newRequestList.charAt(i); insongTitle = read;
         * 
         * read = ""; for(i = i + 2; newRequestList.charAt(i) != '#'; i++) read
         * = read + newRequestList.charAt(i); inartist = read;
         * 
         * for(++i; newRequestList.charAt(i) != ' '; i++){}
         * 
         * read = "";
         * 
         * for(++i; newRequestList.charAt(i) != '\n'; i++) read = read +
         * newRequestList.charAt(i); invoteCount = Integer.parseInt(read);
         * 
         * Song temp = new Song(insongTitle, inartist, invoteCount);
         */
        read = "";
        while( newRequestList.charAt(i) != '\n' )
        {
          read = read + newRequestList.charAt(i);
          i++;
        }
        String title = read.substring(0, 30);
        String artist = read.substring(31, 61);
        String req = read.substring(65, read.length());
        System.out.println(title + artist + " #" + req);
        Song temp = new Song(title, artist, Integer.parseInt(req));
        updatedRequestArrayList.add(temp);
        updatedRequestList.addElement(temp);

        // System.out.println("title: " + insongTitle
        // + "artist: " + inartist
        // + "voteCount: " + invoteCount);
      }
      parentWindow.setRequestListArray(updatedRequestArrayList);
      parentWindow.setRequestListModel(updatedRequestList);
      parentWindow.setVisible(true);
    }
  }

  public void updatePlaylist(String newPlaylist)
  {
    DefaultListModel<String> updatedPlaylist = new DefaultListModel<String>();
    String read = "";
    int i = 0;
    while( newPlaylist.charAt(i) != '\n' )
    {
      read = read + newPlaylist.charAt(i);
      ++i;
    }

    if( !read.equals("PLAYLIST: ") )
    {
      // ERROR
      System.out
          .println("Error in updatePlaylist Function: not receiving 'PLAYLIST: '"
              + "\nshowing word: " + read);
    }
    else
    {
      while( i < newPlaylist.length() - 1 )
      {
        i++;
        read = "";
        /*
         * for (i = i + 1; newPlaylist.charAt(i) != '-'; i++) { read = read +
         * newPlaylist.charAt(i); } read = read + " - "; for (i = i + 4;
         * newPlaylist.charAt(i) != '\n'; i++) { read = read +
         * newPlaylist.charAt(i); }
         */


        while( newPlaylist.charAt(i) != '\n' )
        {
          read = read + newPlaylist.charAt(i);
          i++;
        }

        updatedPlaylist.addElement(read);

      }
      parentWindow.setPlaylistModel(updatedPlaylist);
    }

  }
}
