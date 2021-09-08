/* Pogramación interactiva
 * Autor: Diego Fabián Ledesma - 1928161
 * Miniproyecto 3: Juego de palabras. */

package juegoDePalabras;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misComponentes.Titulos;

// TODO: Auto-generated Javadoc
/**
 * The Class GUIJuegoDePalabras. GUIJuegoDePalabras hereda de JFrame, y hace uso de la clase Titulos 
 * que se encuentra en el proyecto MisComponentes.
 */
public class GUIJuegoDePalabras extends JFrame{
	private JTextField line, tfPlayerWord;
	private JTextArea textArea, taRightWords;
	private JScrollPane scroll;
	private FilesManager filesManager;
	private JLabel lInstEnterPlayer;
	private JPanel pStart, pOptions, pMain, pGame, pWords, pWriting/*, pWrittenLetters*/;
	private JButton bReset, bExit, bFinishSeries, bRestart;
	private GridBagConstraints constraints;
	private CardLayout cardMain, cardGame;
	private Titulos tGameTitle, tCurrentWord, tLevel, tSeries, tTime, tLetters;
	private ControlJuegoDePalabras control;
	private String[] seriesWords;
	//private char[] charArray;
	private Listener listener;
	//private KeyboardListener KeyboardListener;
	private Timer timerWords, timerSecond;
	private int intWordsCounter, secondsLeft;
	private TimerPanel timerPanel;
	
	/**
	 * Instantiates a new GUIJuegoDePalabras.
	 */
	public GUIJuegoDePalabras() {
		initGUI();
		
		this.setUndecorated(true);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		intWordsCounter = 0;
	}
	

