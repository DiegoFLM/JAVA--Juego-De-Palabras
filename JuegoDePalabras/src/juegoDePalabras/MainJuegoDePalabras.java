/* Pogramaci�n interactiva
 * Autor: Diego Fabi�n Ledesma - 1928161
 * Miniproyecto 2: Juego de palabras. */

package juegoDePalabras;

public class MainJuegoDePalabras {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WordGenerator wordGen = new WordGenerator();
		wordGen.generateWords(5);
		wordGen.printToConsole();
	}

}
