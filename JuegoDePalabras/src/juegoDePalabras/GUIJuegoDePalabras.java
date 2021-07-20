/* Pogramación interactiva
 * Autor: Diego Fabián Ledesma - 1928161
 * Miniproyecto 3: Juego de palabras. */

package juegoDePalabras;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import misComponentes.Titulos;

/*GUIJuegoDePalabras hereda de JFrame, y hace uso de la clase Titulos que se encuentra en el proyecto MisComponentes.
 * */
public class GUIJuegoDePalabras extends JFrame {
	
	private JTextField line, tfPlayerWord;
	private JTextArea textArea, taRightWords;
	private FilesManager filesManager;
	private JLabel lInstEnterPlayer;
	private JPanel pStart, pOptions, pMain, pGame, pWords, pWriting, pStats, pPlayer, pLevel;
	private JButton bReset, bExit, bFinishSeries;
	private GridBagConstraints constraints;
	private CardLayout cardMain, cardGame;
	private Titulos tGameTitle, tCurrentWord, tLevel, tSeries, tTime;
	
	private ControlJuegoDePalabras control;
	private String[] seriesWords;
	private Listener listener;
	private Timer timerWords, timerSecond;
	
	private int intWordsCounter, secondsLeft;
	
	
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
	

	/*Initializes main components and shows the starting frame in which the user can see player's names with it's level and
	 * write a name either present on the list or not. If the name written by the user is in the list, the game starts
	 * in the level of the selected player's name.*/
	private void initGUI() {
		this.getContentPane().setLayout(new GridBagLayout());;
        constraints = new GridBagConstraints();
        
		control = new ControlJuegoDePalabras();
		seriesWords = new String[4];
		listener = new Listener();
		
		
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
		JScrollPane scroll = new JScrollPane(textArea);
		
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
		timerSecond = new Timer(1000, listener); // 100 s
		
		
		//Writing interface
		pWriting = new JPanel();
		pWriting.setLayout(new GridBagLayout());
		tfPlayerWord = new JTextField(30);
		tfPlayerWord.addActionListener(listener);
		taRightWords = new JTextArea(10, 30);
		secondsLeft = 20;
		tLevel = new Titulos("Nivel: " + control.getLevel() + "     ", 30, new Color (0, 0, 0));
		tSeries = new Titulos("Serie: " + control.getSeries() + "     ", 30, new Color (0, 0, 0));
		tTime = new Titulos("Tiempo: " + secondsLeft + "     ", 30, new Color (20, 40, 100));
		bFinishSeries = new JButton("Finalizar serie");
		bFinishSeries.addActionListener(listener);
		
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
		
		constraints.gridx = 2;		
 		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(tTime, constraints);
		
		constraints.gridx = 1;		
 		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(tfPlayerWord, constraints);
		
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
		
		bReset = new JButton("Reset Players");
		bReset.addActionListener(listener);
		bReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		pOptions.setLayout(new GridLayout(1,3, 50, 0));
		pOptions.add(bReset);
		pOptions.add(bExit);
		
		constraints.gridx = 0;		
 		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		this.add(pOptions, constraints);	
	}

	
	/*Passes to the next series and starts showing the words.*/
	private void nextSeries(){
		timerSecond.stop();
		seriesWords = control.nextSeries();
		intWordsCounter = 0;
		tCurrentWord = new Titulos(seriesWords[intWordsCounter], 60, new Color (0, 0, 0));
		
		if (control.getSeries() == 1) {
			secondsLeft = 20;
		}
		timerWords.start();
		
		
		//pWriting.removeAll();
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
	
	
	/*Shows the writing interface so the user can write the words he remembers, shows a list of the
	 * right words written by the user in the current series, and shows the current level, series and the
	 * remaining time for writing the words of the level. */
	private void writingInterface() {
		timerWords.stop();
		secondsLeft--;
		
		pStart.removeAll();
		pWords.removeAll();
		pWriting.removeAll();
		tLevel.revalidate();
		tSeries.revalidate();
		tTime.revalidate();
		
		pWriting.setLayout(new GridBagLayout());
		
		tLevel = new Titulos("Nivel: " + control.getLevel() + "     ", 30, new Color (0, 0, 0));
		tSeries = new Titulos("Serie: " + control.getSeries() + "     ", 30, new Color (0, 0, 0));
		tTime = new Titulos("Tiempo: " + secondsLeft + "     ", 30, new Color (20, 40, 100));
		
		timerSecond.start();
		
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
		
		constraints.gridx = 2;		
 		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(tTime, constraints);
		
		constraints.gridx = 1;		
 		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		pWriting.add(tfPlayerWord, constraints);
		
		
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
	}
	
	
	
	
	private class Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == bExit) {
				System.exit(0);
				
			}else if (e.getSource() == line) { //The player just tried to write a series word.
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
				
			}else if(e.getSource() == bFinishSeries) {/*Ends the writing stage of the current series.*/
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
				}else {
					nextWord();
				}
				
			}else if(e.getSource() == timerSecond){ //A second has passed in the writing stage.
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
					
					timerSecond.start();
				}
				
			}else if (e.getSource() == tfPlayerWord) {//The player just wrote a word.
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
			} 
		}
	}
}
