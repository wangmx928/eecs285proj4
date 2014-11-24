package eecs285.proj4;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientForm extends JFrame {
    
    private JList<String> requestList;
    private JList<String> playlist;
    private DefaultListModel<String> requestListModel;
    private DefaultListModel<String> playlistModel;
    
    private JButton submitRequestButton;
    
    public class ClientListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == submitRequestButton)
            {
                SongRequestForm newRequest = new SongRequestForm(
                        "Submit a Song Request", 
                        ClientForm.this);
                newRequest.pack();
                newRequest.setVisible(true);
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
        
        submitRequestButton = new JButton("Submit a Song Request");
        submitRequestButton.addActionListener(myListener);
        submitRequestButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(requestListPanel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(submitRequestButton);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel playlistPanel = new JPanel();
        playlistPanel.setLayout(new BorderLayout());
        
        JLabel playlistLabel = new JLabel("Initial Playlist:");
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
    
    public ListModel<Song> getRequestListModel()
    {
        return requestListModel;
    }
    public ListModel<Song> getPlaylistModel()
    {
        return playlistModel;
    }
    
}
