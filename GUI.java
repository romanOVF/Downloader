// Created oct 27 sat 2018

// https://stackoverflow.com/questions/1912758/how-to-add-a-popup-menu-to-a-jtextfield
// http://www.java2s.com/Code/Java/Swing-JFC/Apopupmenuissometimescalledacontextmenu.htm
// https://stackoverflow.com/questions/30682416/java-right-click-copy-cut-paste-on-textfield
// http://www.java2s.com/Code/Java/Swing-JFC/DemonstrationofFiledialogboxes.htm

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.Action;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultEditorKit.CopyAction;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;

public class GUI extends Thread {

  public static BorderLayout border;
  public static JFrame frame = new JFrame ();
  public static JPanel windowContent, paneProgress, paneButton;
  public static JLabel labelInfoDownload, labelPropertiesFile, waitBar;
  public static JButton buttonGetProperties, buttonDownload, buttonSave;
  private static ImageIcon alfaSpinner, picSpinner, picProperties, picDownload, picSave;
  public static JTextField fieldAddressURL, fieldFileName;
  public static JPopupMenu popUpMenuContext;

  public static String addressURL;
  public static String fileName;
  public static GUI gui;

  public GUI () {
    setLayout ();
    setPanel ();
    setPictureIcon ();
    setLabel ();
    setButton ();
    setContextMenuFieldURLFile ();
    addContentToPanel ();
    listeners ();
    setFrame ();
  }

  // Methods

  private void setLayout () { border = new BorderLayout (); }

  private void setPanel () {
    windowContent = new JPanel ();
    paneButton = new JPanel ();
    paneProgress = new JPanel ();
  }

  private void setLabel () {
    waitBar = new JLabel ( alfaSpinner );
    paneProgress.setBorder ( new TitledBorder ( "progress" ) );
    windowContent.setLayout ( new BorderLayout () );

    labelInfoDownload = new JLabel ( "  . . ." );
    labelInfoDownload.setFont ( new Font ( "Liberation Sans", Font.BOLD, 16 ) );
    labelInfoDownload.setForeground ( Color.WHITE );

    labelPropertiesFile = new JLabel ( "  . . ." );
    labelPropertiesFile.setFont ( new Font ( "Liberation Sans", Font.BOLD, 16 ) );
    labelPropertiesFile.setForeground ( Color.WHITE );
  }

  private void setButton () {
    buttonSave = new JButton ( "save" );
    //buttonSave = new JButton ( "save", picSave );
    buttonSave.setBackground ( Color.GRAY );
    buttonSave.setForeground ( Color.WHITE );
    buttonGetProperties = new JButton ( "properties", picProperties );
    buttonGetProperties.setBackground ( Color.GRAY );
    buttonGetProperties.setForeground ( Color.WHITE );
    buttonDownload = new JButton ( "Download", picDownload );
    buttonDownload.setBackground ( Color.GRAY );
    buttonDownload.setForeground ( Color.WHITE );
  }

  private void setContextMenuFieldURLFile () {
    fieldAddressURL = new JTextField ();
    fieldFileName = new JTextField ( "file.*" );
    popUpMenuContext = new JPopupMenu ();

    Action copy = new DefaultEditorKit.CopyAction ();
    copy.putValue ( Action.NAME, "Copy" );
    copy.putValue ( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke ( "control C" ) );
    popUpMenuContext.add ( copy );

    Action paste = new DefaultEditorKit.PasteAction ();
    paste.putValue ( Action.NAME, "Paste" );
    paste.putValue ( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke ( "control V" ) );
    popUpMenuContext.add ( paste );

    Action cut = new DefaultEditorKit.CutAction ();
    cut.putValue ( Action.NAME, "Cut" );
    cut.putValue ( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke ( "control X" ) );
    popUpMenuContext.add ( cut );

    Action selectAll = new SelectAll ();
    popUpMenuContext.add ( selectAll );

    frame.addMouseListener ( new MouseAdapter () {
      public void mouseReleased ( MouseEvent e ) {
        if ( e.getButton () == e.BUTTON3 ) {
          popUpMenuContext.show ( e.getComponent (), e.getX (), e.getY () );
        }
      }
    } );
    fieldAddressURL.setComponentPopupMenu ( popUpMenuContext );
    fieldFileName.setComponentPopupMenu ( popUpMenuContext );
  }

  private static class SelectAll extends TextAction {
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

  private void setPictureIcon () {
    alfaSpinner = new ImageIcon ( getClass ().getResource ( "pic/alfa_193x24.png" ) );
    picSpinner = new ImageIcon ( getClass ().getResource ( "pic/spinner_warning_by_193x24.gif" ) );
    picProperties = new ImageIcon ( getClass ().getResource ( "pic/shelf.png" ) );
    picDownload = new ImageIcon ( getClass ().getResource ( "pic/download_00.png" ) );
    //picSave = new ImageIcon ( getClass ().getResource ( "pic/save.png" ) );
  }

  public void setSpinner () {
    waitBar.setIcon ( picSpinner );
  }

  public void setAlfaSpinner () {
    waitBar.setIcon ( alfaSpinner );
  }

  private void addContentToPanel () {
    paneProgress.setLayout ( new GridLayout ( 3, 2, 0, 0 ) );
    paneProgress.setBackground ( Color.GRAY );
    paneProgress.add ( labelInfoDownload );
    paneProgress.add ( waitBar );
    paneProgress.add ( labelPropertiesFile );

    paneButton.setLayout ( new GridLayout ( 3, 0, 0, 0 ) );
    paneButton.add ( buttonGetProperties );
    paneButton.add ( buttonDownload );
    paneButton.add ( buttonSave );

    windowContent.add ( fieldAddressURL, BorderLayout.NORTH );
    windowContent.add ( fieldFileName, BorderLayout.SOUTH );
    windowContent.add ( paneButton, BorderLayout.EAST );
    windowContent.add ( paneProgress, BorderLayout.CENTER );
  }

  private void listeners () {
    buttonGetProperties.addActionListener ( ( ActionEvent event ) -> {
      System.out.println ( "button properties" );
      Downloader down = new Downloader ( gui );
      down.ADDRESS_URL = fieldAddressURL.getText ();
      down.FILE_NAME = fieldFileName.getText ();
      down.setKey ( false );
      down.start ();
    } ); // end of adapter

    buttonDownload.addActionListener ( ( ActionEvent event ) -> {
      System.out.println ( "button download" );
      Downloader down = new Downloader ( gui );
      down.ADDRESS_URL = fieldAddressURL.getText ();
      down.FILE_NAME = fieldFileName.getText ();
      down.setKey( true );
      down.start ();
    } ); // end of adapter

    buttonSave.addActionListener ( new SaveFile () );
  }

  class SaveFile implements ActionListener {
    public void actionPerformed ( ActionEvent e ) {
      JFileChooser choose = new JFileChooser ();
      // Demonstrate "Save" dialog:
      int returnValue = choose.showSaveDialog ( windowContent );
      if ( returnValue == JFileChooser.APPROVE_OPTION ) {
        fieldFileName.setText ( choose.getCurrentDirectory ().toString () + "/" + choose.getSelectedFile ().getName () );
      }
      if ( returnValue == JFileChooser.CANCEL_OPTION ) {
        fieldFileName.setText("You pressed cancel");
      }
    }
  }

  private void setFrame () {
    frame.setTitle ( "File downloader v.00" );
    frame.setContentPane ( windowContent );
    frame.setSize ( 500, 180 );
    frame.setResizable ( true );
    frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
    frame.setVisible ( true );
  }

}
