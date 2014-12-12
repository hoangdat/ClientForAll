package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Creates frame and set its properties.
 * 
 * @author www.gametutorial.net
 */

public class Window extends JFrame{
        
    private Window()
    {
        Dimension screenSz = Toolkit.getDefaultToolkit().getScreenSize();
        
        //setUndecorated(true);
        //setBounds(0, 0, screenSz.width, screenSz.height);
        setBounds(0, 0, 480, 320);
        
        // Exit the application when user close frame.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
//        JTextField txtIP = new JTextField();
//        txtIP.setPreferredSize(new Dimension(175, 100));
//        
//        JButton btnConnect = new JButton("Connnect");
//        this.getContentPane().add(txtIP, BorderLayout.CENTER);
//        this.getContentPane().add(btnConnect, BorderLayout.SOUTH);
//        
//        btnConnect.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                setContent();
//            }
//        });
        // Creates the instance of the Framework.java that extends the Canvas.java and puts it on the frame.
        this.setContentPane(new Framework());
        
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        // Use the event dispatch thread to build the UI for thread-safety.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }
    
//    public void setContent() {
//        this.getContentPane().removeAll();
//        this.setContentPane(new Framework());
//        this.revalidate();
//        this.repaint();
//    }
}