	/**
	 * Initializes main components and shows the starting frame in which the user can see player's names with it's level and
	 * write a name either present on the list or not. If the name written by the user is in the list, the game starts
	 * in the level of the selected player's name, if not, starts in level 1.
	 */
	private void initGUI() {
		this.getContentPane().setLayout(new GridBagLayout());;
        constraints = new GridBagConstraints();
        
		control = new ControlJuegoDePalabras();
		seriesWords = new String[4];
		listener = new Listener();
		//KeyboardListener = new KeyboardListener();
		
		//JPanel Start
		pStart = new JPanel();
		pStart.setLayout(new GridBagLayout());
		
		tGameTitle = new Titulos("JUEGO DE PALABRAS", 60, new Color (50, 50, 100)); //RGB
		lInstEnterPlayer = new JLabel("Introduzca un nombre de usuario");
		
		filesManager = new FilesManager();
		line = new JTextField(30);  /*The parameter is the size of the field in number of characters*/
		line.addActionListener(listener);
		
		constraints.gridx = 0;		
 		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pStart.add(tGameTitle, constraints);
		
		constraints.gridx = 0;		
 		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pStart.add(lInstEnterPlayer, constraints);
		
		constraints.gridx = 0;		
 		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pStart.add(line, constraints);
		
		
		textArea = new JTextArea(10, 30);
		textArea.setText(filesManager.readPlayers());
		scroll = new JScrollPane(textArea);
		
		constraints.gridx = 0;		
 		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pStart.add(scroll, constraints);
		
		
		//JPanel Words
		pWords = new JPanel();
		tCurrentWord = new Titulos("Words are shown here", 60, new Color (0, 0, 0));
		pWords.setLayout(new GridBagLayout());
		
		constraints.gridx = 0;		
 		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWords.add(tCurrentWord, constraints);
		
		//JPanel Game
		pGame = new JPanel();
		cardGame = new CardLayout(0, 0);
		pGame.setLayout(cardGame);		
		pGame.add("pWords", pWords);
		
		
		//Timers
		timerWords = new Timer(1000, listener);
		//timerSecond = new Timer(1000, listener); // 100 s
		
		
		//Writing interface
		pWriting = new JPanel();
		pWriting.setLayout(new GridBagLayout());
		tfPlayerWord = new JTextField(30);
		tfPlayerWord.addKeyListener(listener);
		taRightWords = new JTextArea(10, 30);
		secondsLeft = 30;
		tLevel = new Titulos("Nivel: " + control.getLevel() + "     ", 30, new Color (0, 0, 0));
		tSeries = new Titulos("Serie: " + control.getSeries() + "     ", 30, new Color (0, 0, 0));
		tTime = new Titulos("Tiempo: " + secondsLeft + "     ", 30, new Color (20, 40, 100));
		bFinishSeries = new JButton("Finalizar serie");
		bFinishSeries.addActionListener(listener);
		
		timerPanel = new TimerPanel(10, this);
		
		//BETA WRITTEN LETTERS
		/*pWrittenLetters = new JPanel();
		pWrittenLetters.setLayout(new FlowLayout());
		tLetters = new Titulos("LETRAS ", 30, new Color (110, 40, 100));
		pWrittenLetters.add(tLetters);
		charArray = new char[14];
		char[] charArray = {'A', 'B', 'C', 'X'};
		pWrittenLetters.addKeyListener(KeyboardListener);*/
		
		
		constraints.gridx = 0;		
 		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER; 
		pWriting.add(tLevel, constraints);
		
		constraints.gridx = 1;		
 		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(tSeries, constraints);
		
		/*constraints.gridx = 2;									ORIGINAL
 		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(tTime, constraints);*/
		
		
		constraints.gridx = 2;		
 		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(timerPanel, constraints);
		
		
		
		
		constraints.gridx = 1;		
 		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(tfPlayerWord, constraints);
		//pWriting.add(pWrittenLetters, constraints);
		
		
		constraints.gridx = 1;		
 		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(taRightWords, constraints);
		
		constraints.gridx = 2;		
 		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(bFinishSeries, constraints);
		
		pGame.add("pWriting", pWriting);
		
		
		//main JPanel
		pMain = new JPanel();
		cardMain = new CardLayout(0, 0);
		pMain.setLayout(cardMain);
		pMain.add("pStart", pStart);
		pMain.add("pGame", pGame);
		
		constraints.gridx = 0;		
 		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		this.add(pMain, constraints);
				
				
		//Options
		pOptions = new JPanel();
		bExit = new JButton("Exit");
		bExit.addActionListener(listener);
		bExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		bRestart = new JButton("Restart");
		bRestart.addActionListener(listener);
		bRestart.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		bReset = new JButton("Reset Players");
		bReset.addActionListener(listener);
		bReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		pOptions.setLayout(new GridLayout(1,3, 50, 0));
		pOptions.add(bReset);
		pOptions.add(bRestart);
		pOptions.add(bExit);
		
		constraints.gridx = 0;		
 		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		this.add(pOptions, constraints);	
	}

	
	public void terminaTimer() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				timerPanel.setTime(10);
				//timerPanel.start();
				//start.setEnabled(true);
				
				
				
				
				timerPanel.stop();
				
