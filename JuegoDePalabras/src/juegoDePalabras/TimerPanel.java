package juegoDePalabras;

/**
 * 
 */
//package myTimer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author paolajr-EISC
 *
 */
public class TimerPanel extends JPanel implements Runnable {

	private int width = 150;
	private int height = 24;
	
	private String timeString="00:00";
	private long time;
	private Font font;
	private Thread timerThread;
	//private MyTimer myTimer;
	private GUIJuegoDePalabras gui;
	
	
	public TimerPanel(long time, GUIJuegoDePalabras guiJ){
		setTime(time);	
		//this.myTimer = myTimer;
		this.gui = guiJ;
		
	}
	
	public void setTime(long time) {
		this.time = time;
		//long h = time/3600;
		long m = (time)/60; 
		long s = time - m*60;
		timeString = String.format("%02d:%02d", m, s);
		font = new Font(Font.DIALOG, Font.BOLD, 40);
		setFont(font);
		height=font.getSize();
		FontMetrics fm = getFontMetrics(font);
		width=fm.stringWidth(timeString); 
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawString(timeString, 0, height);
	}
	
	public Dimension getPreferredSize(){
		Dimension size = new Dimension(width, height);
		return size;
	}
   
	//Tareas a ejecutar como subproceso
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(time>0){
			time-=1;
			setTime(time);	
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				return;
			}
		}
		timeUp();
	}
	
	//Termina el subproceso
	private void timeUp(){
        stop();
        //myTimer.terminaTimer();    
        gui.endTimer();
	}
	
	//inicia el subproceso
	public void start(){
		stop();
		timerThread = new Thread(this);
		timerThread.start();
	}
	
	//destruir el subproceso
	public void stop(){
		if(timerThread!=null) {
			timerThread.interrupt();
			timerThread=null;
		}
	}	
}

