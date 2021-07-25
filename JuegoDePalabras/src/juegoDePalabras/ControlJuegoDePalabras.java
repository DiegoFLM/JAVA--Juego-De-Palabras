/* Pogramación interactiva
 * Autor: Diego Fabián Ledesma - 1928161
 * Miniproyecto 3: Juego de palabras. */
package juegoDePalabras;

import java.util.IllegalFormatCodePointException;

import javax.swing.JOptionPane;

// TODO: Auto-generated Javadoc
/**
 * The Class ControlJuegoDePalabras. This class handles the game state, except for the time. Is responsible for 
 * using the WordGenerator object, modify the players level in the gameData file using a FilesManager object, keeping track
 * of the series and keeping track of the right words written by the player.
 */
public class ControlJuegoDePalabras {
	private WordGenerator wordGen;
	private FilesManager filesManager;
	private int level, series, levelRW;
	private String[] words, arrData, rightWords;
	private String currentPlayer, strPlayers;
	
	
	/**
	 * Instantiates a new controlJuegoDePalabras.
	 */
	public ControlJuegoDePalabras() {
		wordGen = new WordGenerator();
		filesManager = new FilesManager();
		level = -1;
		currentPlayer = "";
		rightWords = new String[0];
		levelRW = 0;
	}

	
	/**
	 * Finds the level of the specified player. The player must exist in the file gameData in order to use this method.
	 *
	 * @param player. Name of the player.
	 */
	public void startGame(String player) {
		currentPlayer = player;
		series = 0;
		levelRW = 0;
		strPlayers = filesManager.readPlayers();
		for (int j = 0; j < strPlayers.split("\n").length; j++) {
			if (strPlayers.split("\n")[j].split(" ")[0].equals(currentPlayer) ) {
				level = Integer.parseInt(strPlayers.split("\n")[j].split(" ")[1]);
			}
		}
		rightWords = new String[calculateWords()];
	}
	
	
	
	
	
	
	/**
	 * Passes to next series, increases the player level when needed.
	 *
	 * @return an String[] array with the words of the next series
	 */
	public String[] nextSeries() {
		if (series == 0) { /*Series is equal to zero only after the use of startGame(String player) method,
							it doesn't have any meaning in the game.*/
			series = 1;
		}else if (series == 1) {
			series = 2;
		} else {
			this.increaseLevel();
		}
		rightWords = new String[calculateWords()];
		words = wordGen.generateWords(calculateWords());
		return words;
	}
	
	
	/**
	 * Verifies if the writtenWord is right.
	 *
	 * @param writtenWord. The word written by the player.
	 * @return if the written word is right and if there are words that haven't been written yet, returns 1. 
	 * If the writtenWord is wrong, returns 0. If the player just wrote all the series words right, it returns 2.
	 */
	public int verifyWord(String writtenWord) {
		for (int j = 0; j < words.length; j++) {
			if (words[j].equals(writtenWord)) {
				for (int k = 0; k < rightWords.length; k++) {
					if(rightWords[k] == null) {
						if (k == (rightWords.length - 1)) {
							rightWords[k] = words[j];
							levelRW++;
							return 2; //All the series words have been written.
						}else {
							rightWords[k] = words[j];
							levelRW++;
							return 1; //The writtenWord is right, but there are still words that haven't been written.
						}
					} else {
						if (rightWords[k].equals(writtenWord)) {
							return 0; //The writtenWord had been written already.
						}
					}
				}
			}
		}
		return 0; //The writtenWord is wrong.
	}
	
	
	
	/**
	 * Increases in 1 the player's level in both gameData file and the attribute level of the object of this class.
	 */
	private void increaseLevel() {
		level++;
		levelRW = 0;
		series = 1;
		strPlayers = "";
		arrData = filesManager.readPlayers().split("\n");
		
		for (int d = 0; d < arrData.length; d++) {
			//This 'if' allows to avoid empty lines in the between players in the gameData file
			if (arrData[d].split(" ")[0].equals("")) {
				continue;
			}
				
			if (arrData[d].split(" ")[0].equals(currentPlayer)) {
				strPlayers += arrData[d].split(" ")[0] + " " + String.valueOf(1 + Integer.parseInt(arrData[d].split(" ")[1])) + "\n";
			}else {
				if (d == arrData.length - 1) {
					strPlayers += arrData[d];
				} else {
					strPlayers += arrData[d] + "\n";
				}
			}
		}
		
		filesManager.overWrite(strPlayers);
	}
	
	
	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Gets the series.
	 *
	 * @return the series
	 */
	public int getSeries() {
		return (series);
	}
	
	/**
	 * Gets the series right words.
	 *
	 * @return a string with each right word written by the user in a different line.
	 */
	public String getSeriesRightWords(){
		String chainRW = "";
		
		for (int j = 0; j < rightWords.length; j++) {
			if (rightWords[j] == null) {
				break;
			}
			chainRW += rightWords[j] + "\n";
		}
		
		return chainRW; 
	}
	
	/**
	 * Gets the number of right words written in the current level by the player.
	 *
	 * @return the number of right words written so far in the current level.
	 */
	public int getLevelRightWords() {
		return levelRW;
	}
	
	/**
	 * Gets a String array with the series words.
	 *
	 * @return the series words.
	 */
	public String[] getWords() {
		 return words;
	}
	

	/**
	 * Determines if the player wrote enough words in the current level for not losing the game.
	 * It's function is to determine if the player lost when the time for writing words is over
	 * 
	 * @return If the player lost, returns 0; If the player passed to the next level, returns 1.
	 */
	public int checkGameState() {
		if (level == 1) {
			if (levelRW >= 7) {  //levelRW is the number of right words that the player has written in the current level.
				return 1;
			} else {
				return 0;
			}
		} else {
			if (levelRW >= ((level + 1) * 3)) {
				return 1;
			}else {
				return 0;
			}
		}
	}
	
	
	/**
	 * Defines how many words will appear in the current series based on the specified level.
	 *
	 * @return the number of words corresponding to the current level.
	 */
	private int calculateWords() {
		if (level >= 5) {
			return 12;
		} else {
			return ((level + 1) * 2);
		}
	}
	
}