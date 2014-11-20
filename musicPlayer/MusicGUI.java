package proj4.vanoment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MusicGUI extends JFrame
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public String[] largs;
  public MusicPlayer music;
  public JButton play = new JButton("Play");
  public JButton stop = new JButton("Stop");
  public JButton skip = new JButton("Skip");
  public JButton pause = new JButton("Pause");
  public JButton showAll = new JButton("Show All Titles/Artists");
  public DefaultListModel<String> titlesDL = new DefaultListModel<String>();
  public DefaultListModel<String> artistsDL = new DefaultListModel<String>();
  public JList<String> titlesL = new JList<String>(titlesDL);
  public JList<String> artistsL = new JList<String>(artistsDL);
  public ButtonListener buttonListener = new ButtonListener();
  public static JTextField current = new JTextField(22);
  public JCheckBox repeat = new JCheckBox();
  
  
  public MusicGUI()
  {
    super("Music Player");
    setFont(new Font("Desdemona", Font.BOLD, 12));
    String[] fargs = {
        "All Of Me-Jon Schmidt.mp3",
        "Caring Is Creepy-The Shins.mp3",
        "Don't Panic-Coldplay.mp3",
        "Elephant Gun-Beirut.mp3",
        "Fall Line-Jack Johnson.mp3",
        "Falling Slowly-Glen Hansard.mp3",
        "Me Gustas Tu-Manu Chao.mp3",
        "Never Miss a Beat-Kaiser Chefs.mp3",
        "New Slang-The Shins.mp3",
        "Such Great Heights-The Postal Service.mp3",
        "Take On Me-Aha!.mp3"};
    music = new MusicPlayer(fargs, fargs.length);
    JPanel north = new JPanel(new FlowLayout());
    JLabel welcome = new JLabel("Welcome to the Music Player!");
    welcome.setFont(new Font("Desdemona", Font.BOLD, 30));
    north.add(welcome);
    
    
    titlesL.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        JList<String> list = (JList<String>)e.getSource();
        if (e.getClickCount() == 2)
        {
          int index = list.locationToIndex(e.getPoint());
          for (String s : music.songs)
          {
            if (s == music.songs[index])
            {
              music.setCurrentSong(index);
            }
          }
        }
      }
    });
    //artistsL will change, bring up all songs by that artist maybe?
    artistsL.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        JList<String> list = (JList<String>)e.getSource();
        if (e.getClickCount() == 2)
        {
          int index = list.locationToIndex(e.getPoint());
          for (String s : music.songs)
          {
            if (s == music.songs[index])
            {
              music.setCurrentSong(index);
            }
          }
        }
      }
    });
    titlesL.setVisibleRowCount(9);
    artistsL.setVisibleRowCount(9);
    titlesL.setFixedCellWidth(120);
    artistsL.setFixedCellWidth(120);
    updateSongList();
    Dimension listD = new Dimension(260, 150);
    JPanel eastCenter = new JPanel(new GridLayout(1, 2));
    eastCenter.setBackground(Color.getHSBColor(0, 30, 200));
    eastCenter.add(titlesL);
    eastCenter.add(artistsL);
    JScrollPane eastC = new JScrollPane(eastCenter);
    eastC.setPreferredSize(listD);
    JPanel east = new JPanel(new BorderLayout());
    east.add(eastC, BorderLayout.CENTER);
    east.add(showAll, BorderLayout.SOUTH);
    
    repeat.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        System.out.print("Repeat is o");
        if (repeat.isSelected())
        {
          music.setRepeat(true);
          System.out.println("n");
        }
        else
        {
          music.setRepeat(false);
          System.out.println("ff");
        }
      }
    });
    Dimension d = new Dimension(80, 80);
    play.setPreferredSize(d);
    pause.setPreferredSize(d);
    skip.setPreferredSize(d);
    stop.setPreferredSize(d);
    play.addActionListener(buttonListener);
    skip.addActionListener(buttonListener);
    stop.addActionListener(buttonListener);
    pause.addActionListener(buttonListener);
    JPanel westCenter = new JPanel(new GridLayout(2, 2));
    westCenter.setBackground(Color.getHSBColor(0, 30, 200));
    westCenter.add(play);
    westCenter.add(pause);
    westCenter.add(skip);
    westCenter.add(stop);
    JPanel westSouth = new JPanel(new FlowLayout());
    westSouth.add(new JLabel("Repeat"));
    westSouth.add(repeat);
    westSouth.setBackground(Color.getHSBColor(0, 30, 200));
    JPanel west = new JPanel(new BorderLayout());
    west.add(westSouth, BorderLayout.SOUTH);
    west.add(westCenter, BorderLayout.CENTER);

    
    current.setEditable(false);
    JPanel south = new JPanel(new FlowLayout());
    south.add(new JLabel("Currently Playing:"));
    south.add(current);
    
    
    setLayout(new BorderLayout());
    setBackground(Color.blue);
    west.setBackground(Color.getHSBColor(0, 30, 200));
    east.setBackground(Color.getHSBColor(0, 30, 200));
    north.setBackground(Color.getHSBColor(208, 30, 200));
    south.setBackground(Color.getHSBColor(208, 30, 200));
    add(west, BorderLayout.WEST);
    add(east, BorderLayout.EAST);
    add(north, BorderLayout.NORTH);
    add(south, BorderLayout.SOUTH);
  }
  
  public class ButtonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      if (e.getSource() == play)
      {
        music.playMusic();
      }
      else if (e.getSource() == pause)
      {
        music.pauseMusic();
      }
      else if (e.getSource() == skip)
      {
        music.skipMusic();
      }
      else if (e.getSource() == stop)
      {
        music.stopMusic();
      }
    }
  }

  public static void setCurrentSongDisplay(String song)
  {
    current.setText("");
    current.setText(song);
  }

  public void updateSongList() {
    titlesDL.removeAllElements();
    for (String ti : music.getTitles())
    {
      titlesDL.addElement(ti);
    }
    
    artistsDL.removeAllElements();
    for (String ar : music.getArtists())
    {
      artistsDL.addElement(ar);
    }
  }
}
