package eecs285.proj4;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class WelcomeWindow extends JFrame {
    
    private JButton openNewPlaylist;
    private JButton openExistingPlaylist;
    
    private int chooserReturn;
    
    public class WelcomeListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == openNewPlaylist)
            {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setDialogTitle("Select Music Library");
                chooserReturn = chooser.showOpenDialog(WelcomeWindow.this);
                if(chooserReturn == JFileChooser.APPROVE_OPTION)
                {
                    File musicDirectory = chooser.getSelectedFile();
                    File allFiles[] = musicDirectory.listFiles();
                    ArrayList <File> songs = new ArrayList<File>();
                    for(File f : allFiles)
                    {
                        String fileName = f.getName();
                        if(fileName.substring(fileName.lastIndexOf(".")).equals(".mp3"))
                        {
                            songs.add(f);  
                        }
                    }
                    dispose();
                    ManagerForm playlistForm = new ManagerForm("Playlist Manager", songs);
                    playlistForm.pack();
                    playlistForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    playlistForm.setVisible(true);
                }
            }
            else if(e.getSource() == openExistingPlaylist)
            {
                dispose();
                ConnectionWindow connectWindow = new ConnectionWindow("Connection Window");
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
        openExistingPlaylist = new JButton("Submit a Request for an Existing Playlist");
        openExistingPlaylist.setAlignmentX(CENTER_ALIGNMENT);
        openExistingPlaylist.addActionListener(myListener);
        
        JLabel welcomeLabel = new JLabel("Welcome to the Playlist Manager!");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
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
        
        add(totalPanel);
        
    }

}
