package eecs285.proj4;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

import eecs285.proj4.*;

public class WelcomeWindow extends JFrame
{

  private JButton openNewPlaylist;
  private JButton openExistingPlaylist;

  private int chooserReturn;

  public class WelcomeListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      if( e.getSource() == openNewPlaylist )
      {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Select Music Library");
        chooserReturn = chooser.showOpenDialog(WelcomeWindow.this);
        if( chooserReturn == JFileChooser.APPROVE_OPTION )
        {
          File musicDirectory = chooser.getSelectedFile();
          File allFiles[] = musicDirectory.listFiles();
          ArrayList<Song> songs = new ArrayList<Song>();
          for( File f : allFiles )
          {
            try
            {
              String fileName = f.getName();
              if( fileName.substring(fileName.lastIndexOf(".")).equals(".mp3") )
              {
                songs.add(new Song(f));
              }
            }
            catch( StringIndexOutOfBoundsException e1 )
            {
              // Do nothing
            }

          }
          dispose();

          ManagerForm playlistForm = null;
          // creating the server side
          ClientServerSocket serverSock;
          String ipAddress = "";

          try
          {
            // debug information
            System.out.println(Inet4Address.getLocalHost().getHostAddress());
            serverSock = new ClientServerSocket(Inet4Address.getLocalHost()
                .getHostAddress(), 8000);
            ipAddress = Inet4Address.getLocalHost().getHostAddress();
            playlistForm = new ManagerForm("Playlist Manager", songs, ipAddress);
            playlistForm.pack();
            playlistForm
                .setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            playlistForm.setVisible(true);
            // new thread running
            (new ServerThread(serverSock, playlistForm)).start();
          }
          catch( UnknownHostException e1 )
          {
            System.out.println("Error of unknown host");
            System.exit(10);
          }


          boolean tagError = false;
          for( Song s : songs )
          {
            if( s.getTagError() == true )
            {
              tagError = true;
            }
          }
          if( tagError )
          {
            JOptionPane.showMessageDialog(playlistForm,
                "We encountered an error when trying to parse "
                    + "some of your mp3 files.");
          }
        }
      }
      else if( e.getSource() == openExistingPlaylist )
      {
        dispose();
        ConnectionWindow connectWindow = new ConnectionWindow(
            "Connection Window");
        connectWindow.pack();
        connectWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        connectWindow.setVisible(true);
      }
    }
  }

  public WelcomeWindow(String inTitle)
  {
    super(inTitle);

    WelcomeListener myListener = new WelcomeListener();

    openNewPlaylist = new JButton("Build a New Playlist");
    openNewPlaylist.setAlignmentX(CENTER_ALIGNMENT);
    openNewPlaylist.addActionListener(myListener);
    openExistingPlaylist = new JButton(
        "Submit a Request for an Existing Playlist");
    openExistingPlaylist.setAlignmentX(CENTER_ALIGNMENT);
    openExistingPlaylist.addActionListener(myListener);

    JLabel welcomeLabel = new JLabel("Welcome to the Playlist Manager!");
    welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);

    setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    setResizable(false);

    JPanel totalPanel = new JPanel();
    totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.PAGE_AXIS));
    totalPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    totalPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    totalPanel.add(welcomeLabel);
    totalPanel.add(Box.createVerticalStrut(30));
    totalPanel.add(openNewPlaylist);
    totalPanel.add(Box.createVerticalStrut(10));
    totalPanel.add(openExistingPlaylist);
    totalPanel.add(Box.createVerticalStrut(10));

    // change how it looks
    Font fon = new Font("Courier", Font.PLAIN, 12);
    Font title = new Font("Courier", Font.BOLD, 24);
    openNewPlaylist.setFont(fon);
    openExistingPlaylist.setFont(fon);
    welcomeLabel.setFont(title);
    totalPanel.setBackground(Color.getHSBColor(0, 30, 200));

    add(totalPanel);

  }

}
