/* Pogramación interactiva
 * Autor: Diego Fabián Ledesma - 1928161
 * Miniproyecto 3: Juego de palabras. */
package juegoDePalabras;

public class ControlJuegoDePalabras {

	private WordGenerator wordGen;
	private FilesManager filesManager;
	private int level, series;
	private String[] words, arrData;
	private String currentPlayer, strPlayers;
	
	
	public ControlJuegoDePalabras() {
		wordGen = new WordGenerator();
		filesManager = new FilesManager();
		level = -1;
		currentPlayer = "";
	}

	public String[] startGame(String player) {
		currentPlayer = player;
		series = 0;
		strPlayers = filesManager.readPlayers();
		for (int j = 0; j < strPlayers.split("\n").length; j++) {
			if (strPlayers.split("\n")[j].equals(player) ) {
				level = Integer.parseInt(player.split("\n")[j].split(" ")[1]);
			}
		}
		words = wordGen.generateWords((level + 1) * 2);
		return words;
	}
	
	public String[] nextSeries() {
		if (series == 0) {
			series = 1;
		}else {
			series = 0;
			this.increaseLevel();
		}
		
		return wordGen.generateWords((level + 1) * 2);
	}
	
	
	/*Changes the level of a player in the gameData file.*/
	public void increaseLevel() {
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
		
		filesManager.testWriting(strPlayers);
	}


}