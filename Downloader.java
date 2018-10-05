// Created oct 05 fri 2018
// https://docs.oracle.com/javase/tutorial/java/data/numberformat.html

//FILE_URL = "https://archive.org/download/Episode1-IntroductionToThePodcast/MBp1.mp3";
//FILE_URL = "https://archive.org/download/PlanetoftheApes/Retroist-154-The-Planet-of-the-Apes.mp3";
// https://archive.org/details/Episode1-IntroductionToThePodcast

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.io.BufferedInputStream;

import java.text.DecimalFormat;

public class Downloader extends Thread {
  public static String FILE_URL;
  public static String FILE_NAME;
  public static boolean key;

  public Downloader ( String FILE_URL, String FILE_NAME, boolean key ) {
    this.FILE_URL = FILE_URL;
    this.FILE_NAME = FILE_NAME;
    this.key = key;
  }
  public void run () {
    double amount = 0;
    String infoAmount = null;
    String output = null;
    GUI gui = new GUI ();
    gui.setSpinner ();
    DecimalFormat formatterMB = new DecimalFormat ( "    .## MB" );
    DecimalFormat formatterKB = new DecimalFormat ( " ###.## KB" );

    if ( key ) { // get file ( push button "Download" )
      try (
            BufferedInputStream in = new BufferedInputStream ( new URL ( FILE_URL ).openStream () );
            FileOutputStream fileOutputStream = new FileOutputStream ( FILE_NAME );
      ) {
          // get info of file
          amount = ( ( new URL ( FILE_URL ).openConnection ().getContentLength () ) / 1000.0 );
          // get file
          byte [] dataBuffer = new byte [ 1024 ];
          int bytesRead;
          double i = 0;
          while ( ( bytesRead = in.read ( dataBuffer, 0, 1024 ) ) != -1 ) {
            fileOutputStream.write ( dataBuffer, 0, bytesRead );
            i++; // received bytes
            // formating amount to KB
            if ( i < 1000 ) {
              output = formatterKB.format ( i * 1.024 );
              gui.label.setText ( output );
              System.out.printf ( "%.2fKB\n", ( i * 1.024 ) );
            }
              else { // formating amount to MB
                output = formatterMB.format ( ( i * 1.024 ) / 1000.0 );
                gui.label.setText ( output );
                System.out.printf ( "%.2f MB\n", ( ( i * 1.024 ) / 1000.0 ) );
              }
            //
          }
        } catch ( IOException e ) {
          System.out.println ( e );
        }
    }
      else { // get info of file ( push button "properties" )
        try (
              BufferedInputStream in = new BufferedInputStream ( new URL ( FILE_URL ).openStream () );
        ) {
          amount = ( ( new URL ( FILE_URL ).openConnection ().getContentLength () ) / 1000.0 );
          System.out.printf ( "%.2f " + "MB\n", amount / 1000.0 );

          if ( amount < 1000 ) {
            System.out.println ( amount );
            output = formatterKB.format ( amount );
            gui.label.setText ( output );
          }
            else {
              System.out.println ( amount / 1000.0 );
              output = formatterMB.format ( amount / 1000.0 );
              gui.label.setText ( output );
            }
          } catch ( IOException e ) {
            System.out.println ( e );
          }
      }
        gui.unSetSpinner ();
        if ( amount < 1000 ) {
          System.out.println ( amount );
          output = formatterKB.format ( amount );
          gui.label.setText ( output );
        }
          else {
            System.out.println ( amount / 1000.0 );
            output = formatterMB.format ( amount / 1000.0 );
            gui.label.setText ( output );
          }
        //System.out.println ( amount );
        System.out.println ( FILE_NAME );
      }
}
