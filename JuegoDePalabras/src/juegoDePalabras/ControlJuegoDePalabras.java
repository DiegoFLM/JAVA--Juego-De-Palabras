/* Pogramación interactiva
 * Autor: Diego Fabián Ledesma - 1928161
 * Miniproyecto 3: Juego de palabras. */
package juegoDePalabras;

import java.util.IllegalFormatCodePointException;

import javax.swing.JOptionPane;

public class ControlJuegoDePalabras {

	private WordGenerator wordGen;
	private FilesManager filesManager;
	private int level, series, levelRW;
	private String[] words, arrData, rightWords;
	private String currentPlayer, strPlayers;
	
	
	public ControlJuegoDePalabras() {
		wordGen = new WordGenerator();
		filesManager = new FilesManager();
		level = -1;
		currentPlayer = "";
		rightWords = new String[0];
		levelRW = 0;
	}

	
	/*Identifies the player and finds the level of the specified player. The player must exist in the file gameData in
	 * order to use this method.*/
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
	
	
	/*Passes to next series, increases the player level when needed and returns an array with the words of the next series.*/
	public String[] nextSeries() {
		if (series == 0) { /*Series is equal to cero only afther the use of startGame(String player) method,
							it doesn't have a meaning in the game.*/
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
	
	
	/*Verifies if the writtenWord is right, if it's right and there are words that haven't been written yet, returns 1. 
	 * If the writtenWord is wrong, returns 0. 
	 * But if the player just wrote all the series words right, it returns 2.*/
	public int verifyWord(String writtenWord) {
		for (int j = 0; j < words.length; j++) {
			if (words[j].equals(writtenWord)) {
				//JOptionPane.showMessageDialog(null, rightWords.length);
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
	
	
	
	/*Increases in 1 the level of a player in both gameData file and the attribute level of the object of this class.*/
	private void increaseLevel() {
		level++;
		levelRW = 0;
		series = 1;
		strPlayers = "";
		arrData = filesManager.readPlayers().split("\n");
		
		for (int d = 0; d < arrData.length; d++) {
			/*This 'if' allows to avoid empty lines in the between players in the gameData file*/
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
	
	
	public int getLevel() {
		return level;
	}
	
	public int getSeries() {
		return (series);
	}
	
	/*Returns a string with each right word written by the user in a diferent line.*/
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
	
	public int getLevelRightWords() {
		return levelRW;
	}
	
	public String[] getWords() {
		 return words;
	}
	

	/*Determines if the player wrote enough words in the current level for not losing the game.
	 *If the player lost, returns 0; If the player passed to the next level, returns 1.
	 *It's function is to determine if the player lost when the time for writing words is over;*/
	public int checkGameState() {
		if (level == 1) {
			if (levelRW >= 7) {  /*levelRW is the number of right words that the player has written in the current level.*/
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
	
	
	/*Defines how many words will appear in the current series based on the specified level.*/
	private int calculateWords() {
		if (level >= 5) {
			return 12;
		} else {
			return ((level + 1) * 2);
		}
	}
	
}