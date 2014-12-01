package eecs285.proj4;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import eecs285.proj4.*;

public class ConnectionWindow extends JFrame {
    
    private JTextArea ipAdd;
    private JTextArea nameString;
    private JButton connect;
    
    public class ConnectionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == connect)
            {
                try
                {
                	String inIpAdd = ipAdd.getText();	
                    String inNickName = nameString.getText();
                    
                    
                    // start client thread and connection to the server
                    ClientServerSocket csSocket = new ClientServerSocket(inIpAdd, 8000);
                    
                    ClientForm myForm = new ClientForm("Playlist Request Form"
                            , ipAdd.getText(), 8000, csSocket);
                    
                    //ClientList.addClient(csSocket, inNickName);
                    Client curClient = new Client(csSocket, inNickName, myForm);
                    
                    
                    dispose();
                    myForm.setVisible(true);
                    myForm.pack();
                    myForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    
                    
                    
                    
                   //  myForm.setVisible(true);
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
        
        JLabel instrLabel = new JLabel("Please Enter the IP Address "
                + "of the Playlist You Wish to Connect to.");
        
        JLabel ipLabel = new JLabel("IP Address:");
        ipAdd = new JTextArea();
        ipAdd.setPreferredSize(new Dimension(100, 20));
        
        JLabel clientName = new JLabel("Nick Name:");
        nameString = new JTextArea();
        nameString.setPreferredSize(new Dimension(80, 20));
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        
        inputPanel.add(ipLabel);
        inputPanel.add(ipAdd);
        inputPanel.add(clientName);
        inputPanel.add(nameString);
        
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
        
        //change design
        Font fon = new Font("Courier", Font.PLAIN, 12);
        Color colo = Color.getHSBColor(0, 30, 200);
        Font fonBold = new Font("Courier", Font.BOLD, 14);
        connect.setFont(fon);
        clientName.setFont(fon);
        ipLabel.setFont(fon);
        instrLabel.setFont(fonBold);
        inputPanel.setBackground(colo);
        topPanel.setBackground(colo);
        totalPanel.setBackground(colo);
        
        
        
        add(totalPanel);
    }
    
    
    

}
