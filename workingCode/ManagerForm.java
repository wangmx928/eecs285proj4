package eecs285.proj4;

import javax.swing.*;

import javazoom.jlgui.basicplayer.BasicPlayerException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class ManagerForm extends JFrame
{

  private ArrayList<Song> currentLibrary;

  private JButton addSongs;
  private JButton clearLibrary;
  private JButton setPlaylistToLibrary;
  private JButton inviteFriends;
  private JButton playPause;
  private JButton skip;
  private JButton stop;
  private JButton emptyPlaylist;
  private JButton removeSong;
  private JComboBox<String> sortOption;

  private static JList<Song> libraryList;
  private JList<Song> playlist;
  private static DefaultListModel<Song> libraryListModel;
  private static DefaultListModel<Song> playlistModel;

  private MusicPlayer myPlayer;

  private String ipAddress;
  private final int portNum = 8000;

  public class ManagerActionListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      if( e.getSource() == clearLibrary )
      {
        if( JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(
            ManagerForm.this, "Are you sure you want to "
                + "clear all songs from your library?", "Confirmation",
            JOptionPane.YES_NO_OPTION) )
        {
          return;
        }
        ;
        libraryListModel = new DefaultListModel<Song>();
        libraryList.setModel(libraryListModel);
        currentLibrary = new ArrayList<Song>();
      }
      else if( e.getSource() == playPause )
      {
        if( playPause.getText() == "Play" )
        {
          myPlayer.playMusic();
          playPause.setText("Pause");
        }
        else if( playPause.getText() == "Pause" )
        {
          myPlayer.pauseMusic();
          playPause.setText("Play");
        }
      }
      else if( e.getSource() == skip )
      {
        myPlayer.skipMusic();
      }
      else if( e.getSource() == stop )
      {
        playPause.setText("Play");
        myPlayer.stopMusic();
      }
      else if( e.getSource() == addSongs )
      {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Select Music Library");
        int chooserReturn = chooser.showOpenDialog(ManagerForm.this);
        if( chooserReturn == JFileChooser.APPROVE_OPTION )
        {
          File musicDirectory = chooser.getSelectedFile();
          File allFiles[] = musicDirectory.listFiles();
          for( File f : allFiles )
          {
            String fileName = f.getName();
            if( fileName.substring(fileName.lastIndexOf(".")).equals(".mp3") )
            {
              currentLibrary.add(new Song(f));
            }
          }
          boolean tagError = false;
          for( Song s : currentLibrary )
          {
            if( s.getTagError() == true )
            {
              tagError = true;
            }
          }
          if( tagError )
          {
            JOptionPane.showMessageDialog(ManagerForm.this,
                "We encountered an error when trying to parse "
                    + "some of your mp3 files.");
          }
          sortLibrary();
          libraryListModel = new DefaultListModel<Song>();
          for( Song s : currentLibrary )
          {
            libraryListModel.addElement(s);
          }
        }
      }
      else if( e.getSource() == setPlaylistToLibrary )
      {
        playlistModel = new DefaultListModel<Song>();
        myPlayer.songs = new ArrayList<File>();
        for( Song s : currentLibrary )
        {
          (myPlayer.songs).add(s.getFile());
          playlistModel.addElement(s);
        }
        playlist.setModel(playlistModel);
      }
      else if( e.getSource() == removeSong )
      {
        if( playlist.getSelectedIndex() <= myPlayer.i )
        {
          myPlayer.i -= 1;
        }
        myPlayer.songs.remove(playlist.getSelectedIndex());
        playlistModel.remove(playlist.getSelectedIndex());
        playlist.setModel(playlistModel);
      }
      else if( e.getSource() == emptyPlaylist )
      {
        if( JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(
            ManagerForm.this, "Are you sure you want to "
                + "remove all songs from your playlist?", "Confirmation",
            JOptionPane.YES_NO_OPTION) )
        {
          return;
        }
        ;
        playlistModel = new DefaultListModel<Song>();
        myPlayer.songs = new ArrayList<File>();
        playlist.setModel(playlistModel);
      }
      else if( e.getSource() == inviteFriends )
      {
        EmailForm myEmailForm = new EmailForm(ManagerForm.this,
            "Enter Email Recipients");
        myEmailForm.pack();
        String addressList = myEmailForm.showDialog();
        if( addressList != "" )
        {
          SendMailTLS sendEmails = new SendMailTLS();
          sendEmails.emailMe(addressList, ipAddress);
        }
      }
      else if( e.getSource() == sortOption )
      {
        sortLibrary();
      }
    }

  }

  public ManagerForm(String inTitle, ArrayList<Song> mp3s, String inIPAddress)
  {
    super(inTitle);

    ipAddress = inIPAddress;

    ManagerActionListener myActionListener = new ManagerActionListener();

    currentLibrary = mp3s;

    setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    setResizable(false);

    /*
     * JScrollPane requestListPane = new JScrollPane(); requestList = new
     * JList<File>(); requestListModel = new DefaultListModel<File>();
     * requestList.setModel(requestListModel);
     * requestListPane.setViewportView(requestList);
     * requestListPane.setPreferredSize(new Dimension(500, 400));
     * 
     * JPanel requestListPanel = new JPanel(); requestListPanel.setLayout(new
     * BorderLayout());
     */

    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
    // JLabel requestListLabel = new JLabel("Current Request List:");

    /*
     * requestListPanel.add(requestListLabel, BorderLayout.NORTH);
     * requestListPanel.add(requestListPane, BorderLayout.CENTER);
     */


    JPanel libraryButtonsPanel = new JPanel();
    libraryButtonsPanel.setLayout(new FlowLayout());

    addSongs = new JButton("Add Songs");
    addSongs.addActionListener(myActionListener);
    clearLibrary = new JButton("Clear Library");
    clearLibrary.addActionListener(myActionListener);
    libraryButtonsPanel.add(addSongs);
    libraryButtonsPanel.add(clearLibrary);

    JScrollPane libraryPane = new JScrollPane();
    libraryList = new JList<Song>();
    libraryList.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        if( e.getClickCount() == 2 )
        {
          playlistModel.addElement(libraryList.getSelectedValue());
          playlist.setModel(playlistModel);
          myPlayer.addSong(libraryList.getSelectedValue().getFile());
        }
      }
    });
    libraryListModel = new DefaultListModel<Song>();

    updateLibrary();
    libraryPane.setViewportView(libraryList);
    libraryPane.setPreferredSize(new Dimension(500, 400));

    JLabel libraryListLabel = new JLabel(String.format("Current Library:"));
    JLabel libraryListLabel2 = new JLabel(String.format("%-30s %-30s %s",
        "Song Title", "Artist", "#Request"));
    JPanel libraryListPanel = new JPanel(new GridLayout(2, 1));
    libraryListPanel.add(libraryListLabel);
    libraryListPanel.add(libraryListLabel2);
    JPanel libraryPanel = new JPanel(new BorderLayout());
    libraryPanel.add(libraryListPanel, BorderLayout.NORTH);
    libraryPanel.add(libraryPane, BorderLayout.CENTER);

    JPanel sortingPanel = new JPanel();
    sortingPanel.setLayout(new FlowLayout());

    JLabel sortLabel = new JLabel("Sort Library By:");

    sortOption = new JComboBox<String>();
    sortOption.addItem("Song Title");
    sortOption.addItem("Artist");
    sortOption.addItem("Request Count");
    sortOption.addActionListener(myActionListener);

    sortingPanel.add(sortLabel);
    sortingPanel.add(sortOption);

    // leftPanel.add(requestListPanel);
    leftPanel.add(sortingPanel);
    leftPanel.add(libraryPanel);
    leftPanel.add(libraryButtonsPanel);
    leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel playlistPanel = new JPanel();
    playlistPanel.setLayout(new BorderLayout());

    JLabel playlistLabel = new JLabel("Current Playlist:\n");
    JLabel playlistLabel2 = new JLabel("Song Title                     Artist");
    JScrollPane playlistPane = new JScrollPane();
    playlist = new JList<Song>();
    playlistModel = new DefaultListModel<Song>();
    playlist.setModel(playlistModel);
    playlist.setCellRenderer(new FileListCellRenderer());
    playlistPane.setViewportView(playlist);
    playlistPane.setPreferredSize(new Dimension(500, 400));

    playlist.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        if( e.getClickCount() == 2 )
        {
          int index = playlist.getSelectedIndex();
          myPlayer.stopMusic();
          myPlayer.i = index;
          playlist.setSelectedIndex(index);
          System.out.println(myPlayer.player.getStatus());
          myPlayer.playMusic();
          playPause.setText("Pause");
        }
      }
    });

    playlistPanel.add(playlistLabel, BorderLayout.NORTH);
    playlistPanel.add(playlistLabel2, BorderLayout.CENTER);
    playlistPanel.add(playlistPane, BorderLayout.SOUTH);
    playlistPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));

    JPanel rightPanel = new JPanel();
    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

    setPlaylistToLibrary = new JButton("Set Entire Library as Playlist");
    setPlaylistToLibrary.addActionListener(myActionListener);
    inviteFriends = new JButton("Invite Friends to Request Songs");
    inviteFriends.addActionListener(myActionListener);

    JPanel playlistButtonsPanel = new JPanel();
    playlistButtonsPanel.setLayout(new FlowLayout());
    playlistButtonsPanel.add(inviteFriends);
    playlistButtonsPanel.add(setPlaylistToLibrary);

    removeSong = new JButton("Remove Selected Song");
    removeSong.addActionListener(myActionListener);
    emptyPlaylist = new JButton("Clear Playlist");
    emptyPlaylist.addActionListener(myActionListener);

    JPanel playlistButtonsPanel2 = new JPanel();
    playlistButtonsPanel2.setLayout(new FlowLayout());
    playlistButtonsPanel2.add(removeSong);
    playlistButtonsPanel2.add(emptyPlaylist);

    JPanel playlistTopPanel = new JPanel();
    playlistTopPanel.setLayout(new FlowLayout());


    playPause = new JButton("Play");
    playPause.setPreferredSize(new Dimension(70, 25));
    playPause.addActionListener(myActionListener);
    skip = new JButton("Skip");
    skip.setPreferredSize(new Dimension(70, 25));
    skip.addActionListener(myActionListener);
    stop = new JButton("Stop");
    stop.setPreferredSize(new Dimension(70, 25));
    stop.addActionListener(myActionListener);

    playlistTopPanel.add(playPause);
    playlistTopPanel.add(skip);
    playlistTopPanel.add(stop);

    rightPanel.add(playlistTopPanel);
    rightPanel.add(playlistPanel);
    rightPanel.add(playlistButtonsPanel2);
    rightPanel.add(playlistButtonsPanel);

    JPanel totalPanel = new JPanel();
    totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.LINE_AXIS));

    totalPanel.add(leftPanel);
    totalPanel.add(rightPanel);

    myPlayer = new MusicPlayer(new ArrayList<File>(), playlist);
    sortLibrary();

    // changing the looks
    Font fon = new Font("Courier", Font.PLAIN, 12);
    Color colo = Color.getHSBColor(0, 30, 200);
    Font fonBold = new Font("Courier", Font.BOLD, 12);
    libraryList.setFont(fon);
    playlist.setFont(fon);
    skip.setFont(fon);
    stop.setFont(fon);
    playPause.setFont(fon);
    addSongs.setFont(fon);
    clearLibrary.setFont(fon);
    setPlaylistToLibrary.setFont(fon);
    inviteFriends.setFont(fon);
    emptyPlaylist.setFont(fon);
    removeSong.setFont(fon);
    sortOption.setFont(fon);
    sortLabel.setFont(fonBold);
    libraryListLabel.setFont(fonBold);
    libraryListLabel2.setFont(fonBold);
    playlistLabel.setFont(fonBold);
    playlistLabel2.setFont(fonBold);

    playlistTopPanel.setBackground(colo);
    playlistPanel.setBackground(colo);
    playlistButtonsPanel.setBackground(colo);
    playlistButtonsPanel2.setBackground(colo);
    sortingPanel.setBackground(colo);
    libraryPanel.setBackground(colo);
    libraryListPanel.setBackground(colo);
    libraryButtonsPanel.setBackground(colo);
    leftPanel.setBackground(colo);

    add(totalPanel);

  }

  private void updateLibrary()
  {
    libraryListModel = new DefaultListModel<Song>();
    for( Song s : currentLibrary )
    {
      libraryListModel.addElement(s);
    }
    libraryList.setModel(libraryListModel);
  }

  public void sortLibrary()
  {
    if( sortOption.getSelectedItem() == "Song Title" )
    {
      Collections.sort(currentLibrary, new SongTitleComparator());
      libraryListModel = new DefaultListModel<Song>();
      for( Song s : currentLibrary )
      {
        libraryListModel.addElement(s);
      }
      libraryList.setModel(libraryListModel);
    }
    else if( sortOption.getSelectedItem() == "Artist" )
    {
      Collections.sort(currentLibrary, new SongArtistComparator());
      libraryListModel = new DefaultListModel<Song>();
      for( Song s : currentLibrary )
      {
        libraryListModel.addElement(s);
      }
      libraryList.setModel(libraryListModel);
    }
    else if( sortOption.getSelectedItem() == "Request Count" )
    {
      Collections.sort(currentLibrary, new SongVoteComparator());
      libraryListModel = new DefaultListModel<Song>();
      for( Song s : currentLibrary )
      {
        libraryListModel.addElement(s);
      }
      libraryList.setModel(libraryListModel);
    }
  }

  public static DefaultListModel<Song> getLibraryListModel()
  {
    return libraryListModel;
  }

  public static DefaultListModel<Song> getPlaylistModel()
  {
    return playlistModel;
  }

  public static void updateRequestList()
  {


  }


}
