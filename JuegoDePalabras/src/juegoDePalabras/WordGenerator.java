/* Pogramación interactiva
 * Autor: Diego Fabián Ledesma - 1928161
 * Miniproyecto 3: Juego de palabras. */

package juegoDePalabras;

import java.util.Random;

/*WordGenerator generates an array of unrepeated words in order to be used for a series of the game*/
public class WordGenerator {
	
	//Attributes
	private String[] wordBank, words;
	private String auxWords;
	private Random random;
	private int auxGenerateWords, auxComparison;
	private FilesManager filesManager;
	
	public WordGenerator() {
		random = new Random();
		//This wordBank is no longer used, now the words are stored in a text file called wordBank.
		/*wordBank = new String[] 	{"INMENSO", "OFICIAL", "ASPIRAR", "CAMILLA", "SEMILLA", "INFANTIL", "INFLAR", "PASADO", "CONDUCIR", "MORSA",
								"PREGUNTAR", "PRESIDENTE", "LONGITUD", "AVIONES", "OBSERVATORIO", "ARQUITECTURA", "MAIZ", "CAMPESINO", "HELADO", "CEPILLO",
								"CUEVA", "BEBIDA", "SIEMBRA", "SACACORCHOS", "DIENTE", "TERCIARIO", "CALDERA", "DEFENSA", "BLUSA", "LIVIANO",
								"MASA", "COBARDE", "SUBURBIO", "BORRACHO", "CUARTETO", "TARAREAR", "LENGUA", "LADRILLO", "BARANDA", "ACTORES",
								"LIBERAR", "CLAVO", "LABERINTO", "CAPAS", "MUSEO", "HECHIZAR", "HEMISFERIOS", "DROGA", "AGUA", "BANDERA",
								"PARED", "SIGNOS", "NAVEGAR", "VASCO", "AVISO", "RESPIRAR", "ESCAPE", "RESPALDO", "MODA", "ENSUCIAR" 
								};	 */
		
		filesManager = new FilesManager();
		auxWords = filesManager.getWords();
		wordBank = auxWords.split("\n");
		
	}
	
	/*receives an integer and returns an array of unrepeated Strings (taken from the wordBank) with the size of the received integer.*/
	public String[] generateWords(int numberOfWords) {
		words = new String[numberOfWords];
		for (int j = 0; j < numberOfWords;) {
			auxGenerateWords = random.nextInt(wordBank.length);
			auxComparison = 0;
			for(int k = 0; k < (j - 1); k++) {
				if (wordBank[auxGenerateWords].equals(words[k])) {
					auxComparison++;
					break;
				}
			}
			
			if(auxComparison == 0) {
				words[j] = wordBank[auxGenerateWords];
				j++;
			}
		}
		return words;
	}
	
	public void printToConsole() {
		for (int j = 0; j < words.length; j++) {
			System.out.println(words[j]);
		}
	}

}
