package eecs285.proj4;

import java.util.Comparator;

public class SongTitleComparator implements Comparator<Song>
{
  public int compare(Song s1, Song s2)
  {
    return (s1.getSongTitle().compareTo(s2.getSongTitle()));
  }
}
