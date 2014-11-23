package eecs285.proj4;




public class ClientSender
{
  
  private ClientServerSocket socket;
  ClientSender(ClientServerSocket inSocket)
  {
    socket = inSocket;
  }
  public static void SendSongRequest(Song)
  {
    socket.sendString(String.format("%s: %s", ConnectionActionTypeEnum.SONG_REQUEST, Song.toString());
    String newPlayistRequestList = socket.recString();
    updatePlaylistRequestList(newPlayistRequestList)
  }
  public static void SendVote(Song)
  {
    socket.sendString(String.format("%s: %s", ConnectionActionTypeEnum.VOTE, Song.toString());
    String newPlayistRequestList = socket.recString();
    updatePlaylistRequestList(newPlayistRequestList)
  }
  public static void SendListRequest(Song)
  {
    sendString(String.format("%s", ConnectionActionTypeEnum.LIST_REQUEST);
    String newPlayistRequestList = socket.recString();
    updatePlaylistRequestList(newPlayistRequestList)
  }
  private void updatePlaylistRequestList(String newPlaylistRequestList)
  {
    String newPlaylist, newRequestList;
    DefaultListModel<String> currPlaylist = ClientForm.getPlaylist();

  }
}
