/* Pogramación interactiva
 * Autor: Diego Fabián Ledesma - 1928161
 * Miniproyecto 3: Juego de palabras. */

package juegoDePalabras;

import java.awt.EventQueue;

// TODO: Auto-generated Javadoc
/**
 * The Class MainJuegoDePalabras.
 */
public class MainJuegoDePalabras {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				GUIJuegoDePalabras gui = new GUIJuegoDePalabras();
			}
    	});
	}
}
