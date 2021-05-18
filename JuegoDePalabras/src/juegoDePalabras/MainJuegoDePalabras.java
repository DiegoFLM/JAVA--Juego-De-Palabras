/* Pogramación interactiva
 * Autor: Diego Fabián Ledesma - 1928161
 * Miniproyecto 3: Juego de palabras. */

package juegoDePalabras;

public class MainJuegoDePalabras {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WordGenerator wordGen = new WordGenerator();
		wordGen.generateWords(5);
		wordGen.printToConsole();
	}

}
