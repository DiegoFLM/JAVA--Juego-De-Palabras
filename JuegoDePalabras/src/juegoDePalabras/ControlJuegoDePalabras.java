/* Pogramación interactiva
 * Autor: Diego Fabián Ledesma - 1928161
 * Miniproyecto 3: Juego de palabras. */
package juegoDePalabras;

public class ControlJuegoDePalabras() {

	private WordGenerator wordGen;
	private FilesManager filesManager;
	private String user;
	private int level, series;

	
	
	public ControlJuegoDePalabras(String user, int level) {
		wordGen = new WordGenerator();
		filesManager = new FilesManager();
	}

}
