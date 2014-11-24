package eecs285.proj4;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmailForm extends JDialog {
    
    private JTextArea addressList;
    private JButton ok;
    private JButton cancel;
    
    private String emailList;
    
    public class EmailFormListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == ok)
            {
                emailList = addressList.getText().trim();
                dispose();
            }
            else if(e.getSource() == cancel)
            {
                emailList = "";
                dispose();
            }
        }
    }
    
    public EmailForm(JFrame mainFrame, String inTitle)
    {
        super(mainFrame, inTitle, true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        emailList = "";
        
        EmailFormListener myListener = new EmailFormListener();
        
        JLabel instrLabel = new JLabel("Enter all of the email addresses of the people "
                + "you wish to allow to submit song requests (separated by commas)");
        
        JScrollPane emailPane = new JScrollPane();
        emailPane.setPreferredSize(new Dimension(300, 300));
        addressList = new JTextArea();
        emailPane.setViewportView(addressList);
        
        ok = new JButton("OK");
        ok.addActionListener(myListener);
        cancel = new JButton("Cancel");
        cancel.addActionListener(myListener);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(ok);
        buttonPanel.add(cancel);
        
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.PAGE_AXIS));
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        totalPanel.add(instrLabel);
        totalPanel.add(emailPane);
        totalPanel.add(buttonPanel);
        
        add(totalPanel);
    }
    
    public String showDialog()
    {
        EmailForm.this.setVisible(true);
        return addressList.getText().trim();
    }
}
