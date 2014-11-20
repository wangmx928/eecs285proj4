package proj4.vanoment;

import javax.swing.WindowConstants;


public class Main
{
  public static void main(String[] args)
  {
    MusicGUI music = new MusicGUI();
    music.pack();
    music.setVisible(true);
    music.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }
}
