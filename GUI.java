// Created oct 01 mon 2018

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
import javax.swing.SwingConstants;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.io.BufferedInputStream;

public class GUI extends Thread {

  public static BorderLayout border;
  public static JFrame frame = new JFrame ();
  public static JPanel windowContent, paneButton;
  public static JLabel label, waitBar;
  public static JButton buttonDownload;
  private static ImageIcon picDownload;

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

    paneButton.setLayout ( new GridLayout ( 1, 1, 0, 0 ) );
    paneButton.add ( buttonDownload );

    windowContent.setBackground ( Color.DARK_GRAY );
    windowContent.add ( label, BorderLayout.WEST );
    windowContent.add ( paneButton, BorderLayout.EAST );

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    buttonDownload.addActionListener ( ( ActionEvent event ) -> {
      System.out.println ( "button download" );
      GUI gui = new GUI ();
      gui.start ();
    }); // end of adapter
  }

  private void close () {
    try {
      Thread.sleep ( 3000 );
    } catch ( InterruptedException e ) { e.printStackTrace (); }
    System.exit ( 0 );
  }

  public void run () {
    String FILE_NAME = "audio.mp3";
    String FILE_URL;
    String info = null;

    FILE_URL = "https://archive.org/download/Episode1-IntroductionToThePodcast/MBp1.mp3";
    //FILE_URL = "https://archive.org/download/PlanetoftheApes/Retroist-154-The-Planet-of-the-Apes.mp3";
    // https://archive.org/details/Episode1-IntroductionToThePodcast
    try (
          BufferedInputStream in = new BufferedInputStream ( new URL ( FILE_URL ).openStream () );
          FileOutputStream fileOutputStream = new FileOutputStream ( FILE_NAME );
    ) {
        info = ( ( new URL ( FILE_URL ).openConnection ().getContentLength () ) / 1000000.0 ) + " MB";
        System.out.println ( info );
        label.setText ( info );

        byte [] dataBuffer = new byte [ 1024 ];
        int bytesRead;
        int i = 0;
        while ( ( bytesRead = in.read ( dataBuffer, 0, 1024 ) ) != -1 ) {
          fileOutputStream.write ( dataBuffer, 0, bytesRead );
          i++;
          String receivedData = String.valueOf ( ( i * 1.024 ) / 1000.0 );
          label.setText ( receivedData );
          System.out.println ( ( i * 1.024 ) / 1000.0 );
        }
      } catch ( IOException e ) {
        System.out.println ( e );
      }
      label.setText ( "OK! " + info );
      System.out.println ( "OK! " + FILE_NAME );
      close ();
  }
}
