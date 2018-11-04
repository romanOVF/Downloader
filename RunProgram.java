// Created nov 04 sun 2018
// reset && javac -d bin RunProgram.java && java -cp bin RunProgram

import javax.swing.SwingUtilities;

public class RunProgram {
  public static void main ( String [] args ) {
    /*new GUI ();
    System.out.println ( Thread.currentThread () );*/

    SwingUtilities.invokeLater ( new Runnable () {

			@Override
			public void run () {
				new GUI ();
      }
		} );
  }
}
