package eecs285.proj4;

import java.io.File;
import java.util.ArrayList;

import javax.swing.WindowConstants;

public class Mainfile {
	
    public static void main( String[] args) {
        WelcomeWindow win = new WelcomeWindow("Playlist Manager");
        win.pack();
        win.setVisible(true);
        win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
