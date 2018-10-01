// Created sep 30 sun 2018

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import javax.swing.*;

public class GUI extends JFrame {

  public static BorderLayout border;
  public static JPanel windowContent;
  public static JLabel label;

  public GUI () {
      initUI ();
      setTitle ( "File downloader v.00" );
      setContentPane ( windowContent );
      setSize ( 350, 80 );
      setResizable ( true );
      setDefaultCloseOperation ( EXIT_ON_CLOSE );
      setLocationRelativeTo ( null );
      setVisible ( true );
  }

  public void close () {
    try {
      Thread.sleep ( 5000 );
    } catch ( InterruptedException e ) { e.printStackTrace (); }
    System.exit ( 0 );
  }

  private void initUI () {
    windowContent = new JPanel ();
    label = new JLabel ( ". . .               ", SwingConstants.CENTER ); // 20 times
    label.setFont ( new Font ( "Liberation Sans", Font.BOLD, 24 ) );
    label.setForeground ( Color.WHITE );
    windowContent.setBackground ( Color.DARK_GRAY );
    windowContent.add ( label );
  }
}
