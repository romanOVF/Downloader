// Created oct 18 thu 2018

// https://stackoverflow.com/questions/1912758/how-to-add-a-popup-menu-to-a-jtextfield
// http://www.java2s.com/Code/Java/Swing-JFC/Apopupmenuissometimescalledacontextmenu.htm
// https://stackoverflow.com/questions/30682416/java-right-click-copy-cut-paste-on-textfield

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultEditorKit.CopyAction;
import javax.swing.text.TextAction;

public class GUI extends Thread {

  public static BorderLayout border;
  public static JFrame frame = new JFrame ();
  public static JPanel windowContent, paneButton;
  public static JLabel label, waitBar;
  public static JButton buttonGetProperties, buttonDownload;
  private static ImageIcon alfa, picSpinner, picProperties, picDownload;
  public static JTextField fieldAddressURL, fieldFileName;
  public static JPopupMenu contextMenu;
  public static JMenuItem pastMenuItem;

  public static String addressURL;
  public static String fileName;

  public GUI () {
      initUI ();
      frame.setTitle ( "File downloader v.00" );
      frame.setContentPane ( windowContent );
      frame.setSize ( 500, 180 );
      frame.setResizable ( true );
      frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
      frame.setVisible ( true );
  }

  private void initUI () {
    alfa = new ImageIcon ( getClass ().getResource ( "pic/alfa_193x24.png" ) );
    picSpinner = new ImageIcon ( getClass ().getResource ( "pic/spinner_warning_by_193x24.gif" ) );
    picProperties = new ImageIcon ( getClass ().getResource ( "pic/shelf.png" ) );
    picDownload = new ImageIcon ( getClass ().getResource ( "pic/download_00.png" ) );
    border = new BorderLayout ();
    windowContent = new JPanel ();
    paneButton = new JPanel ();
    waitBar = new JLabel ( alfa );
    windowContent.setLayout ( new BorderLayout () );

    label = new JLabel ( "  . . ." );
    label.setFont ( new Font ( "Liberation Sans", Font.BOLD, 16 ) );
    label.setForeground ( Color.WHITE );

    buttonGetProperties = new JButton ( "properties", picProperties );
    buttonGetProperties.setBackground ( Color.GRAY );
    buttonGetProperties.setForeground ( Color.WHITE );
    buttonDownload = new JButton ( "Download", picDownload );
    buttonDownload.setBackground ( Color.GRAY );
    buttonDownload.setForeground ( Color.WHITE );

    fieldAddressURL = new JTextField ();
    fieldFileName = new JTextField ( "file.*" );
    contextMenu = new JPopupMenu ();

    Action copy = new DefaultEditorKit.CopyAction ();
    copy.putValue ( Action.NAME, "Copy" );
    copy.putValue ( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke ( "control C" ) );
    contextMenu.add ( copy );

    Action paste = new DefaultEditorKit.PasteAction ();
    paste.putValue ( Action.NAME, "Paste" );
    paste.putValue ( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke ( "control V" ) );
    contextMenu.add ( paste );

    Action cut = new DefaultEditorKit.CutAction ();
    cut.putValue ( Action.NAME, "Cut" );
    cut.putValue ( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke ( "control X" ) );
    contextMenu.add ( cut );

    Action selectAll = new SelectAll ();
    contextMenu.add ( selectAll );

    frame.addMouseListener ( new MouseAdapter () {
      public void mouseReleased ( MouseEvent e ) {
        if ( e.getButton () == e.BUTTON3 ) {
          contextMenu.show ( e.getComponent (), e.getX (), e.getY () );
        }
      }
    });
    fieldAddressURL.setComponentPopupMenu ( contextMenu );
    fieldFileName.setComponentPopupMenu ( contextMenu );

    paneButton.setLayout ( new GridLayout ( 2, 1, 0, 0 ) );
    paneButton.add ( buttonGetProperties );
    paneButton.add ( buttonDownload );

    windowContent.setBackground ( Color.DARK_GRAY );
    windowContent.add ( fieldAddressURL, BorderLayout.NORTH );
    windowContent.add ( fieldFileName, BorderLayout.SOUTH );
    windowContent.add ( label, BorderLayout.WEST );
    windowContent.add ( paneButton, BorderLayout.EAST );
    windowContent.add ( waitBar, BorderLayout.CENTER );


    buttonGetProperties.addActionListener ( ( ActionEvent event ) -> {
      System.out.println ( "button properties" );
      addressURL = fieldAddressURL.getText ();
      fileName = fieldFileName.getText ();
      Downloader down = new Downloader ( false );
      down.start ();
    }); // end of adapter

    //////////////////////////////////////////////////////////////////////////
    buttonDownload.addActionListener ( ( ActionEvent event ) -> {
      System.out.println ( "button download" );
      Downloader down = new Downloader ( true );
      down.start ();
    }); // end of adapter
  }

  static class SelectAll extends TextAction {
        public SelectAll () {
            super ( "Select All" );
            putValue ( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke ( "control S" ) );
        }

        public void actionPerformed ( ActionEvent e ) {
            JTextComponent component = getFocusedComponent ();
            component.selectAll ();
            component.requestFocusInWindow ();
        }
    }

  public void setSpinner () {
    waitBar.setIcon ( picSpinner );
  }

  public void unSetSpinner () {
    waitBar.setIcon ( alfa );
  }

  public void close () {
    try {
      Thread.sleep ( 3000 );
    } catch ( InterruptedException e ) { e.printStackTrace (); }
    System.exit ( 0 );
  }

}
