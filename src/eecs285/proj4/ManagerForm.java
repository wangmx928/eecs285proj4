package eecs285.proj4;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class ManagerForm extends JFrame {
    
    private ArrayList <File> currentLibrary;
    
    private JButton addSongs;
    private JButton clearLibrary;
    private JButton setPlaylistToLibrary;
    private JButton inviteFriends;
    
    private JList<String> requestList;
    private JList<String> libraryList;
    private JList<String> playlist;
    private DefaultListModel<String> requestListModel;
    private DefaultListModel<String> libraryListModel;
    private DefaultListModel<String> playlistModel;
    
    public ManagerForm(String inTitle, ArrayList<File> mp3s)
    {
        super(inTitle);
        
        currentLibrary = mp3s;
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setResizable(false);
        
        JScrollPane requestListPane = new JScrollPane();
        requestList = new JList<String>();
        requestListModel = new DefaultListModel<String>();
        requestList.setModel(requestListModel);
        requestListPane.setViewportView(requestList);
        requestListPane.setPreferredSize(new Dimension(500, 400));
        
        JPanel requestListPanel = new JPanel();
        requestListPanel.setLayout(new BorderLayout());
        
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        JLabel requestListLabel = new JLabel("Current Request List:");
              
        requestListPanel.add(requestListLabel, BorderLayout.NORTH);
        requestListPanel.add(requestListPane, BorderLayout.CENTER);
        
        JPanel libraryButtonsPanel = new JPanel();
        libraryButtonsPanel.setLayout(new FlowLayout());
        
        addSongs = new JButton("Add Songs");
        clearLibrary = new JButton("Clear Library");
        libraryButtonsPanel.add(addSongs);
        libraryButtonsPanel.add(clearLibrary);
        
        JScrollPane libraryPane = new JScrollPane();
        libraryList = new JList<String>();
        libraryListModel = new DefaultListModel<String>();
        
        updateLibrary();
        libraryPane.setViewportView(libraryList);
        libraryPane.setPreferredSize(new Dimension(500, 400));
        
        JLabel libraryListLabel = new JLabel("Current Library:");
        JPanel libraryPanel = new JPanel();
        libraryPanel.setLayout(new BorderLayout());
        libraryPanel.add(libraryListLabel, BorderLayout.NORTH);
        libraryPanel.add(libraryPane, BorderLayout.CENTER);

        leftPanel.add(requestListPanel);
        leftPanel.add(libraryPanel);
        leftPanel.add(libraryButtonsPanel);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel playlistPanel = new JPanel();
        playlistPanel.setLayout(new BorderLayout());
        
        JLabel playlistLabel = new JLabel("Current Playlist:");
        JScrollPane playlistPane = new JScrollPane();
        playlist = new JList<String>();
        playlistModel = new DefaultListModel<String>();
        playlist.setModel(playlistModel);
        playlistPane.setViewportView(playlist);
        playlistPane.setPreferredSize(new Dimension(500, 400));
        
        playlistPanel.add(playlistLabel, BorderLayout.NORTH);
        playlistPanel.add(playlistPane, BorderLayout.CENTER);
        playlistPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        
        setPlaylistToLibrary = new JButton("Set the Playlist to the Entire Library");
        inviteFriends = new JButton("Invite Friends to Request Songs for your Playlist");
        
        JPanel playlistButtonsPanel = new JPanel();
        playlistButtonsPanel.setLayout(new FlowLayout());
        playlistButtonsPanel.add(inviteFriends);
        playlistButtonsPanel.add(setPlaylistToLibrary);
        
        rightPanel.add(playlistPanel);
        rightPanel.add(playlistButtonsPanel);
        
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.LINE_AXIS));
        
        totalPanel.add(leftPanel);
        totalPanel.add(rightPanel);
        
        add(totalPanel);
        
    }
    
    private void updateLibrary()
    {
        libraryListModel = new DefaultListModel<String>();
        for(File f : currentLibrary)
        {
            libraryListModel.addElement(f.getName());
        }
        libraryList.setModel(libraryListModel);
    }
    
}
