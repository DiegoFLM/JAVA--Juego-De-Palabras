/* Pogramaci�n interactiva
 * Autor: Diego Fabi�n Ledesma - 1928161
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
	
	public String readFile() {
		
		String chain = "";
		
		try {
			fileRead = new FileReader("src/resources/gameData");
			input = new BufferedReader(fileRead);
			
			String text = input.readLine();
			
			while(text != null) {
				chain += "\n" + text;
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

	
	public void writeFile(String line) {
		try {
			fileWriter = new FileWriter("src/resources/gameData", true); /*The second parameter determine if the file will be overwited or just get
																		text added to the existing. False means overwite*/
			output = new BufferedWriter(fileWriter);
			
			output.write(line);
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