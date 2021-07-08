/* Pogramación interactiva
 * Autor: Diego Fabián Ledesma - 1928161
 * Miniproyecto 3: Juego de palabras. */
package juegoDePalabras;

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

	/*Returns a String array with the first series of the specified player's level. The player must exist in the file gameData in
	 * order to use this method.*/
	public String[] startGame(String player) {
		currentPlayer = player;
		series = 0;
		rightWords = new String[calculateWords(level)];
		strPlayers = filesManager.readPlayers();
		for (int j = 0; j < strPlayers.split("\n").length; j++) {
			if (strPlayers.split("\n")[j].split(" ")[0].equals(currentPlayer) ) {
				level = Integer.parseInt(strPlayers.split("\n")[j].split(" ")[1]);
				
			}
		}
		//System.out.println(level);
		words = wordGen.generateWords(calculateWords(level));
		//wordGen.printToConsole();
		return words;
	}
	
	
	public String[] nextSeries() {
		if (series == 0) {
			series = 1;
		}else {
			series = 0;
			this.increaseLevel();
		}
		rightWords = new String[calculateWords(level)];
		words = wordGen.generateWords(calculateWords(level));
		return words;
	}
	
	/*Verifies if the writtenWord is right, if so, returns 1, if its wrong, returns 0. 
	 * But if the player just wrote all the series words right, it returns 2.*/
	public int verifyWord(String writtenWord) {
		for (int j = 0; j < words.length; j++) {
			if (words[j].equals(writtenWord)) {
				for (int k = 0; k < rightWords.length; k++) {
					if (rightWords[k].equals(writtenWord)) {
						return 0; //The writtenWord had been written already.
					}else if(rightWords[k].equals(null)) {
						if (k == (rightWords.length - 1)) {
							return 2; //All the series words have been written.
						}else {
							rightWords[k] = words[j];
							return 1; //The writtenWord is right, but there are still words that haven't been written.
						}
					}
				}
			}
		}
		return 0; //The writtenWord is wrong.
	} 
	
	
	/*Increase in 1 the level of a player in the gameData file and in the attribute level of the object of this class.*/
	private void increaseLevel() {
		level++;
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
	
	
	/*Defines how many words will appear in each series based on the specified level.*/
	private int calculateWords(int lvl) {
		if (lvl >= 5) {
			return 12;
		} else {
			return ((lvl + 1) * 2);
		}
	}
	
}