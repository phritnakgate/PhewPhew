package com.phewphew.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.phewphew.main.Game.STATE;

//Keyboard Input
public class KeyInput extends KeyAdapter{
	
	private Handler handler;
	Game game;
	
	private boolean uP = false;
	private boolean dP = false;
	private boolean lP = false;
	private boolean rP = false;
	
	public KeyInput(Handler handler, Game game) {
		this.game = game;
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//System.out.println(key);
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player) {
				if(key == KeyEvent.VK_W) {
					uP = true;
					tempObject.setVelY(-handler.speed);
				}
				if(key == KeyEvent.VK_S) {
					dP = true;
					tempObject.setVelY(handler.speed);
				}
				if(key == KeyEvent.VK_A) {
					lP = true;
					tempObject.setVelX(-handler.speed);
				}
				if(key == KeyEvent.VK_D) {
					rP = true;
					tempObject.setVelX(handler.speed);
				}
					
			}
		}
		if(key == KeyEvent.VK_P) {
			
			if(game.gameState == STATE.Game) {
				if(Game.pause) Game.pause = false;
				else Game.pause = true;
			}
			
			
		}
		if(key == KeyEvent.VK_SPACE) {
			if(game.gameState == STATE.Game) {
				game.gameState = STATE.Shop;
			}else if (game.gameState == STATE.Shop){
				game.gameState = STATE.Game;
			}
		}
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			//Solve short delay when changing directions
			if(tempObject.getId() == ID.Player) {
				if(key == KeyEvent.VK_W) {
					uP = false;
					if(dP) {
						tempObject.setVelY(handler.speed);
					}else {
						tempObject.setVelY(0);
					}
				}
				if(key == KeyEvent.VK_S) {
					dP = false;
					if(uP) {
						tempObject.setVelY(-handler.speed);
					}else {
						tempObject.setVelY(0);
					}
				}
				if(key == KeyEvent.VK_A) {
					lP = false;
					if(rP) {
						tempObject.setVelX(handler.speed);
					}else {
						tempObject.setVelX(0);
					}
				}
				if(key == KeyEvent.VK_D) {
					rP = false;
					if(lP) {
						tempObject.setVelX(-handler.speed);
					}else {
						tempObject.setVelX(0);
					}
				}
			}
		}
	}
}