				if (control.getSeries() == 1){
					nextSeries();
				}else if (control.getSeries() == 2) {
					switch (control.checkGameState()) {
					case 0:
						JOptionPane.showMessageDialog(null, "¡Perdiste!");
						System.exit(0);
						break;

					case 1:
						JOptionPane.showMessageDialog(null, "gameState: " + control.checkGameState());
						nextSeries();
						break;
					}
				}
				
				
			
				
				
			}
			
		});
	}
	
	/*
	 * Ends the current game and goes back to the starting interface where the user writes a player's name.
	 */
	private void restart() {
		control = new ControlJuegoDePalabras();
		
		textArea.setText(filesManager.readPlayers());
		//scroll = new JScrollPane(textArea);
		scroll.revalidate();
		scroll.repaint();
		pMain.repaint();
		
		cardMain.show(pMain, "pStart");
	}
	
	
	
	/**
	 * Passes to the next series and starts showing the words.
	 */
	private void nextSeries(){
		//timerSecond.stop();
		seriesWords = control.nextSeries();
		intWordsCounter = 0;
		tCurrentWord = new Titulos(seriesWords[intWordsCounter], 60, new Color (0, 0, 0));
		
		if (control.getSeries() == 1) {
			secondsLeft = 30;
		}
		timerWords.start();
		
		
		pWriting.removeAll();
		pWords.removeAll();
		pWords.setLayout(new GridBagLayout());
		constraints.gridx = 0;		
 		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWords.add(tCurrentWord, constraints);
		
		cardMain.show(pMain, "pGame");
		cardGame.show(pGame, "pWords");
	}
	
	
	/*Shows the next word of the series.*/
	private void nextWord() {
		intWordsCounter++;
		tCurrentWord.revalidate();
		tCurrentWord.repaint();
		tCurrentWord = new Titulos(seriesWords[intWordsCounter], 60, new Color (0, 0, 0));
		timerWords.start();
		
		pWords.removeAll();
		
		pWords.setLayout(new GridBagLayout());
		constraints.gridx = 0;		
 		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWords.add(tCurrentWord, constraints);
		
		pWords.repaint();
		pGame.repaint();
		pMain.repaint();
	}
	
	

	/*
	 * Shows the writing interface so the user can write the words he remembers, shows a list of the
	 * right words written by the user in the current series, and shows the current level, series and the
	 * remaining time for writing the words of the level. 
	 * */
	private void writingInterface() {
		timerWords.stop();
		secondsLeft--;
		
		//pStart.removeAll();
		pWords.removeAll();
		pWriting.removeAll();
		tLevel.revalidate();
		tSeries.revalidate();
		tTime.revalidate();
		
		pWriting.setLayout(new GridBagLayout());
		
		tLevel = new Titulos("Nivel: " + control.getLevel() + "     ", 30, new Color (0, 0, 0));
		tSeries = new Titulos("Serie: " + control.getSeries() + "     ", 30, new Color (0, 0, 0));
		tTime = new Titulos("Tiempo: " + secondsLeft + "     ", 30, new Color (20, 40, 100));
		
		//BETA
		/*pWrittenLetters = new JPanel();
		pWrittenLetters.setLayout(new FlowLayout());
		pWrittenLetters.addKeyListener(KeyboardListener);
		tLetters = new Titulos(String.valueOf(charArray), 30, new Color (110, 40, 100));
		pWrittenLetters.add(tLetters);*/
		
		
		
		//timerSecond.start();
		
		constraints.gridx = 0;		
 		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(tLevel, constraints);
		
		constraints.gridx = 1;		
 		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(tSeries, constraints);
		
		/*constraints.gridx = 2;								ORIGINAL
 		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(tTime, constraints);*/
		
		
		constraints.gridx = 2;		
 		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(timerPanel, constraints);
		
		
		
		constraints.gridx = 1;		
 		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(tfPlayerWord, constraints);
		//pWriting.add(pWrittenLetters, constraints);
		
		taRightWords.setText(control.getSeriesRightWords());
		constraints.gridx = 1;		
 		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(taRightWords, constraints);
		
		constraints.gridx = 2;		
 		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(bFinishSeries, constraints);
		
		cardMain.show(pMain, "pGame");
		cardGame.show(pGame, "pWriting");
		
		tfPlayerWord.setFocusable(true);
		tfPlayerWord.grabFocus();
		
		/*pWrittenLetters.setFocusable(true);
		pWrittenLetters.grabFocus();*/
	}
	
	
	
	
	/**
	 * The Class Listener.
	 */
	private class Listener implements ActionListener, KeyListener{

		/**
		 * Action performed.
		 *
		 * @param e the e
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == bExit) {
				System.exit(0);
				
			}else if (e.getSource() == line) { //The user wrote a player name
				if (line.getText().contains(" ") || line.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Invalid player name");
					line.setText("");
				}else {
				filesManager.writePlayer(line.getText().toUpperCase().split("\n")[0]);
				textArea.setText(filesManager.readPlayers());
				control.startGame(line.getText().toUpperCase().split("\n")[0]);
				line.setText("");
				nextSeries();
				}
				
			}else if(e.getSource() == bReset){ //Deletes all players information.
				filesManager.deletePlayers();
				textArea.setText(filesManager.readPlayers());
				line.setText("");
				System.exit(0);
				
			}else if(e.getSource() == bRestart) { //Returns to starting screen where the user writes a player's name.
				
				timerWords.stop();
				//timerSecond.stop();
				restart();
				
			}else if(e.getSource() == bFinishSeries) {//Ends the writing stage of the current series.
				if ((control.checkGameState() == 0) && (control.getSeries() == 2)) {
					JOptionPane.showMessageDialog(null, "¡Perdiste!");
					System.exit(0);
				}else {
					nextSeries();
				}
				
			}else if(e.getSource() == timerWords) {//The time for showing a specific series word is over.
				timerWords.stop();
				
				if (intWordsCounter == (seriesWords.length - 1) ) {
					writingInterface();
					
					
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							//timerPanel.setTime(5);
							timerPanel.start();
						}
						
					});
					
					
				}else {
					nextWord();
				}
				
			}
			/*else if(e.getSource() == timerSecond){ //A second has passed in the writing stage.
				timerSecond.stop();
				if (secondsLeft == 1) {
					if (control.getSeries() == 1)
						nextSeries();
					if (control.getSeries() == 2) {
						switch (control.checkGameState()) {
						case 0:
							JOptionPane.showMessageDialog(null, "¡Perdiste!");
							System.exit(0);
							break;

						case 1:
							JOptionPane.showMessageDialog(null, "gameState: " + control.checkGameState());
							nextSeries();
							break;
						}
					}
				} else {
					secondsLeft--;
					tTime.revalidate();
					pWriting.remove(tTime);
					tTime = new Titulos("Tiempo: " + secondsLeft + "     ", 30, new Color (20, 40, 100));
					
					constraints.gridx = 2;		
			 		constraints.gridy = 0;
					constraints.gridwidth = 1;
					constraints.gridheight = 1;
					constraints.fill = GridBagConstraints.HORIZONTAL;
					constraints.anchor = GridBagConstraints.CENTER;
					pWriting.add(tTime, constraints);
					
					pWriting.repaint();
					
					//timerSecond.start();
				}
				
			}*/
			/*else if (e.getSource() == tfPlayerWord) {//The player just wrote a word.
				timerSecond.stop();
				switch (control.verifyWord(tfPlayerWord.getText().toUpperCase())) {
				case 0:
					tfPlayerWord.setText("");
					timerSecond.start();
					break;
				
				case 1:
					taRightWords.setText(control.getSeriesRightWords());
					tfPlayerWord.setText("");
					timerSecond.start();
					break;
					
				case 2:
					//next Series
					taRightWords.setText(control.getSeriesRightWords());
					tfPlayerWord.setText("");
					nextSeries();
					break;
					
					
				default:
					JOptionPane.showMessageDialog(null, "none");
					break;
				}
			}*/ 
		}

		@Override
		public void keyTyped(KeyEvent ke) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent ke) {
			// TODO Auto-generated method stub
			if (ke.getKeyCode() == ke.VK_ENTER) {//The player just wrote a word.
				//timerSecond.stop();
				switch (control.verifyWord(tfPlayerWord.getText().toUpperCase())) {
				case 0: //The writtenWord had been written already or it's wrong.
					tfPlayerWord.setText("");
					//timerSecond.start();
					break;
				
				case 1: //The writtenWord is right, but there are still words that haven't been written.
					taRightWords.setText(control.getSeriesRightWords());
					tfPlayerWord.setText("");
					//timerSecond.start();
					break;
					
				case 2:   //All the series words have been written.
					//next Series
					taRightWords.setText(control.getSeriesRightWords());
					tfPlayerWord.setText("");
					timerPanel.stop();
					nextSeries();
					break;
					
					
				default:
					JOptionPane.showMessageDialog(null, "none");
					break;
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent ke) {
			// TODO Auto-generated method stub
			
		}
	}




	
	
	
	/*private class KeyboardListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent keyTyped) {
			// TODO Auto-generated method stub
			//if (keyTyped.getKeyChar() == 'V' ) { //VK_BACK_SPACE
			//}
			
			charArray[0] = keyTyped.getKeyChar();
			writingInterface();
		}

		@Override
		public void keyPressed(KeyEvent kp) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent kr) {
			// TODO Auto-generated method stub
			
		}

		
	}*/
	
	/*
	 *  	VK_DELETE
	 *  
	 *  
	 *  VK_INSERT
	 *  VK_CLEAR
	 *   	VK_UNDO
	 *   VK_SUBTRACT*/
	
	
}
