// Created oct 03 wed 2018

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUI extends Thread implements Runnable {

  public static BorderLayout border;
  public static JFrame frame = new JFrame ();
  public static JPanel windowContent, paneButton;
  public static JLabel label, waitBar;
  public static JButton buttonDownload;
  private static ImageIcon picDownload;
  public static JTextField fieldAddress, fieldOut;

  public GUI () {
      initUI ();
      frame.setTitle ( "File downloader v.00" );
      frame.setContentPane ( windowContent );
      frame.setSize ( 400, 180 );
      frame.setResizable ( true );
      frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
      frame.setLocationRelativeTo ( null );
      frame.setVisible ( true );
  }

  private void initUI () {
    picDownload = new ImageIcon ( getClass ().getResource ( "pic/download_00.png" ) );
    border = new BorderLayout ();
    windowContent = new JPanel ();
    paneButton = new JPanel ();
    windowContent.setLayout ( new BorderLayout () );

    label = new JLabel ( ". . .               ", SwingConstants.CENTER ); // 20 times
    label.setFont ( new Font ( "Liberation Sans", Font.BOLD, 16 ) );
    label.setForeground ( Color.WHITE );
    buttonDownload = new JButton ( "Download", picDownload );
    buttonDownload.setBackground ( Color.GRAY );
    buttonDownload.setForeground ( Color.WHITE );

    fieldAddress = new JTextField ();
    fieldOut = new JTextField ( "*.*" );

    paneButton.setLayout ( new GridLayout ( 1, 1, 0, 0 ) );
    paneButton.add ( buttonDownload );

    windowContent.setBackground ( Color.DARK_GRAY );
    windowContent.add ( fieldAddress, BorderLayout.NORTH );
    windowContent.add ( fieldOut, BorderLayout.SOUTH );
    windowContent.add ( label, BorderLayout.WEST );
    windowContent.add ( paneButton, BorderLayout.EAST );

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    buttonDownload.addActionListener ( ( ActionEvent event ) -> {
      System.out.println ( "button download" );
      Downloader down = new Downloader ( fieldAddress.getText (), fieldOut.getText () );
      Thread t1 = new Thread ( down );
      t1.start ();
    }); // end of adapter
  }

  public void close () {
    try {
      Thread.sleep ( 3000 );
    } catch ( InterruptedException e ) { e.printStackTrace (); }
    System.exit ( 0 );
  }
}
