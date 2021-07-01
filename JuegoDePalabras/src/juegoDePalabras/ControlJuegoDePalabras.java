/* Pogramación interactiva
 * Autor: Diego Fabián Ledesma - 1928161
 * Miniproyecto 3: Juego de palabras. */
package juegoDePalabras;

public class ControlJuegoDePalabras {

	private WordGenerator wordGen;
	private FilesManager filesManager;
	private int level, series;

	
	
	public ControlJuegoDePalabras() {
		wordGen = new WordGenerator();
		filesManager = new FilesManager();
	}

}
