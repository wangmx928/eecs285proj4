package eecs285.proj4;

import java.util.Comparator;

public class SongVoteComparator implements Comparator<Song>{
	public int compare(Song s1, Song s2) {
		if(s1.getVoteCount() > s2.getVoteCount())
			return 1;
		else if(s1.getVoteCount() == s2.getVoteCount())
			return return (s1.getSongTitle().compareTo(s2.getSongTitle()));
		else return -1;
	}
}
