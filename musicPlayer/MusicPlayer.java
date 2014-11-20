package proj4.vanoment;

import java.util.Map;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

public class MusicPlayer
{
  public int i = 0;
  public boolean repeat;
  private String[] songsInitial;
  private String[] titlesInitial;
  private String[] artistsInitial;
  public String[] songs;
  public String[] titles;
  public String[] artists;
  public BasicPlayer player = new BasicPlayer();
  public PlayerListener playerListener = new PlayerListener();
  
  public MusicPlayer(String[] args, int length)
  {
    songsInitial = new String[length];
    titlesInitial = new String[length];
    artistsInitial = new String[length];
    songs = new String[length];
    titles = new String[length];
    artists = new String[length];
    for (int i = 0; i < length; i++)
    {
      songs[i] = args[i];
      String[] titleArtist = songs[i].split("-");
      titlesInitial[i] = titleArtist[0];
      titles[i] = titleArtist[0];
      artistsInitial[i] = titleArtist[1].substring(0, titleArtist[1].length() - 4);
      artists[i] = titleArtist[1].substring(0, titleArtist[1].length() - 4);
    }
    //check here
    player.addBasicPlayerListener(playerListener);
  }
  
  public String[] getTitles()
  {
    return titles;
  }
  
  public String[] getArtists()
  {
    return artists;
  }
  
  public String[] getInfo()
  {
    return songs;
  }
  
  public String getCurrentTitle()
  {
    return titles[i];
  }

  public String getCurrentArtist()
  {
    return artists[i];
  }
  
  public void addSong(String song)
  {
    String[] temp = songs;
    songs = new String[temp.length + 1];
    songs = temp;
    songs[songs.length] = song;
    
    String[] ta = song.split("-");
    
    temp = titles;
    titles = new String[temp.length + 1];
    titles = temp;
    titles[temp.length] = ta[0];
    
    temp = artists;
    artists = new String[temp.length + 1];
    artists = temp;
    titles[temp.length] = ta[1].substring(0, ta[1].length() - 4); 
  }
  
  private void startMusic()
  {
    if (i < 0) i = 0;
    try
    {
      String songFile = new String("songs/" + songs[i]);
      System.out.println(songFile);
      player = new BasicPlayer();
      player.addBasicPlayerListener(playerListener);
      player.open(getClass().getClassLoader().getResource(songFile));
      player.play();
    }
    catch (BasicPlayerException bpe)
    {
      bpe.printStackTrace();
    }
  }
  
  public void playMusic()
  {
    try
    {
      if (player.getStatus() < 0)
      {
        startMusic();
      }
      else
      {
        player.resume();
      }
    }
    catch( BasicPlayerException e )
    {
    }
  }
  
  public void pauseMusic()
  {
    try
    {
      player.pause();
    }
    catch( BasicPlayerException e )
    {
      //e.printStackTrace();
    }
  }
  
  public void skipMusic()
  {
    try
    {
      if (i < songs.length - 1)
      {
        i = i+1;
      }
      else
      {
        if (repeat == true) i = 0;
        else i = -1;
      }
      player.stop();
      startMusic();
    }
    catch( BasicPlayerException e )
    {
      e.printStackTrace();
    }
  }
  
  public void stopMusic()
  {
    try
    {
      player.stop();
      player = new BasicPlayer();
      i = 0;
    }
    catch( BasicPlayerException e1 )
    {
      e1.printStackTrace();
    }
  }
  
  public void setRepeat(boolean bool)
  {
    repeat = bool;
  }
  
  public void setCurrentSong(int index)
  {
    try
    {
      i = index;
      player.stop();
      startMusic();
    }
    catch( BasicPlayerException e )
    {
      e.printStackTrace();
    }
  }
  
  public void setPlaylist(String[] songsIn)
  {
    songs = new String[songsIn.length];
    for (int i = 0; i < songsIn.length; i++)
    {
      songs[i] = songsIn[i];
      String[] ta = songs[i].split("-");
      titles[i] = ta[0];
      artists[i] = ta[i].substring(0, ta[1].length() - 4);
    }
  }
  
  public void setOriginalPlaylist()
  {
    //3:20 nantes
    songs = songsInitial;
    titles = titlesInitial;
    artists = artistsInitial;
  }
  
  public void addBasicPlayerListener(BasicPlayerListener bpl)
  {
    player.addBasicPlayerListener(bpl);
  }
  
  public class PlayerListener implements BasicPlayerListener
  {

    @Override
    public void opened(Object arg0, Map arg1)
    {
      System.out.println("Opened");
      //MusicGUI.setCurrentSongDisplay(getCurrentTitle() + " - " + getCurrentArtist());
    }

    @Override
    public void progress(int arg0, long arg1, byte[] arg2, Map arg3)
    {
    }

    @Override
    public void setController(BasicController arg0)
    {
      System.out.println("Set Controller");
    }

    @Override
    public void stateUpdated(BasicPlayerEvent arg0)
    {
      System.out.println("State Updated " + player.getStatus());
      if (player.getStatus() == 2)
      {
        if (i == -1) stopMusic();
        //MusicGUI.setCurrentSongDisplay("");
      }
      else if (player.getStatus() == -1)
      {
        //MusicGUI.setCurrentSongDisplay("");
      }
    }
  }
}
