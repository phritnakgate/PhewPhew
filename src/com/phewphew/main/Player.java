package com.phewphew.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends GameObject{
	
	Handler handler;
	
	public Player(float x, float y, ID id,Handler handler) {
		super(x, y, id);
		this.handler = handler;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,32,32);
	}
	
	public void tick() {
		//Limit Player Movement to corner
		x = Game.clamp(x, 0, Game.WIDTH - 37);
		y = Game.clamp(y, 0, Game.HEIGHT - 60);
		
		//Collision
		collision();
		
		//Trail
		handler.addObject(new Trail(x,y,ID.Trail,Color.white,32,32,0.08f,handler));
		//For Smoother Movement
		if(velX <= -1) {
			for(int i=0;i > velX; i--) {
				x -= 1;
			}
		}else {
			for(int i=0;i < velX; i++) {
				x += 1;
			}
		}
		if(velY <= -1) {
			for(int i=0;i > velY; i--) {
				y -= 1;
			}
		}else {
			for(int i=0;i < velY; i++) {
				y += 1;
			}
		}
		
	}
	
	private void collision() {
		for(int i=0; i < handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SmartEnemy) {
				//Check if rectangle collision of enemy intersect player collision rectangle
				if(getBounds().intersects(tempObject.getBounds())) {
					HUD.HEALTH -= 2;
				}
			}
			if(tempObject.getId() == ID.EnemyBoss) {
				if(getBounds().intersects(tempObject.getBounds())) {
					HUD.HEALTH -= 5;
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int)x, (int)y, 32, 32);
	}
	
	
	
}
