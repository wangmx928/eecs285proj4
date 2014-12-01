package eecs285.proj4;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JList;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

public class MusicPlayer
{
  public int i = 0;
  public boolean repeat;
  public ArrayList<File> songs;
  public String[] titles;
  public String[] artists;
  public BasicPlayer player = new BasicPlayer();
  public PlayerListener playerListener = new PlayerListener();

  private JList<Song> playlist;

  public MusicPlayer(ArrayList<File> args, JList<Song> inPlaylist)
  {
    songs = new ArrayList<File>();
    for( File f : args )
    {
      songs.add(f);
    }
    // check here
    player.addBasicPlayerListener(playerListener);

    playlist = inPlaylist;
  }

  public String[] getTitles()
  {
    return titles;
  }

  public String[] getArtists()
  {
    return artists;
  }

  public ArrayList<File> getInfo()
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

  public void addSong(File song)
  {
    songs.add(song);
  }

  private void startMusic()
  {
    try
    {
      File songFile = songs.get(i);
      playlist.setSelectedIndex(i);
      System.out.println(songFile);
      player = new BasicPlayer();
      player.addBasicPlayerListener(playerListener);
      player.open(songFile);
      player.play();
    }
    catch( BasicPlayerException bpe )
    {
      bpe.printStackTrace();
    }
  }

  public void playMusic()
  {
    try
    {
      if( player.getStatus() < 0 )
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
      // e.printStackTrace();
    }
  }

  public void skipMusic()
  {
    try
    {
      i = i + 1;
      i = i % songs.size();
      playlist.setSelectedIndex(i);

      player.pause();
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
      player.pause();
      player = new BasicPlayer();
      i = 0;
      playlist.setSelectedIndex(i);
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
      playlist.setSelectedIndex(i);
      player.pause();
      startMusic();
    }
    catch( BasicPlayerException e )
    {
      e.printStackTrace();
    }
  }

  public void setPlaylist(ArrayList<File> songsIn)
  {
    songs = new ArrayList<File>();
    for( File f : songsIn )
    {
      songs.add(f);
    }
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
      // MusicGUI.setCurrentSongDisplay(getCurrentTitle() + " - " +
      // getCurrentArtist());
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
      if( player.getStatus() == 2 )
      {
        skipMusic();
      }
      else if( player.getStatus() == -1 )
      {
        // MusicGUI.setCurrentSongDisplay("");
      }
    }
  }
}
