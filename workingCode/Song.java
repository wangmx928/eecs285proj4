package eecs285.proj4;

import java.io.File;
import java.io.IOException;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

public class Song
{
  private File fileName;
  private Integer voteCount;
  private String artist;
  private String songTitle;

  private boolean tagError;

  public Song(String inSongTitle, String inArtist, int inVoteCount)
  {
    songTitle = inSongTitle;
    artist = inArtist;
    voteCount = inVoteCount;
    fileName = null;
    tagError = false;
  }

  public Song(File inFile)
  {
    tagError = false;
    fileName = inFile;
    MP3File mp3;
    try
    {
      mp3 = new MP3File(inFile);
      songTitle = mp3.getID3v1Tag().getSongTitle();
      artist = mp3.getID3v1Tag().getArtist();
    }
    catch( IOException e1 )
    {
      // TODO Auto-generated catch block
      e1.printStackTrace();
      tagError = true;
    }
    catch( TagException e1 )
    {
      // TODO Auto-generated catch block
      e1.printStackTrace();
      tagError = true;
    }
    catch( NullPointerException e1 )
    {
      e1.printStackTrace();
      tagError = true;
    }
    if( songTitle == null || songTitle.trim().equals("") )
    {
      songTitle = inFile.getName().substring(0, inFile.getName().length() - 4);
      tagError = true;
    }
    if( artist == null || artist.trim().equals("") )
    {
      artist = "Unknown";
      tagError = true;
    }
    voteCount = 0;
  }

  public boolean getTagError()
  {
    return tagError;
  }

  public void addOneVote()
  {
    voteCount++;
  }

  public File getFile()
  {
    return fileName;
  }

  public String getArtist()
  {
    return artist;
  }

  public String getSongTitle()
  {
    return songTitle;
  }

  public int getVoteCount()
  {
    return voteCount;
  }

  public String toString()
  {
    if( songTitle.length() > 30 && artist.length() > 30 )
    {
      return String.format("%-27.27s... %-27.27s...    %s", songTitle, artist,
          voteCount.toString());
    }
    else if( songTitle.length() > 30 )
    {
      return String.format("%-27.27s... %-30.30s    %s", songTitle, artist,
          voteCount.toString());
    }
    else if( artist.length() > 30 )
    {
      return String.format("%-30.30s %-27.27s...    %s", songTitle, artist,
          voteCount.toString());
    }
    else
      return String.format("%-30.30s %-30.30s    %s", songTitle, artist,
          voteCount.toString());
  }

  public String playlistString()
  {
    if( songTitle.length() > 30 && artist.length() > 30 )
    {
      return String.format("%-27.27s... %-27.27s...", songTitle, artist);
    }
    else if( songTitle.length() > 30 )
    {
      return String.format("%-27.27s... %-30.30s", songTitle, artist);
    }
    else if( artist.length() > 30 )
    {
      return String.format("%-30.30s %-27.27s...", songTitle, artist);
    }
    else
      return String.format("%-30.30s %-30.30s", songTitle, artist);
  }

}
