// Created oct 04 thu 2018
//FILE_URL = "https://archive.org/download/Episode1-IntroductionToThePodcast/MBp1.mp3";
//FILE_URL = "https://archive.org/download/PlanetoftheApes/Retroist-154-The-Planet-of-the-Apes.mp3";
// https://archive.org/details/Episode1-IntroductionToThePodcast

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.io.BufferedInputStream;

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
    String info = null;
    GUI gui = new GUI ();

    if ( key ) {
      try (
            BufferedInputStream in = new BufferedInputStream ( new URL ( FILE_URL ).openStream () );
            FileOutputStream fileOutputStream = new FileOutputStream ( FILE_NAME );
      ) {
          info = ( ( new URL ( FILE_URL ).openConnection ().getContentLength () ) / 1000000.0 ) + " MB";
          System.out.println ( info );
          gui.label.setText ( info );

          byte [] dataBuffer = new byte [ 1024 ];
          int bytesRead;
          int i = 0;
          while ( ( bytesRead = in.read ( dataBuffer, 0, 1024 ) ) != -1 ) {
            fileOutputStream.write ( dataBuffer, 0, bytesRead );
            i++;
            String receivedData = String.valueOf ( ( i * 1.024 ) / 1000.0 );
            gui.label.setText ( receivedData );
            System.out.println ( ( i * 1.024 ) / 1000.0 );
          }
        } catch ( IOException e ) {
          System.out.println ( e );
        }
    }
      else {
        try (
              BufferedInputStream in = new BufferedInputStream ( new URL ( FILE_URL ).openStream () );
        ) {
          info = ( ( new URL ( FILE_URL ).openConnection ().getContentLength () ) / 1000000.0 ) + " MB";
          //System.out.println ( info );
          gui.label.setText ( info );
          } catch ( IOException e ) {
            System.out.println ( e );
          }
      }
        gui.label.setText ( "OK! " + info );
        System.out.println ( "OK! " + FILE_NAME );
      }
}
