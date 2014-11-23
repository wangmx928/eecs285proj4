package eecs285.proj4;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

public class Song
{
  private File fileName;
  private Integer voteCount;
  private String artist;
  private String songTitle;

  public Song(File inFile)
  {
    fileName = inFile;
    MP3File mp3 = new MP3File(inFile);
    try 
    {
      songTitle = mp3.getID3v1Tag().getSongTitle();
    }
    catch (TagException e)
    {
      songTitle = listFile.getName().substring(0, listFile.getName().length() - 4);
    }
    try
    {
      artist = mp3.getID3v1Tag().getArtist();
    }
    catch
    {
      artist = "Unknown";
    }
    if (songTitle == "")
    {
      songTitle = listFile.getName().substring(0, listFile.getName().length() - 4);
    }
    if (artist == "")
    {
      artist = "Unknown";
    }
    voteCount = 0;
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
    String output = String.format("%s - %s    #Request: %s", songTitle, artist,
        voteCount.toString());
  }
}



