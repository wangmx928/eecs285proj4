package eecs285.proj4;




public class ClientSender
{
    
    private ClientServerSocket socket;
    ClientSender(ClientServerSocket inSocket)
    {
        socket = inSocket;
    }
    
    public static void SendVote(Song inSong)
    {
      socket.sendString(String.format("%s\n%s", ConnectionActionTypeEnum.VOTE, inSong.playlistString());
      String newPlayist = socket.recvString();
      String newRequestList = socket.recvString();
      updatePlaylist(newPlaylist);
      updateRequestList(newRequestList);
    }
    public static void SendListRequest(Song)
    {
      sendString(String.format("%s\n", ConnectionActionTypeEnum.LIST_REQUEST)
      String newPlayist = socket.recString();
      String newRequestList = socket.recString();
      updatePlaylist(newPlaylist);
      updateRequestList(newRequestList);
    }
                       
                       
                       
    private void updateRequestList(String newRequestList)
    {
      DefaultListModel<String> updatedRequestList = new DefaultListModel<String>();
      String read = "";
      int i = 0;
      while (newPlaylistRequestList.at(i) != '\n')
      {
        read = read + newRequestList.at(i);
        ++i;
      }
      if (read != "REQUESTS")
      {
         //ERROR
      }
      read = "";
      while (newPlaylist.at(i) != '\0')
      {
        read = read + newRequestList.at(i);
        if (newPlaylist.at(i) == '\n')
        {       
          updatedRequestList.add(read);
          read = "";
        }
      ++i;
      }
    }
                       
  private void updatePlaylist(String newPlaylist)
  {
                DefaultListModel<String> updatedPlaylist = new DefaultListModel<String>();
                String read = "";
                int i = 0;
                while (newPlaylistRequestList.at(i) != '\n')
                {
                    read = read + newPlaylist.at(i);
                    ++i;
                }
                if (read != "PLAYLIST")
                {
                    //ERROR
                }
                read = "";
                while (newPlaylist.at(i) != '\0')
                {
                    read = read + newPlaylist.at(i);
                    if (newPlaylist.at(i) == '\n')
                    {
                        
                        updatedPlaylist.add(read);
                        
                        read = "";
                    }
                    ++i;
                }
                //set ClientWindow Playlist
                
            }
}








