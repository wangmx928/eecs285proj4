package eecs285.proj4;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class ClientForm extends JFrame
{

  private ArrayList<Song> currentLibrary;
  // private ClientSender clientSender;
  private JList<Song> requestList;
  private JList<String> playlist;
  // should requestListModel be String since in ServerTask we compare it to
  // playlistString?
  private DefaultListModel<Song> requestListModel;
  private DefaultListModel<String> playlistModel;
  private JComboBox<String> sortOption;
  private JButton submitRequestButton;
  private JButton refresh;

  private ClientServerSocket socket;

  public class ClientListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      if( e.getSource() == submitRequestButton )
      {
        socket.sendString(String.format("VOTE\n%s\n", requestList
            .getSelectedValue().playlistString()));
        socket.sendString("LIST_REQUEST\n");
        System.out.println("Send vote from client form");
      }
      else if( e.getSource() == refresh )
      {
        socket.sendString("LIST_REQUEST\n");
        System.out.println("Send list_request from client form");
      }
      else if( e.getSource() == sortOption )
      {
        sortLibrary();
      }
    }
  }

  public ClientForm(String inTitle, String ipAddress, Integer portNum,
      ClientServerSocket insocket)
  {
    super(inTitle);
    addWindowListener(new WindowAdapter() {
    	@Override
    	public void windowClosing(WindowEvent arg0){
    		System.out.println("Closing");
    		socket.sendString("THREAD_EXIT\n");
    		System.out.println("this thread sent exit message");
    	}
    });
    socket = insocket;

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
    JLabel requestListLabel2 = new JLabel(String.format("%-30s %-30s %s",
        "Song Title", "Artist", "#Request"));
    JPanel requestListLabelPanel = new JPanel(new GridLayout(2, 1));
    requestListLabelPanel.add(requestListLabel);
    requestListLabelPanel.add(requestListLabel2);
    requestListPanel.add(requestListLabelPanel, BorderLayout.NORTH);
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
    JLabel playlistLabel2 = new JLabel("Song Title                     Artist");
    JPanel playlistLabelPanel = new JPanel(new GridLayout(2, 1));
    playlistLabelPanel.add(playlistLabel);
    playlistLabelPanel.add(playlistLabel2);
    JScrollPane playlistPane = new JScrollPane();
    playlist = new JList<String>();
    playlistModel = new DefaultListModel<String>();
    playlist.setModel(playlistModel);
    playlistPane.setViewportView(playlist);
    playlistPane.setPreferredSize(new Dimension(500, 400));

    playlistPanel.add(playlistLabelPanel, BorderLayout.NORTH);
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

    // change how it looks
    Font fon = new Font("Courier", Font.PLAIN, 12);
    Color colo = Color.getHSBColor(0, 30, 200);
    Font fonBold = new Font("Courier", Font.BOLD, 12);
    playlistPanel.setBackground(colo);
    playlistButtonsPanel.setBackground(colo);
    sortingPanel.setBackground(colo);
    requestListPanel.setBackground(colo);
    buttonsPanel.setBackground(colo);
    leftPanel.setBackground(colo);
    requestListLabelPanel.setBackground(colo);
    playlistLabelPanel.setBackground(colo);
    playlist.setFont(fon);
    requestList.setFont(fon);
    sortOption.setFont(fon);
    submitRequestButton.setFont(fon);
    refresh.setFont(fon);
    requestListLabel.setFont(fonBold);
    requestListLabel2.setFont(fonBold);
    sortLabel.setFont(fonBold);
    playlistLabel.setFont(fonBold);
    playlistLabel2.setFont(fonBold);

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
    requestListModel = new DefaultListModel<Song>();
    for( int i = 0; i < inRequestList.getSize(); i++ )
    {
      requestListModel.addElement(inRequestList.elementAt(i));


    }
    requestList.setModel(requestListModel);
    sortLibrary();
  }

  public void setRequestListArray(ArrayList<Song> inRequestList)
  {
    currentLibrary = inRequestList;

  }

  public void setPlaylistModel(DefaultListModel<String> inPlaylist)
  {
    playlistModel = inPlaylist;
    playlist.setModel(playlistModel);

  }

  private void sortLibrary()
  {
    if( sortOption.getSelectedItem() == "Song Title" )
    {
      Collections.sort(currentLibrary, new SongTitleComparator());
      requestListModel = new DefaultListModel<Song>();
      for( Song s : currentLibrary )
      {
        requestListModel.addElement(s);
      }
      requestList.setModel(requestListModel);
    }
    else if( sortOption.getSelectedItem() == "Artist" )
    {
      Collections.sort(currentLibrary, new SongArtistComparator());
      requestListModel = new DefaultListModel<Song>();
      for( Song s : currentLibrary )
      {
        requestListModel.addElement(s);
      }
      requestList.setModel(requestListModel);
    }
    else if( sortOption.getSelectedItem() == "Request Count" )
    {
      Collections.sort(currentLibrary, new SongVoteComparator());
      requestListModel = new DefaultListModel<Song>();
      for( Song s : currentLibrary )
      {
        requestListModel.addElement(s);
      }
      requestList.setModel(requestListModel);
    }
  }

}
