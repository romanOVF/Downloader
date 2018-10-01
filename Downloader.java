// Created sep 30 sat 2018

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.io.BufferedInputStream;

public class Downloader extends Thread {

  public void run () {
    GUI instance = new GUI ();
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
        instance.label.setText ( info );

        byte [] dataBuffer = new byte [ 1024 ];
        int bytesRead;
        int i = 0;
        while ( ( bytesRead = in.read ( dataBuffer, 0, 1024 ) ) != -1 ) {
          fileOutputStream.write ( dataBuffer, 0, bytesRead );
          i++;
          String receivedData = String.valueOf ( ( i * 1.024 ) /1000.0 );
          instance.label.setText ( receivedData );
          System.out.println ( ( i * 1.024 ) /1000.0 );
        }
      } catch ( IOException e ) {
        System.out.println ( e );
      }
      instance.label.setText ( "OK! " + info );
      System.out.println ( "OK! " + FILE_NAME );
      instance.close ();
  }
}
