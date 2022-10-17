package com.phewphew.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class FastEnemy extends GameObject{
	
	private Handler handler;
	
	public FastEnemy(float x, float y, ID id,Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velX = 2;
		velY = 9;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,16,16);
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		if(y <= 0 || y >= Game.HEIGHT-32) {
			velY *= -1;
		}
		if(x <= 0 || x >= Game.WIDTH-32) {
			velX *= -1;
		}
		
		//Trail
		handler.addObject(new Trail(x,y,ID.Trail,Color.blue,16,16,0.05f,handler));
	}

	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect((int)x, (int)y, 16, 16);
		
	}
	
}

