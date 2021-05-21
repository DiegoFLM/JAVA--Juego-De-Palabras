/* Pogramación interactiva
 * Autor: Diego Fabián Ledesma - 1928161
 * Miniproyecto 3: Juego de palabras. */

package juegoDePalabras;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUIJuegoDePalabras extends JFrame {
	
	private JTextField line;
	private JTextArea textArea;
	private FilesManager filesManager;
	
	public GUIJuegoDePalabras() {
		initGUI();
		
		this.setTitle("Files Manager test");
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initGUI() {
		
		filesManager = new FilesManager();
		line = new JTextField(15);  /*The parameter is the size of the field in number of characters*/
		this.add(line, BorderLayout.NORTH);
		
		textArea = new JTextArea(10, 30);
		JScrollPane scroll = new JScrollPane(textArea);
		this.add(scroll, BorderLayout.CENTER);
		
	}
	
	private class listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	

}
