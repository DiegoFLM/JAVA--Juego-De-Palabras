package juegoDePalabras;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class GUITemp extends JFrame {

	private JTextField line;
	private JTextArea textArea;
	private FilesManager filesManager;
	private Listener listener;
	private String directoryGameData;
	
	public GUITemp () {
		initGUI();
		
		this.setTitle("Files Management");
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initGUI() {
		filesManager = new FilesManager();
		listener = new Listener();
		
		line = new JTextField(15); //The parameter is the size of the text field in characters.
		line.addActionListener(listener);
		this.add(line, BorderLayout.NORTH);
		textArea = new JTextArea(10, 30);
		textArea.setText(filesManager.readPlayers());
		JScrollPane scroll = new JScrollPane(textArea);
		this.add(scroll, BorderLayout.CENTER);
		
	}
	
	
	
	
	private class Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (line.getText().equals("delete")) {
				filesManager.deletePlayers();
				textArea.setText(filesManager.readPlayers());
				line.setText("");
			}else {
				filesManager.writePlayer(line.getText());
				textArea.setText(filesManager.readPlayers());
				line.setText("");
			}
		}
		
	}
	
	
	

}
