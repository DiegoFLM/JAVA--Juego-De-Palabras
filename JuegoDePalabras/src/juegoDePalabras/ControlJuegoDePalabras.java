/* Pogramación interactiva
 * Autor: Diego Fabián Ledesma - 1928161
 * Miniproyecto 3: Juego de palabras. */
package juegoDePalabras;

import java.util.IllegalFormatCodePointException;

import javax.swing.JOptionPane;

public class ControlJuegoDePalabras {

	private WordGenerator wordGen;
	private FilesManager filesManager;
	private int level, series;
	private String[] words, arrData, rightWords;
	private String currentPlayer, strPlayers;
	
	
	public ControlJuegoDePalabras() {
		wordGen = new WordGenerator();
		filesManager = new FilesManager();
		level = -1;
		currentPlayer = "";
		rightWords = new String[0];
	}

	/*Finds the level of the specified player. The player must exist in the file gameData in
	 * order to use this method.*/
	public void startGame(String player) {
		currentPlayer = player;
		series = 0;
		strPlayers = filesManager.readPlayers();
		for (int j = 0; j < strPlayers.split("\n").length; j++) {
			if (strPlayers.split("\n")[j].split(" ")[0].equals(currentPlayer) ) {
				JOptionPane.showMessageDialog(null, "start level: " + level);
				level = Integer.parseInt(strPlayers.split("\n")[j].split(" ")[1]);
			}
		}
		rightWords = new String[calculateWords()];
		//words = wordGen.generateWords(calculateWords());
	}
	
	/*Change between series*/
	public String[] nextSeries() {
		if (series == 0) {
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
	
	
	/*Verifies if the writtenWord is right, if so, returns 1, if its wrong, returns 0. 
	 * But if the player just wrote all the series words right, it returns 2.*/
	public int verifyWord(String writtenWord) {
		for (int j = 0; j < words.length; j++) {
			if (words[j].equals(writtenWord)) {
				//JOptionPane.showMessageDialog(null, rightWords.length);
				for (int k = 0; k < rightWords.length; k++) {
					//JOptionPane.showMessageDialog(null, rightWords.length);
					if(rightWords[k] == null) {
						if (k == (rightWords.length - 1)) {
							rightWords[k] = words[j];
							return 2; //All the series words have been written.
						}else {
							rightWords[k] = words[j];
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
	
	
	
	/*Increase in 1 the level of a player in both gameData file and the attribute level of the object of this class.*/
	private void increaseLevel() {
		level++;
		series = 1;
		strPlayers = "";
		arrData = filesManager.readPlayers().split("\n");
		
		for (int d = 0; d < arrData.length; d++) {
			if (arrData[d].split(" ")[0].equals(currentPlayer)) {
				strPlayers += arrData[d].split(" ")[0] + " " + String.valueOf(1 + Integer.parseInt(arrData[d].split(" ")[1])) + "\n";
			}else {
				strPlayers += arrData[d] + "\n";
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
	public String getRightWords() {
		String chainRW = "";
		
		for (int j = 0; j < rightWords.length; j++) {
			if (rightWords[j] == null) {
				break;
			}
			chainRW += rightWords[j] + "\n";
		}
		
		return chainRW; 
	}
	
	public String[] getWords() {
		 return words;
	}

	/*Determines if the player lost when the time for writing words is over;
	 * If the player lost, returns 0; If the player passed to the next level, returns 1.*/
	public int checkGameState() {
		if (level == 1) {
			if (this.howManyRightWords() >= 7) {
				return 1;
			} else {
				return 0;
			}
		} else {
			if (this.howManyRightWords() >= ((level + 1) * 3)) {
				return 1;
			}else {
				return 0;
			}
		}
	}
	
	/*Calculates how many words will appear in each series based on the level.*/
	public int howManyRightWords() {
		int howManyRW = 0;
		for (int j = 0; j < rightWords.length; j++) {
			if (!(rightWords[j] == null)) {
				howManyRW++;
			}else {
				break;
			}
		}
		return howManyRW;
	}
	
	/*Defines how many words will appear in each series based on the specified level.*/
	private int calculateWords() {
		if (level >= 5) {
			return 12;
		} else {
			return ((level + 1) * 2);
		}
	}
	
}