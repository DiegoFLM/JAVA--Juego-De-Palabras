/* Pogramación interactiva
 * Autor: Diego Fabián Ledesma - 1928161
 * Miniproyecto 3: Juego de palabras. */

package juegoDePalabras;

import java.awt.EventQueue;

public class MainJuegoDePalabras {

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
