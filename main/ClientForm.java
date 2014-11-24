package eecs285.proj4;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class ClientForm extends JFrame {
    
    private ArrayList<Song> currentLibrary;
    // private ClientSender clientSender;
    private JList<Song> requestList;
    private JList<String> playlist;
    private DefaultListModel<Song> requestListModel;
    private DefaultListModel<String> playlistModel;
    private JComboBox<String> sortOption;
    
    private JButton submitRequestButton;
    private JButton refresh;
    
    public class ClientListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == submitRequestButton)
            {
                
            }
            else if(e.getSource() == refresh)
            {
                
            }
            else if(e.getSource() == sortOption)
            {
                sortLibrary();
            }
        }
    }
    
    public ClientForm(String inTitle, String ipAddress, Integer portNum)
    {
        super(inTitle);
        
        ClientListener myListener = new ClientListener();
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setResizable(false);
        
        JScrollPane requestListPane = new JScrollPane();
        requestList = new JList<Song>();
        requestListModel = new DefaultListModel<Song>();
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
        
        submitRequestButton = new JButton("Request the Selected Song");
        submitRequestButton.addActionListener(myListener);
        submitRequestButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel sortingPanel = new JPanel();
        sortingPanel.setLayout(new FlowLayout());
        
        JLabel sortLabel = new JLabel("Sort Library By:");
        
        sortOption = new JComboBox<String>();
        sortOption.addItem("Song Title");
        sortOption.addItem("Artist");
        sortOption.addItem("Request Count");
        sortOption.addActionListener(myListener);
        
        sortingPanel.add(sortLabel);
        sortingPanel.add(sortOption);
        
        refresh = new JButton("Refresh Library and Playlist");
        refresh.addActionListener(myListener);
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(submitRequestButton);
        buttonsPanel.add(refresh);

        leftPanel.add(sortingPanel);
        leftPanel.add(requestListPanel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(buttonsPanel);
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
        playlistPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 10));
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        
        JPanel playlistButtonsPanel = new JPanel();
        playlistButtonsPanel.setLayout(new FlowLayout());
        
        rightPanel.add(playlistPanel);
        rightPanel.add(playlistButtonsPanel);
        
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.LINE_AXIS));
        
        totalPanel.add(leftPanel);
        totalPanel.add(rightPanel);
        
        add(totalPanel);
    }
    
    public DefaultListModel<Song> getRequestListModel()
    {
        return requestListModel;
    }
    public DefaultListModel<String> getPlaylistModel()
    {
        return playlistModel;
    }
    
    public void setRequestListModel(DefaultListModel<Song> inRequestList)
    {
        requestListModel = inRequestList;
    }
    
    public void setRequestListArray(ArrayList<Song> inRequestList)
    {
    	currentLibrary = inRequestList;
    }
    
    public void setPlaylistModel(DefaultListModel<String> inPlaylist)
    {
        playlistModel = inPlaylist;
    }
    
    private void sortLibrary()
    {
        if(sortOption.getSelectedItem() == "Song Title")
        {
            Collections.sort(currentLibrary, new SongTitleComparator());
            requestListModel = new DefaultListModel<Song>();
            for(Song s : currentLibrary)
            {
                requestListModel.addElement(s);
            }
            requestList.setModel(requestListModel);
        }
        else if(sortOption.getSelectedItem() == "Artist")
        {
            Collections.sort(currentLibrary, new SongArtistComparator());
            requestListModel = new DefaultListModel<Song>();
            for(Song s : currentLibrary)
            {
                requestListModel.addElement(s);
            }
            requestList.setModel(requestListModel);
        }
        else if(sortOption.getSelectedItem() == "Request Count")
        {
            Collections.sort(currentLibrary, new SongVoteComparator());
            requestListModel = new DefaultListModel<Song>();
            for(Song s : currentLibrary)
            {
                requestListModel.addElement(s);
            }
            requestList.setModel(requestListModel);
        }
    }
    
}
