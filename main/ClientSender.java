
public class ClientSender
{
  public static void SendSongRequest(Song)
  {
    sendString(String.format("%s: %s", ConnectionActionTypeEnum.SONG_REQUEST, Song.toString());
    String newPlayistRequestList = recString();
    updatePlaylistRequestList(newPlayistRequestList)
  }
  public static void SendVote(Song)
  {
    sendString(String.format("%s: %s", ConnectionActionTypeEnum.VOTE, Song.toString());
    String newPlayistRequestList = recString();
    updatePlaylistRequestList(newPlayistRequestList)
  }
  public static void SendListRequest(Song)
  {
    sendString(String.format("%s", ConnectionActionTypeEnum.LIST_REQUEST);
    String newPlayistRequestList = recString();
    updatePlaylistRequestList(newPlayistRequestList)
  }
  private void updatePlaylistRequestList(String newPlaylistRequestList)
  {
    String newPlaylist, newRequestList;
    DefaultListModel<String> currPlaylist = ClientForm.getPlaylist();
    
    
    
    
    
  }
}
