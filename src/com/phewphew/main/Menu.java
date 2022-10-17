package com.phewphew.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.phewphew.main.Game.STATE;

public class Menu extends MouseAdapter{
	
	private Handler handler;
	private Game game;
	private HUD hud;
	private Random r= new Random();
	
	public Menu(Game game, Handler handler,HUD hud) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		
		/*
		String audioFilePath = "res/menubgm.wav";
        AudioPlayer2 menu_bgm = new AudioPlayer2();
        menu_bgm.play(audioFilePath);
        */
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		//MENU
		if(game.gameState == STATE.Menu) {
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
			if(mouseOver(mx,my,265,350,100,64)) {
				game.gameState = STATE.Menu;
				return;
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
				hud.bounds = 0;
				hud.HEALTH = Game.clamp(hud.HEALTH, 0, 100);
				handler.object.clear();
				handler.addObject(new Player(Game.WIDTH/2-32,Game.HEIGHT/2-32,ID.Player,handler));
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-50),r.nextInt(Game.HEIGHT-50),ID.BasicEnemy,handler));
				return;
			}
			
			//MainMenu
			if(mouseOver(mx,my,350,320,150,48)) {
				game.gameState = STATE.Menu;
				hud.setLevel(1);
				hud.setScore(0);
				hud.setCredit(0);
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
				handler.addObject(new Player((Game.WIDTH/2-32),(Game.HEIGHT/2-32),ID.Player,handler));
				handler.clearEnemy();
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-10),r.nextInt(Game.HEIGHT-10),ID.BasicEnemy,handler));
				game.diff = 0;
				game.diff_label = "Normal";
			}
			//Hard
			if(mouseOver(mx,my,210, 250, 200, 64)) {
				game.gameState = STATE.Game;
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
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("Silkscreen",1,50);
		Font fnt2 = new Font("Silkscreen",1,30);
		Font fnt3 = new Font("Silkscreen",1,20);
		
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
			
			//Back button
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawRect(265, 350, 100, 64);
			g.drawString("Back", 270, 390);
			
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
