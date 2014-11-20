package eecs285.proj4;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConnectionWindow extends JFrame {
    
    private JTextArea ipAdd;
    private JTextArea portNum;
    private JButton connect;
    
    public class ConnectionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == connect)
            {
                try
                {
                    Integer inPortNum = Integer.parseInt(portNum.getText());
                    ClientForm myForm = new ClientForm("Playlist Request Form"
                            , ipAdd.getText(), inPortNum);
                    dispose();
                    myForm.pack();
                    myForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    myForm.setVisible(true);
                }
                catch (NumberFormatException e1)
                {
                    JOptionPane.showMessageDialog(ConnectionWindow.this,
                            "Port Number must be an integer",
                            "Port Number Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    public ConnectionWindow(String inTitle)
    {
        super(inTitle);
        
        ConnectionListener myListener = new ConnectionListener();
        
        JLabel instrLabel = new JLabel("Please Enter the IP Address and Port "
                + "Number of the Playlist You Wish to Connect to.");
        
        JLabel ipLabel = new JLabel("IP Address:");
        ipAdd = new JTextArea();
        ipAdd.setPreferredSize(new Dimension(100, 20));
        
        JLabel portLabel = new JLabel("Port Number:");
        portNum = new JTextArea();
        portNum.setPreferredSize(new Dimension(80, 20));
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        
        inputPanel.add(ipLabel);
        inputPanel.add(ipAdd);
        inputPanel.add(portLabel);
        inputPanel.add(portNum);
        
        connect = new JButton("Connect");
        connect.setAlignmentX(Component.CENTER_ALIGNMENT);
        connect.addActionListener(myListener);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        topPanel.add(instrLabel, BorderLayout.NORTH);
        topPanel.add(inputPanel, BorderLayout.CENTER);
        
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.PAGE_AXIS));
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        totalPanel.add(topPanel);
        totalPanel.add(connect);
        
        add(totalPanel);
    }
    
    
    

}
