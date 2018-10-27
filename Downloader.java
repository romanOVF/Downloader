// Created oct 27 sat 2018
// https://docs.oracle.com/javase/tutorial/java/data/numberformat.html

//ADDRESS_URL = "https://archive.org/download/Episode1-IntroductionToThePodcast/MBp1.mp3";
//ADDRESS_URL = "https://archive.org/download/PlanetoftheApes/Retroist-154-The-Planet-of-the-Apes.mp3";
// https://archive.org/details/Episode1-IntroductionToThePodcast

// https://images-na.ssl-images-amazon.com/images/I/610CpdfoH5L.png

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.net.URL;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

public class Downloader extends Thread {

  public static String ADDRESS_URL;
  public static String FILE_NAME;
  private static boolean key;
  private static GUI gui = new GUI ();

  public Downloader ( GUI guit ) { this.gui = gui; }

  public void setKey ( boolean key ) { this.key = key; }

  public void run () {
    double amount = 0;
    String infoAmount = null;
    String output = null;
    gui.setSpinner ();
    DecimalFormat formatterMBDownload = new DecimalFormat ( "loaded    .## MB" );
    DecimalFormat formatterKBDownload = new DecimalFormat ( "loaded   ###.## KB" );
    DecimalFormat formatterMBProperties = new DecimalFormat ( "file size    .## MB" );
    DecimalFormat formatterKBProperties = new DecimalFormat ( "file size   ###.## KB" );

    if ( key ) { // to get file - push button "Download"
      try (
            BufferedInputStream in = new BufferedInputStream ( new URL ( ADDRESS_URL ).openStream () );
            FileOutputStream fileOutputStream = new FileOutputStream ( FILE_NAME );
      ) {
          // get info of file
          amount = ( ( new URL ( ADDRESS_URL ).openConnection ().getContentLength () ) / 1000.0 );
          // get file
          byte [] dataBuffer = new byte [ 1024 ];
          int bytesRead;
          double i = 0;
          while ( ( bytesRead = in.read ( dataBuffer, 0, 1024 ) ) != -1 ) {
            fileOutputStream.write ( dataBuffer, 0, bytesRead );
            i++; // received bytes
            // formating amount to KB
            if ( i < 1000 ) {
              output = formatterKBDownload.format ( i );
              gui.labelInfoDownload.setText ( output );
              System.out.printf ( "%.2f KB\n", i );
            }
              else { // formating amount to MB
                output = formatterMBDownload.format ( i / 1000.0 );
                gui.labelInfoDownload.setText ( output );
                System.out.printf ( "%.2f MB\n", ( i / 1000.0 ) );
              }
            //
          }
        } catch ( IOException e ) { System.out.println ( e ); }
    }
      else { // to get info of file - push button "properties"
        try (
              BufferedInputStream in = new BufferedInputStream ( new URL ( ADDRESS_URL ).openStream () );
        ) {
          amount = ( ( new URL ( ADDRESS_URL ).openConnection ().getContentLength () ) / 1000.0 );
          System.out.printf ( "%.2f " + " MB\n", amount / 1000.0 );

          if ( amount < 1000 ) {
            output = formatterKBProperties.format ( amount );
            gui.labelPropertiesFile.setText ( output );
          }
            else {
              output = formatterMBProperties.format ( amount / 1000.0 );
              gui.labelPropertiesFile.setText ( output );
            }
          } catch ( IOException e ) { System.out.println ( e ); }
      }
        gui.setAlfaSpinner ();

        if ( key ) { // get size loaded file
          if ( amount < 1000 ) {
            System.out.println ( amount + " KB" );
            output = formatterKBDownload.format ( amount );
            gui.labelInfoDownload.setText ( output );
            gui.labelPropertiesFile.setText ( "" );
          }
            else {
              System.out.println ( ( amount / 1000.0 ) + " MB" );
              output = formatterMBDownload.format ( amount / 1000.0 );
              gui.labelInfoDownload.setText ( output );
              gui.labelPropertiesFile.setText ( "" );
            }
        }
        //System.out.println ( amount ); // the line checks value while debugging
        System.out.println ( FILE_NAME );
        System.out.println ( Thread.currentThread () );
        JOptionPane.showMessageDialog ( null, "That's All!" );
        System.out.println ( "DONE!" );
      }
}
