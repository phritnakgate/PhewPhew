package com.phewphew.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class MenuParticle extends GameObject{
	
	private Handler handler;
	Random r = new Random();
	private int red = r.nextInt(255);
	private int green = r.nextInt(255);
	private int blue = r.nextInt(255);
	private Color col;
	int dir = 0;
	
	public MenuParticle(float x, float y, ID id,Handler handler) {
		super(x, y, id);
		this.handler = handler;
		dir = r.nextInt(2);
		if(dir == 0) {
			velX = 2;
			velY = 9;
		}else if(dir == 1) {
			velX = 9;
			velY = 2;
		}
		
		col = new Color(red,green,blue);
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
		handler.addObject(new Trail(x,y,ID.Trail,col,16,16,0.1f,handler));
	}

	public void render(Graphics g) {
		g.setColor(col);
		g.fillRect((int)x, (int)y, 16, 16);
		
	}
	
}

