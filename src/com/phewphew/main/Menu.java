package com.phewphew.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
//For file
import java.io.FileWriter;   
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;

import javax.swing.JOptionPane;

import com.phewphew.main.Game.STATE;

public class Menu extends MouseAdapter{
	
	private Handler handler;
	private Game game;
	private HUD hud;
	private Shop shop;
	private Random r= new Random();
	
	private String[] highscoreNormal = {"","","","",""};
	private String[] highscoreNormal_A = {"","","","",""};
	private String[] highscoreHard = {"","","","",""};
	private String[] highscoreHard_A = {"","","","",""};
	private Integer[] hscoreN = {0,0,0,0,0};
	private Integer[] hscoreH = {0,0,0,0,0};
	private int colonindex,tempscore;
	private AudioPlayer menuaudio = new AudioPlayer("res/menubgm.wav");
	private AudioPlayer audioplayer2 = new AudioPlayer("res/playbgm.wav");
	
	public Menu(Game game, Handler handler,HUD hud,Shop shop) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		this.shop = shop;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		//MENU
		if(game.gameState == STATE.Menu) {
			menuaudio.play();
			//Play Button
			if(mouseOver(mx,my,210,150,200,64)) {
				game.gameState = STATE.Select;
				return;
			}
		
			//ScoreBoard button
			if(mouseOver(mx,my,210,250,200,64)) {
				game.gameState = STATE.Scoreboard;
			}
			//Quit Button
			if(mouseOver(mx,my,210,350,200,64)) {
				System.exit(1);
			}
		}
		
		//SCOREBOARD
		if(game.gameState == STATE.Scoreboard) {
			if(mouseOver(mx,my,325,350,100,48)) {
				game.gameState = STATE.Menu;
				return;
			}
			if(mouseOver(mx,my,200,350,100,48)) {
				getScoreNormal();
				getScoreHard();
			}
		}
		
