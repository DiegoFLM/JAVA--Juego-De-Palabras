/* Pogramación interactiva
 * Autor: Diego Fabián Ledesma - 1928161
 * Miniproyecto 3: Juego de palabras. */

package juegoDePalabras;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class FilesManager {

	private FileReader fileRead;
	private BufferedReader input;
	private FileWriter fileWriter;
	private BufferedWriter output;
	
	/*Players are stored with a format: Each line corresponds to a player, followed by a space and then the level of the player. This
	 * method returns a String with the specified format*/
	public String readPlayers() {
		
		String chain = "";
		
		try {
			fileRead = new FileReader("src/resources/gameData");
			input = new BufferedReader(fileRead);
			
			String text = input.readLine();
			
			while(text != null) {
				chain += text + "\n";
				text = input.readLine();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return chain; 
	}
	
	

	/*Players are stored with a format: Each line corresponds to a player, followed by a space and then the level of the player.
	 * This method receives the name of a player and check if the player is already stored, if so, it returns the level of the player; 
	 * if not, adds the player to the file with level 1 and returns 1.*/
	public int writePlayer(String line) {
		try {
			fileWriter = new FileWriter("src/resources/gameData", true); /*The second parameter determine if the file will be overwritten or just get
																		text added to the existing. False means overwrite*/
			output = new BufferedWriter(fileWriter);
			
			String[] players = this.readPlayers().split("\n");
			
			boolean aux = true;
			for (int j = 0; j < players.length; j++) {
				//System.out.println(players[j].split(" ")[0]);
				if (line.equals(players[j].split(" ")[0]) ) {
					aux = false;
					return Integer.parseInt(players[j].split(" ")[1]);
				}
			}
			
			if (aux) {
				output.write(line + " " + "1");
				output.newLine();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 1;
	}
	
	
	public String getWords() {
		String words = "";
		
		try {
			fileRead = new FileReader("src/resources/wordBank");
			input = new BufferedReader(fileRead);
			
			String word = input.readLine();
			
			while (word != null) {
				words += word + "\n";
				word = input.readLine();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return words;	
	}
	
	
	public void deletePlayers() {
		try {
			String nothing = "";
			fileWriter = new FileWriter("src/resources/gameData", false);
			
			output = new BufferedWriter(fileWriter);
			
			output.write(nothing);
			output.newLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testWriting(String data) {
		try {
			fileWriter = new FileWriter("src/resources/gameData", false); /*The second parameter determine if the file will be overwritten or just get
																		text added to the existing. False means overwrite*/
			output = new BufferedWriter(fileWriter);
			
			output.write(data);
			output.newLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
