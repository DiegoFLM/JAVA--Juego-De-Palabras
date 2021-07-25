/* Pogramación interactiva
 * Autor: Diego Fabián Ledesma - 1928161
 * Miniproyecto 3: Juego de palabras. */

package juegoDePalabras;
import java.util.Random;


// TODO: Auto-generated Javadoc
/**
 * The Class WordGenerator. Generates string arrays of random words taken from the wordBank file.
 */
public class WordGenerator {
	
	private String[] wordBank, words;
	private String auxWords;
	private Random random;
	private int auxGenerateWords, auxComparison;
	private FilesManager filesManager;
	
	/**
	 * Instantiates a new word generator.
	 */
	public WordGenerator() {
		random = new Random();
		filesManager = new FilesManager();
		auxWords = filesManager.getWords();
		wordBank = auxWords.split("\n");
	}
	
	/**
	 * Receives an integer and returns an array of unrepeated words (taken from the wordBank file) with the size of the received integer.
	 *
	 * @param numberOfWords the number of words
	 * @return the String[] (string array) of words.
	 */
	public String[] generateWords(int numberOfWords) {
		words = new String[numberOfWords];
		for (int j = 0; j < numberOfWords;) {
			auxGenerateWords = random.nextInt(wordBank.length);
			auxComparison = 0;
			for(int k = 0; k < j; k++) {
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
	
	
	/**
	 * Prints the words array to console.
	 */
	public void printToConsole() {
		for (int j = 0; j < words.length; j++) {
			System.out.println(words[j]);
		}
	}

}