		//Game Over
		if(game.gameState == STATE.End) {
			
			//Try Again
			if(mouseOver(mx,my,160,320,150,48)) {
				game.gameState = STATE.Game;
				hud.setLevel(1);
				hud.setScore(0);
				hud.setCredit(0);
				shop.setB1(10);
				shop.setB2(10);
				shop.setupglv1(1);
				shop.setupglv2(1);
				hud.bounds = 0;
				hud.HEALTH = Game.clamp(hud.HEALTH, 0, 100);
				handler.object.clear();
				handler.addObject(new Player(Game.WIDTH/2-32,Game.HEIGHT/2-32,ID.Player,handler));
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-50),r.nextInt(Game.HEIGHT-50),ID.BasicEnemy,handler));
				return;
			}
			
			//MainMenu
			if(mouseOver(mx,my,350,320,150,48)) {
				if(game.diff == 0) {
					saveScoreNormal();
				}else {
					saveScoreHard();
				}
				game.gameState = STATE.Menu;
				audioplayer2.stop();
				menuaudio.play();
				hud.setLevel(1);
				hud.setScore(0);
				hud.setCredit(0);
				shop.setB1(10);
				shop.setB2(10);
				shop.setupglv1(1);
				shop.setupglv2(1);
				hud.bounds = 0;
				hud.HEALTH = Game.clamp(hud.HEALTH, 0, 100);
				return;
			}
		}
		
		//SELECT
		if(game.gameState == STATE.Select) {
			//Normal
			if(mouseOver(mx,my,210, 150, 200, 64)) {
				game.gameState = STATE.Game;
				menuaudio.stop();
				audioplayer2.play();
				handler.addObject(new Player((Game.WIDTH/2-32),(Game.HEIGHT/2-32),ID.Player,handler));
				handler.clearEnemy();
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-10),r.nextInt(Game.HEIGHT-10),ID.BasicEnemy,handler));
				game.diff = 0;
				game.diff_label = "Normal";
			}
			//Hard
			if(mouseOver(mx,my,210, 250, 200, 64)) {
				game.gameState = STATE.Game;
				menuaudio.stop();
				audioplayer2.play();
				handler.addObject(new Player((Game.WIDTH/2-32),(Game.HEIGHT/2-32),ID.Player,handler));
				handler.clearEnemy();
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-10),r.nextInt(Game.HEIGHT-10),ID.BasicEnemy,handler));
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-10),r.nextInt(Game.HEIGHT-10),ID.BasicEnemy,handler));
				game.diff = 1;
				game.diff_label = "Hard";
			}
			//Back
			if(mouseOver(mx,my,210, 350, 200, 64)) {
				game.gameState = STATE.Menu;
				return;
			}
			
		}
		
	}
	public void mouseReleased(MouseEvent e) {
		
	}
	
	private boolean mouseOver(int mx,int my,int x,int y,int width,int height) {
		if(mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			}else return false;
		}else return false;
		
	}
	
	public void saveScoreNormal() {
		getScoreNormal();
		int hscore = hscoreN[0];
		
		int playerscore;
		playerscore = hud.getScore();
		String highscoreInput = "";
		if(playerscore > hscore) {
			String name = JOptionPane.showInputDialog("You beat highscore! What's your name?");
			highscoreInput = name + ":" + playerscore;
			try {
				FileWriter f = new FileWriter("res/scoreBoardNormal.txt",true);
				BufferedWriter bw = new BufferedWriter(f);
				try {
					bw.newLine();
					bw.write(highscoreInput);
					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	public void saveScoreHard() {
		getScoreHard();
		int hscore = hscoreH[0];
		
		int playerscore;
		playerscore = hud.getScore();
		String highscoreInput = "";
		if(playerscore > hscore) {
			String name = JOptionPane.showInputDialog("You beat highscore! What's your name?");
			highscoreInput = name + ":" + playerscore;
			try {
				FileWriter f = new FileWriter("res/scoreBoardHard.txt",true);
				BufferedWriter bw = new BufferedWriter(f);
				try {
					bw.newLine();
					bw.write(highscoreInput);
					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	public void getScoreNormal() {
		try {
			FileReader reader = new FileReader(new File("res/scoreBoardNormal.txt"));
            BufferedReader br = new BufferedReader(reader);
            String message = "";
            int i = 0;
            while((message = br.readLine()) != null) {
            	if(i < 5) {
            		highscoreNormal[i] = message;
            		colonindex = message.indexOf(":");
            		tempscore = Integer.parseInt(message.substring(colonindex+1));
            		hscoreN[i] = tempscore;
            		i++;
            	}
            }
            
            Arrays.sort(hscoreN,Collections.reverseOrder());
            System.out.println(Arrays.toString(hscoreN));
            
		}catch (Exception e) {
			e.printStackTrace();
		}

		try {
			FileReader reader = new FileReader(new File("res/scoreBoardNormal.txt"));
            BufferedReader br = new BufferedReader(reader);
            String message = "";
            int i = 0;
            while((message = br.readLine()) != null) {
            	if(i < 5) {
        			colonindex = message.indexOf(":");
        			tempscore = Integer.parseInt(message.substring(colonindex+1));
        			for(int j=0;j<5;j++) {
        				if(hscoreN[j] == tempscore) {
        					highscoreNormal_A[j] = message;
        				}
        			}
        			i++;
            	}
            }
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println(Arrays.toString(highscoreNormal_A));
	}
	
	public void getScoreHard() {
		try {
			FileReader reader = new FileReader(new File("res/scoreBoardHard.txt"));
            BufferedReader br = new BufferedReader(reader);
            String message = "";
            int i = 0;
            while((message = br.readLine()) != null) {
            	if(i < 5) {
            		highscoreHard[i] = message;
            		colonindex = message.indexOf(":");
            		tempscore = Integer.parseInt(message.substring(colonindex+1));
            		hscoreH[i] = tempscore;
            		i++;
            	}
            }
            
            Arrays.sort(hscoreH,Collections.reverseOrder());
            System.out.println(Arrays.toString(hscoreH));

            //System.out.println(Arrays.toString(highscoreHard_A));
            
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			FileReader reader = new FileReader(new File("res/scoreBoardHard.txt"));
            BufferedReader br = new BufferedReader(reader);
            String message = "";
            int i = 0;
            while((message = br.readLine()) != null) {
            	if(i < 5) {
        			colonindex = message.indexOf(":");
        			tempscore = Integer.parseInt(message.substring(colonindex+1));
        			for(int j=0;j<5;j++) {
        				if(hscoreH[j] == tempscore) {
        					highscoreHard_A[j] = message;
        				}
        			}
        			i++;
            	}
            }
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("Silkscreen",1,50);
		Font fnt2 = new Font("Silkscreen",1,30);
		Font fnt3 = new Font("Silkscreen",1,20);
		Font fnt4 = new Font("arial",1,20);
		Font fnt5 = new Font("arial",1,14);
		
		if(game.gameState == STATE.Menu) {
			//Title
			g.setFont(fnt);
			g.drawString("PhewPhew", 160, 70);
			
			//Play
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawRect(210, 150, 200, 64);
			g.drawString("Play", 260, 190);
			
			//ScoreBoard
			g.setFont(fnt3);
			g.setColor(Color.white);
			g.drawRect(210, 250, 200, 64);
			g.drawString("Scoreboard", 235, 290);
			
			//Quit
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Quit", 270, 390);
			}
		else if(game.gameState == STATE.Scoreboard) {
			//Title
			g.setFont(fnt);
			g.drawString("Scoreboard", 130, 70);
			
			//Normal Board
			g.setFont(fnt4);
			g.drawRect(100,100,200,200);
			g.drawString("Normal Mode", 140, 130);
			g.setFont(fnt5);
			g.drawString("1st: ", 110, 170);
			g.drawString(""+highscoreNormal_A[0], 150, 170);
			g.drawString("2nd: ", 110, 200);
			g.drawString(""+highscoreNormal_A[1], 150, 200);
			g.drawString("3rd: ", 110, 230);
			g.drawString(""+highscoreNormal_A[2], 150, 230);
			g.drawString("4th: ", 110, 260);
			g.drawString(""+highscoreNormal_A[3], 150, 260);
			g.drawString("5th: ", 110, 290);
			g.drawString(""+highscoreNormal_A[4], 150, 290);
			
			//Hard Board
			g.setFont(fnt4);
			g.drawRect(350,100,200,200);
			g.drawString("Hard Mode", 400, 130);
			g.setFont(fnt5);
			g.drawString("1st: ", 360, 170);
			g.drawString(""+highscoreHard_A[0], 400, 170);
			g.drawString("2nd: ", 360, 200);
			g.drawString(""+highscoreHard_A[1], 400, 200);
			g.drawString("3rd: ", 360, 230);
			g.drawString(""+highscoreHard_A[2], 400, 230);
			g.drawString("4th: ", 360, 260);
			g.drawString(""+highscoreHard_A[3], 400, 260);
			g.drawString("5th: ", 360, 290);
			g.drawString(""+highscoreHard_A[4], 400, 290);
			
			//Back button
			g.setFont(fnt3);
			g.setColor(Color.white);
			g.drawRect(325, 350, 100, 48);
			g.drawString("Back", 340, 380);
			
			//Show Score
			g.drawRect(200, 350, 100, 48);
			g.drawString("Show", 215, 380);
			
		}
		else if(game.gameState == STATE.End) {
			//Title
			g.setFont(fnt);
			g.drawString("GAME OVER", 160, 170);
			
			//Desc
			g.setFont(fnt2);
			g.drawString("Your Score: "+hud.getScore(), 155, 270);
			
			//TryAgain
			g.setFont(fnt3);
			g.setColor(Color.white);
			g.drawRect(160, 320, 150, 48);
			g.drawString("Try Again", 170, 350);
			
			
			//MainMenu
			g.setFont(fnt3);
			g.setColor(Color.white);
			g.drawRect(350, 320, 150, 48);
			g.drawString("Main Menu", 355, 350);
		}
		else if(game.gameState == STATE.Select) {
			//Title
			g.setFont(fnt);
			g.drawString("Difficulty", 160, 70);
			
			//Normal
			g.setFont(fnt2);
			g.drawRect(210, 150, 200, 64);
			g.drawString("Normal", 240, 190);
			
			//Hard
			g.drawRect(210, 250, 200, 64);
			g.drawString("Hard", 265, 290);
			
			//Back
			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 265, 390);
		}
	}
}
