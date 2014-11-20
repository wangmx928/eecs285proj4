package eecs285.proj4;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SongRequestForm extends JDialog {
    
    private JTextArea title;
    private JTextArea artist;
    private JButton submitRequest;
    
    public class RequestListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            
        }
    }
    
    public SongRequestForm(String inTitle, JFrame mainFrame)
    {
        super(mainFrame, inTitle, true);
        
        RequestListener myListener = new RequestListener();

        JLabel titleLabel = new JLabel("Song Title:");
        title = new JTextArea();
        title.setPreferredSize(new Dimension(100, 20));
        
        JLabel artistLabel = new JLabel("Artist:");
        artist = new JTextArea();
        artist.setPreferredSize(new Dimension(80, 20));
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        
        inputPanel.add(titleLabel);
        inputPanel.add(title);
        inputPanel.add(artistLabel);
        inputPanel.add(artist);
        
        submitRequest = new JButton("Submit Request");
        submitRequest.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitRequest.addActionListener(myListener);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        topPanel.add(inputPanel, BorderLayout.CENTER);
        
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.PAGE_AXIS));
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        totalPanel.add(topPanel);
        totalPanel.add(submitRequest);
        
        add(totalPanel);
    }
    
    
    

}
