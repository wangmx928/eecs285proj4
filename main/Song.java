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
    try {
        mp3 = new MP3File(inFile);
        songTitle = mp3.getID3v1Tag().getSongTitle();
        artist = mp3.getID3v1Tag().getArtist();
    } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
        tagError = true;
    } catch (TagException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
        tagError = true;
    } catch (NullPointerException e1) {
        e1.printStackTrace();
        tagError = true;
    }
    if (songTitle == null || songTitle.trim().equals(""))
    {
      songTitle = inFile.getName().substring(0, inFile.getName().length() - 4);
      tagError = true;
    }
    if (artist == null || artist.trim().equals(""))
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
    String output = String.format("%s - %s    #Request: %s", songTitle, artist,
        voteCount.toString());
    return output;
  }
  public String playlistString()
  {
    String output = String.format("%s - %s", songTitle, artist);
    return output;
  }
  
}



